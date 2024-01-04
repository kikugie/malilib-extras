package dev.kikugie.malilib_extras.api.restriction

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
annotation class Condition(
    val value: String = "",
    val version: String = "",
    val tester: KClass<out Tester> = Tester::class
)
