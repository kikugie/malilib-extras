package dev.kikugie.malilib_extras.util.restriction

import dev.kikugie.malilib_extras.util.FabricUtils.fabric
import fi.dy.masa.malilib.util.StringUtils
import me.fallenbreath.conditionalmixin.api.annotation.Condition
import me.fallenbreath.conditionalmixin.api.annotation.Condition.Type.*
import me.fallenbreath.conditionalmixin.api.annotation.Restriction
import me.fallenbreath.conditionalmixin.api.mixin.ConditionTester
import me.fallenbreath.conditionalmixin.api.util.VersionChecker
import kotlin.reflect.jvm.jvmName

object SimpleRestrictionChecker : RestrictionChecker {
    private val formatter: (String) -> String = { it -> "malilib_extras.condition.reason.$it" }
    override fun test(restriction: Restriction): RestrictionChecker.Result {
        val fails = mutableListOf<CheckResult>()

        restriction.require.forEach {
            val result = check(it)
            if (!result.success) fails.add(result)
        }
        restriction.conflict.forEach {
            val result = check(it)
            if (result.success) fails.add(result)
        }

        return if (fails.isEmpty()) RestrictionChecker.DEFAULT
        else RestrictionChecker.Result(false, fails.map { it.translation })
    }

    private fun check(condition: Condition): CheckResult =
        when (condition.type) {
            MOD -> checkMod(condition)
            TESTER -> checkTester(condition)
            MIXIN -> throw IllegalArgumentException("Mixin checker is not supported for runtime conditions")
        }


    private fun checkMod(condition: Condition): CheckResult {
        val modid = condition.value
        require(modid.isNotBlank()) { "Mod ID must not be blank for the type MOD" }

        if (!fabric.isModLoaded(modid))
            return CheckResult(false, formatter("not_found"), modid)
        val modVersion = fabric.getModContainer(modid).get().metadata.version
        val versionPredicates = condition.versionPredicates.toList()
        return if (VersionChecker.doesVersionSatisfyPredicate(modVersion, versionPredicates))
            CheckResult(true, formatter("unsupported"), modid, modVersion.toString())
        else CheckResult(
            false,
            formatter("version_mismatch"),
            modid,
            modVersion.toString(),
            versionPredicates.toString()
        )
    }

    private fun checkTester(condition: Condition): CheckResult {
        val clazz = Class.forName(condition.tester.jvmName)
        require(!clazz.isInterface) { "Tester class ${clazz.name} must not be an interface" }

        val tester = clazz.getConstructor().newInstance() as ConditionTester
        val result = tester.isSatisfied(null)
        return CheckResult(result, formatter("test"), result.toString())
    }

    class CheckResult(
        val success: Boolean,
        private val reason: String,
        vararg val args: String
    ) {
        val translation: () -> String = { StringUtils.translate(reason, args) }
    }
}