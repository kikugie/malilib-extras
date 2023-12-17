package dev.kikugie.malilib_extras.impl.option

import dev.kikugie.malilib_extras.api.option.OptionWrapper
import dev.kikugie.malilib_extras.util.TranslationTemplate
import fi.dy.masa.malilib.config.IConfigBase

abstract class BaseWrapper<T : IConfigBase>(val name: String) : OptionWrapper<T> {
    lateinit var nameTranslation: String
    lateinit var descriptionTranslation: String
}