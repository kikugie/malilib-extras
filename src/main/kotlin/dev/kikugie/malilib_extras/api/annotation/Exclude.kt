package dev.kikugie.malilib_extras.api.annotation

/**
 * Excludes a field from being registered
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Exclude
