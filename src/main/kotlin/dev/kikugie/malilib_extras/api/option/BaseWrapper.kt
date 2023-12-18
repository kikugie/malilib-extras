package dev.kikugie.malilib_extras.api.option

import dev.kikugie.malilib_extras.access.ConfigCommentAccessor
import dev.kikugie.malilib_extras.mixin.malilib.ConfigBaseAccessor
import fi.dy.masa.malilib.config.options.ConfigBase

abstract class BaseWrapper<T : ConfigBase<*>>(val name: String) : OptionWrapper<T> {
    lateinit var nameTranslation: String
    lateinit var descriptionTranslation: String

    fun T.fixTranslations() {
        (this as ConfigCommentAccessor).`malilib_extras$setCommentPath`(this@BaseWrapper.descriptionTranslation)
        (this as ConfigBaseAccessor).setPrettyName(this@BaseWrapper.nameTranslation)
    }
}