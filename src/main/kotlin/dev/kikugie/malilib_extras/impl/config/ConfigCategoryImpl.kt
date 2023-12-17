package dev.kikugie.malilib_extras.impl.config

import dev.kikugie.malilib_extras.api.config.ConfigCategory
import dev.kikugie.malilib_extras.util.Translation

open class ConfigCategoryImpl(
    override val id: String,
    override val name: Translation,
    override val description: Translation
) : ConfigCategory