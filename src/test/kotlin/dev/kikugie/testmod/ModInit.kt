package dev.kikugie.testmod

import dev.kikugie.malilib_extras.api.config.ConfigRegistry
import dev.kikugie.malilib_extras.api.config.MalilibConfig
import net.fabricmc.api.ModInitializer

object ModInit : ModInitializer {
    private val MOD_ID = "testmod"
    lateinit var config: MalilibConfig
        private set
    override fun onInitialize() {
        config = MalilibConfig.create(MOD_ID, "0.0.0") {
            titleKey = "$id.title"
            register(ModConfig::class)
        }
        ConfigRegistry.register(config)
    }
}