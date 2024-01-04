package dev.kikugie.malilib_extras.impl.config

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import dev.kikugie.malilib_extras.api.config.ConfigRegistry

object ModMenuPlugin : ModMenuApi {
    override fun getProvidedConfigScreenFactories(): Map<String, ConfigScreenFactory<*>> =
        ConfigRegistry.data
            .filter { it.modmenu }
            .associate { it.config.id to ConfigScreenFactory { parent -> it.gui?.invoke(parent) } }
}