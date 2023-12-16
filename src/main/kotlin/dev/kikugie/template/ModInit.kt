package dev.kikugie.template

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object ModInit : ModInitializer {
    val LOGGER = LoggerFactory.getLogger("template")
    override fun onInitialize() {
        /*?>1.20 {*/
        LOGGER.info("1.20!")
        /*?} else {*//*
        LOGGER.info("1.19!")
        *//*?}*/

        /*? ~1.19 */
        /*LOGGER.info("")*/
    }
}