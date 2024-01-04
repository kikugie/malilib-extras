package dev.kikugie.malilib_extras.api.restriction


@Retention(AnnotationRetention.RUNTIME)
annotation class Restriction(
    val require: Array<Condition> = [],
    val conflict: Array<Condition> = []
)

