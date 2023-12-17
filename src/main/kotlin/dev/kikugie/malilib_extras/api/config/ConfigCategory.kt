package dev.kikugie.malilib_extras.api.config

import dev.kikugie.malilib_extras.util.Translation

interface ConfigCategory {
    val id: String
    val name: Translation
    val description: Translation
}