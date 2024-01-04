package dev.kikugie.malilib_extras.util

import fi.dy.masa.malilib.util.StringUtils

/**
 * Use to indicate that a string is meant to be a translation key.
 */
typealias TranslationKey = String
fun TranslationKey.translate(vararg args: String): String {
    return StringUtils.translate(this, args)
}

fun TranslationKey.translation(vararg args: String): () -> String {
    return { StringUtils.translate(this, args) }
}