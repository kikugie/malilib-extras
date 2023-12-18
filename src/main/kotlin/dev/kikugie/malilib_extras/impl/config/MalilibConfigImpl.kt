package dev.kikugie.malilib_extras.impl.config

import dev.kikugie.malilib_extras.api.config.ConfigCategory
import dev.kikugie.malilib_extras.api.config.ConfigEntry
import dev.kikugie.malilib_extras.api.config.MalilibConfig
import dev.kikugie.malilib_extras.util.TranslationKey
import fi.dy.masa.malilib.config.IConfigBase

data class MalilibConfigImpl(
    override val id: String,
    override val version: String,
    override val name: TranslationKey,
    override val categories: List<ConfigCategory>,
    override val groups: Map<String, List<ConfigEntry>>,
    override val entries: List<ConfigEntry>
) : MalilibConfig