package dev.kikugie.malilib_extras.impl.config

import dev.kikugie.malilib_extras.api.config.MalilibConfig
import net.minecraft.client.gui.screen.Screen

data class ConfigData(
    val config: MalilibConfig,
    val modmenu: Boolean,
    val gui: ((Screen?) -> Screen)?
)
