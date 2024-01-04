package dev.kikugie.malilib_extras.api.config

import dev.kikugie.malilib_extras.impl.config.ConfigData
import dev.kikugie.malilib_extras.impl.config.MalilibInit
import dev.kikugie.malilib_extras.impl.config.ModMenuPlugin
import net.minecraft.client.gui.screen.Screen

/**
 * A global storage for all configs to be registered automatically.
 * @see [MalilibInit]
 * @see [ModMenuPlugin]
 */
object ConfigRegistry {
    private var frozen = false
    internal val data = mutableListOf<ConfigData>()
    internal val configs: Sequence<MalilibConfig>
        get() = data.asSequence().map { it.config }

    /**
     * Adds provided [MalilibConfig] to the registry.
     * Must be called before Malilib loads configs (best done in the mod initializer).
     * @param modmenu Whenever the config screen should be available in [ModMenu](https://modrinth.com/mod/modmenu). Default is `true`
     * @param gui Config screen provider. Accepts the parent screen and current config. Default is [MalilibConfigGui]
     * @throws IllegalArgumentException if config is registered when frozen.
     */
    fun register(config: MalilibConfig, modmenu: Boolean = true, gui: (Screen?) -> Screen) {
        if (frozen) throw IllegalStateException("Config registry is already frozen. Config must be registered in the mod initializer")
        data.add(ConfigData(config, modmenu, gui))
    }

    internal fun freeze() {
        frozen = true
    }
}