package dev.kikugie.malilib_extras.api.restriction

import dev.kikugie.malilib_extras.impl.restriction.RestrictionCheckerImpl
import dev.kikugie.malilib_extras.api.config.ConfigBuilder

/**
 * Used in [ConfigBuilder] to add conditional options.
 */
interface RestrictionChecker {
    /**
     * Checks a passed [Restriction].
     * All conditions in [Restriction.require] must succeed and all in [Restriction.conflict] must fail for this check to pass.
     *
     * @param restriction [Restriction] to test.
     * @return [TestResult]`(true)` if all conditions are satisfied,
     * or [TestResult]`(false, reasons)` with reasons passed from failed condition.
     */
    fun testRestriction(restriction: Restriction?): TestResult

    /**
     * Checks a single condition. Whenever it's a requirement or a conflict must be checked in [testRestriction].
     * Result should include a reason even if the check succeeds, because it's inverted for conflicts.
     *
     * @param condition [Condition] to test.
     * @return [TestResult]`(result, reasons)` for this condition.
     */
    fun testCondition(condition: Condition): TestResult

    companion object {
        val standard = RestrictionCheckerImpl
    }
}