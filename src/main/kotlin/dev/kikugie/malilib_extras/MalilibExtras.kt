package dev.kikugie.malilib_extras

import dev.kikugie.malilib_extras.impl.config.MalilibInit
import net.fabricmc.api.ClientModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

object MalilibExtras : ClientModInitializer {
    const val MOD_ID = "malilib_extras"
    const val NAME = "Malilib Extras"
    val LOGGER = LoggerFactory.getLogger(NAME)
    override fun onInitializeClient() {
        LOGGER.info("Hello from $NAME!")
        MalilibInit.init()
    }

    fun logger(clazz: KClass<*>): Logger = LoggerFactory.getLogger("$NAME/${clazz.simpleName}")
}