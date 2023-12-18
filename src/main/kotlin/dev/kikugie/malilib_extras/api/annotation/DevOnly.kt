package dev.kikugie.malilib_extras.api.annotation

/**
 * Hides the option if not in development environment.
 *
 * The option will still be registered and available to the codebase, but it won't be visible in the config screen.
 */
// TODO
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class DevOnly
