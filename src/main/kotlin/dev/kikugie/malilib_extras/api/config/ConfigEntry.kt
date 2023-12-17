package dev.kikugie.malilib_extras.api.config

import dev.kikugie.malilib_extras.util.restriction.RestrictionChecker
import fi.dy.masa.malilib.config.IConfigBase
import me.fallenbreath.conditionalmixin.api.annotation.Restriction

interface ConfigEntry {
    val config: IConfigBase
    val restriction: Restriction?
    val check: RestrictionChecker.Result
    val dev: Boolean
}