package dev.kikugie.malilib_extras.api.config

import dev.kikugie.malilib_extras.impl.config.ConfigBuilderImpl
import dev.kikugie.malilib_extras.util.Translation
import fi.dy.masa.malilib.config.IConfigBase

interface MalilibConfig {
    val id: String
    val version: String
    val name: Translation

    val categories: List<ConfigCategory>
    val groups: Map<String, List<ConfigEntry>>
    val entries: List<ConfigEntry>
    val options: List<IConfigBase>

    companion object {
        fun create(id: String, version: String, init: ConfigBuilder.() -> Unit): MalilibConfig = ConfigBuilderImpl(id, version).apply(init).build()
    }
}