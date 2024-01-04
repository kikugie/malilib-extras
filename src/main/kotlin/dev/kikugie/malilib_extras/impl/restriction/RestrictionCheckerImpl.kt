package dev.kikugie.malilib_extras.impl.restriction

import dev.kikugie.malilib_extras.api.restriction.*
import dev.kikugie.malilib_extras.util.FabricUtils.fabric
import dev.kikugie.malilib_extras.util.translation
import net.fabricmc.loader.api.metadata.version.VersionPredicate
import kotlin.reflect.KClass

/**
 * // dev info
 * ## Translations:
 * - `malilib_extras.condition.not_found(mod_id)`
 * - `malilib_extras.condition.version_match(mod_id, version)`
 * - `malilib_extras.condition.version_mismatch(mod_id, version)`
 * - `malilib_extras.condition.test_success(class_name)`
 * - `malilib_extras.condition.test_fail(class_name)`
 */
object RestrictionCheckerImpl : RestrictionChecker {
    private val formatter: (String) -> String = { "malilib_extras.condition.$it" }
    override fun testRestriction(restriction: Restriction?): TestResult {
        if (restriction == null) return TestResult(true)
        val fails = mutableListOf<TestResult>()

        restriction.require.forEach {
            val result = testCondition(it)
            if (!result.success) fails += result
        }
        restriction.conflict.forEach {
            val result = testCondition(it)
            if (result.success) fails += result
        }
        return if (fails.isEmpty()) TestResult(true)
        else TestResult(false, fails.flatMap { it.reasons })
    }

    override fun testCondition(condition: Condition): TestResult {
        if (condition.tester != Tester::class) {
            require(condition.value.isEmpty() && condition.version.isEmpty()) {
                "Condition with a tester can't have mod requirements: $condition"
            }
            require(!condition.tester.java.isInterface) {
                "Condition tester can't be an interface"
            }
            return runTester(condition.tester)
        }
        val modid = condition.value.ifEmpty { "minecraft" }
        val version = condition.version.ifEmpty { "*" }
        val modVersion = fabric.getModContainer(modid).let {
            if (it.isEmpty)
                return TestResult(false, formatter("not_found").translation(modid))
            it.get().metadata.version
        }
        val result = VersionPredicate.parse(version).test(modVersion)
        val key = if (result) "version_match" else "version_mismatch"
        return TestResult(result, formatter(key).translation(modid, version))
    }

    private fun runTester(tester: KClass<out Tester>): TestResult {
        val impl = tester.objectInstance ?: tester.constructors.firstOrNull { it.parameters.isEmpty() }?.call()
        requireNotNull(impl) {
            "Tester must be a Kotlin object or have a constructor with no arguments to run the check"
        }
        val result = impl.test()
        val key = if (result) "test_success" else "test_fail"
        return TestResult(result, formatter(key).translation(tester.simpleName ?: "???"))
    }
}