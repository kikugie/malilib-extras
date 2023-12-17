package dev.kikugie.malilib_extras.util

import fi.dy.masa.malilib.util.StringUtils

typealias Translation = () -> String
typealias TranslationTemplate = (String) -> String
object TranslationUtils {
    fun String.translation(vararg args: String): Translation {
        return { StringUtils.translate(this, args) }
    }

    fun String.translationTemplate(vararg args: String): TranslationTemplate {
        return { key -> StringUtils.translate(this.format(key), args) }
    }
}