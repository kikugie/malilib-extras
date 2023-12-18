package dev.kikugie.malilib_extras

import dev.kikugie.malilib_extras.impl.config.MalilibInit
import net.fabricmc.api.ClientModInitializer
import org.slf4j.LoggerFactory

object MalilibExtras : ClientModInitializer {
    val LOGGER = LoggerFactory.getLogger("malilib-extras")
    override fun onInitializeClient() {
        MalilibInit.init()
    }
}