package dev.kikugie.malilib_extras.api.restriction

/**
 * A set of [Condition]s for an option.
 *
 * @property require Conditions that must succeed.
 * @property conflict Conditions that must fail.
 */
@Retention(AnnotationRetention.RUNTIME)
annotation class Restriction(
    val require: Array<Condition> = [],
    val conflict: Array<Condition> = []
)

