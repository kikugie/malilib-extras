package dev.kikugie.malilib_extras.api.annotation

/**
 * Excludes a field from being registered.
 *
 * Fields that don't extend [IConfigBase] are always excluded.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Exclude
