package dev.kikugie.testmod

import dev.kikugie.malilib_extras.api.config.ConfigRegistry
import dev.kikugie.malilib_extras.api.config.MalilibConfig
import net.fabricmc.api.ModInitializer

object ModInit : ModInitializer {
    private val MOD_ID = "testmod"
    private lateinit var config: MalilibConfig
    override fun onInitialize() {
        config = MalilibConfig.create(MOD_ID, "0.0.0") {
            register(ModConfig::class)
        }
        ConfigRegistry.register(config)
    }
}