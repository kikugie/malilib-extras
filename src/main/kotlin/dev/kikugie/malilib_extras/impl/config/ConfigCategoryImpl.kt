package dev.kikugie.malilib_extras.impl.config

import dev.kikugie.malilib_extras.api.config.ConfigCategory
import dev.kikugie.malilib_extras.util.TranslationKey

open class ConfigCategoryImpl(
    override val id: String,
    override val name: TranslationKey,
    override val description: TranslationKey
) : ConfigCategory