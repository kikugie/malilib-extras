package dev.kikugie.malilib_extras.util.restriction

import me.fallenbreath.conditionalmixin.api.annotation.Restriction
import kotlin.reflect.KProperty
import kotlin.reflect.full.findAnnotation

interface RestrictionChecker {
    fun test(restriction: Restriction): Result
    fun test(field: KProperty<*>): Result = field.findAnnotation<Restriction>()?.let { test(it) } ?: DEFAULT

    data class Result(val success: Boolean, val reasons: List<() -> String> = listOf())
    companion object {
        val DEFAULT = Result(true)
    }
}