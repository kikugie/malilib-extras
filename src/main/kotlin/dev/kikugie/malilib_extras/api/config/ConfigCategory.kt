package dev.kikugie.malilib_extras.api.config

import dev.kikugie.malilib_extras.util.TranslationKey

/**
 * Intermediary interface for representing categories in [MalilibConfig].
 */
interface ConfigCategory {
    val id: String
    val name: TranslationKey
    val description: TranslationKey
}