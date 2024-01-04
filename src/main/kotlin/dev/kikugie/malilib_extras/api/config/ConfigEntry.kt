package dev.kikugie.malilib_extras.api.config

import dev.kikugie.malilib_extras.api.restriction.Restriction
import dev.kikugie.malilib_extras.api.restriction.TestResult
import fi.dy.masa.malilib.config.IConfigBase

/**
 * A wrapper for [IConfigBase] in [MalilibConfig] containing extra metadata about the option.
 */
interface ConfigEntry {
    val config: IConfigBase
    val restriction: Restriction?
    val check: TestResult
    val dev: Boolean
}