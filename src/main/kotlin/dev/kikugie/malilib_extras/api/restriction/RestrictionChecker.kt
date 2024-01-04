package dev.kikugie.malilib_extras.api.restriction

import dev.kikugie.malilib_extras.impl.restriction.RestrictionCheckerImpl

interface RestrictionChecker {
    fun testRestriction(restriction: Restriction?): TestResult
    fun testCondition(condition: Condition): TestResult

    companion object {
        val standard = RestrictionCheckerImpl
    }
}