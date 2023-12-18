package dev.kikugie.malilib_extras.api.annotation

/**
 * Specifies tab the annotated class will be assigned to.
 * Categories must be standardized, so it's recommended to use a class with constants.
 *
 * Passing a not annotated class throws [IllegalArgumentException].
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Category(val id: String)
