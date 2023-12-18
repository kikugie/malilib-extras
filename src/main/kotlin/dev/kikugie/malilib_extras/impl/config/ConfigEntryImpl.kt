package dev.kikugie.malilib_extras.impl.config

import dev.kikugie.malilib_extras.api.config.ConfigEntry
import dev.kikugie.malilib_extras.util.restriction.RestrictionChecker
import fi.dy.masa.malilib.config.IConfigBase
import me.fallenbreath.conditionalmixin.api.annotation.Restriction

data class ConfigEntryImpl(
    override val config: IConfigBase,
    override val restriction: Restriction?,
    override val check: RestrictionChecker.Result,
    override val dev: Boolean
) : ConfigEntry