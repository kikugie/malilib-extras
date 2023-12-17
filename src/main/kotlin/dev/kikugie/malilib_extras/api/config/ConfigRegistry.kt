package dev.kikugie.malilib_extras.api.config

import dev.kikugie.malilib_extras.impl.config.ConfigData
import dev.kikugie.malilib_extras.impl.gui.MalilibConfigGui
import net.minecraft.client.gui.screen.Screen

object ConfigRegistry {
    private var frozen = false
    internal val data = mutableListOf<ConfigData>()
    internal val configs: Sequence<MalilibConfig>
        get() = data.asSequence().map { it.config }

    fun register(config: MalilibConfig, modmenu: Boolean = true, gui: (Screen?) -> Screen = { MalilibConfigGui(config, it) }) {
        if (frozen) throw IllegalStateException("Config registry is already frozen. Config must be registered in the mod initializer")
        data.add(ConfigData(config, modmenu, gui))
    }

    internal fun freeze() {
        frozen = true
    }
}