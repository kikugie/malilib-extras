package dev.kikugie.malilib_extras.api.annotation

/**
 * Specifies tab the option will be available in.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Category(val id: String)
