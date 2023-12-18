package dev.kikugie.malilib_extras.api.option

import fi.dy.masa.malilib.config.IConfigBase

/**
 * Allows specifying additional data and transforming the option before returning it.
 */
interface OptionWrapper<T : IConfigBase> {
    fun get(): T
}