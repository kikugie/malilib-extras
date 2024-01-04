package dev.kikugie.malilib_extras.impl.config

import dev.kikugie.malilib_extras.api.config.ConfigEntry
import dev.kikugie.malilib_extras.api.restriction.Restriction
import dev.kikugie.malilib_extras.api.restriction.TestResult
import fi.dy.masa.malilib.config.IConfigBase

data class ConfigEntryImpl(
    override val config: IConfigBase,
    override val restriction: Restriction?,
    override val check: TestResult,
    override val dev: Boolean
) : ConfigEntry