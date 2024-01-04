package dev.kikugie.malilib_extras.api.restriction

import kotlin.reflect.KClass

/**
 * Condition entry for an option.
 *
 * @property value ID of the mod to check.
 * Defaults to "minecraft" and must be empty if a tester is used.
 * @property version Version predicate of the mod (i.e. ">1.19.4 <1.20.2").
 * Defaults to "*" and must be empty if a tester is used.
 * @property tester A custom tester for the condition.
 * Must be a class implementing [Tester] with an empty constructor.
 */
@Retention(AnnotationRetention.RUNTIME)
annotation class Condition(
    val value: String = "",
    val version: String = "",
    val tester: KClass<out Tester> = Tester::class
)
