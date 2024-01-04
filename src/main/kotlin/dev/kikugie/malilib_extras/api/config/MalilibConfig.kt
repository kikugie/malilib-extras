package dev.kikugie.malilib_extras.api.config

import dev.kikugie.malilib_extras.api.option.OptionFactory
import dev.kikugie.malilib_extras.impl.config.ConfigBuilderImpl
import dev.kikugie.malilib_extras.util.TranslationKey

/**
 * Represents the finalized options and categories of the config.
 * Define a config category with @[Category] and use [OptionFactory] to add options.
 *
 * ```kt
 *  const val MOD_ID = "example_mod"
 *  const val GENERAL = "general"
 *  const val TWEAKS = "tweaks"
 *
 *  @Category(GENERAL)
 *  object ModConfig {
 *      private val factory = OptionFactory(MOD_ID)
 *
 *      val boolOpt = factory.create<ConfigBoolean>("boolOpt", true)
 *      val intOpt = factory.create<ConfigInteger>("intOpt", 1) {
 *          min = 0
 *          max = 100
 *          slider = true
 *      }
 *  }
 *
 *  object ModInit : ClientModInitializer {
 *      lateinit var config: MalilibConfig
 *          private set
 *
 *      override fun onInitializeClient() {
 *          config = MalilibConfig.create(MOD_ID, "0.1.0") {
 *              register(ModConfig::class)
 *          }
 *          ConfigRegistry.register(config)
 *      }
 *  }
 * ```
 */
interface MalilibConfig {
    /**
     * Config ID used for identifying it. Should match the mod id.
     */
    val id: String

    /**
     * Mod version. Used for config display.
     */
    val version: String

    /**
     * Translation key for the config title. Defaults to `$id.config.title`.
     */
    val name: TranslationKey

    /**
     * All registered categories.
     */
    val categories: List<ConfigCategory>

    /**
     * Maps category IDs to [ConfigEntry]-ies.
     */
    val groups: Map<String, List<ConfigEntry>>

    /**
     * Direct access to all [ConfigEntry]-ies.
     */
    val entries: List<ConfigEntry>

    companion object {
        /**
         * Returns a default [ConfigBuilder]. Use [Kotlin type-safe builders](https://kotlinlang.org/docs/type-safe-builders.html) for configuration.
         */
        fun create(id: String, version: String = "", init: ConfigBuilder.() -> Unit): MalilibConfig =
            ConfigBuilderImpl(id, version).apply(init).build()
    }
}