package dev.kikugie.malilib_extras.api.annotation

/**
 * Hides the option if not in development environment.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.FIELD)
annotation class DevOnly
