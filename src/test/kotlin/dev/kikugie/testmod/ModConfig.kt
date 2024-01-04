package dev.kikugie.testmod

import dev.kikugie.malilib_extras.api.config.Category
import dev.kikugie.malilib_extras.api.option.OptionFactory
import fi.dy.masa.malilib.config.options.ConfigBoolean
import fi.dy.masa.malilib.config.options.ConfigInteger

const val GENERAL = "general"
const val TWEAKS = "tweaks"

@Category(GENERAL)
object ModConfig {
    private val factory = OptionFactory("test")

    val boolOpt = factory.create<ConfigBoolean>("boolOpt", true)
    val intOpt1 = factory.create<ConfigInteger>("intOpt1", 1)
    val intOpt2 = factory.create<ConfigInteger>("intOpt2", 1) {
        min = 0
        max = 10
        slider = true
    }
}