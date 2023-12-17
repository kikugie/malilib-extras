package dev.kikugie.malilib_extras.api.option

import fi.dy.masa.malilib.config.IConfigBase

interface OptionWrapper<T : IConfigBase> {
    fun get(): T
}