package dev.kikugie.malilib_extras.impl.config

import dev.kikugie.malilib_extras.api.config.ConfigCategory
import dev.kikugie.malilib_extras.api.config.ConfigEntry
import dev.kikugie.malilib_extras.api.config.MalilibConfig
import dev.kikugie.malilib_extras.util.Translation
import fi.dy.masa.malilib.config.IConfigBase

class MalilibConfigImpl(
    override val id: String,
    override val version: String,
    override val name: Translation,
    override val categories: List<ConfigCategory>,
    override val groups: Map<String, List<ConfigEntry>>,
    override val entries: List<ConfigEntry>
) : MalilibConfig {
    override val options: List<IConfigBase> = entries.map { it.config }
}