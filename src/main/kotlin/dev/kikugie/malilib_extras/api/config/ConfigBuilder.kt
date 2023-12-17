package dev.kikugie.malilib_extras.api.config

import kotlin.reflect.KClass

interface ConfigBuilder {
    val id: String
    val version: String

    val titleKey: String
    val categoryKey: (String) -> String
    val categoryDescriptionKey: (String) -> String
    fun register(vararg categories: KClass<*>)
    fun register(vararg categories: Class<*>) = register(*categories.map { it.kotlin }.toTypedArray())
}