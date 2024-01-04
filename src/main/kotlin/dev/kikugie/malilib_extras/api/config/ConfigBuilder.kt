package dev.kikugie.malilib_extras.api.config

import kotlin.reflect.KClass
import dev.kikugie.malilib_extras.api.restriction.RestrictionChecker
import dev.kikugie.malilib_extras.util.TranslationKey

/**
 * Interface for setting up [MalilibConfig].
 * @see [MalilibConfig.Companion.create].
 */
interface ConfigBuilder {
    /**
     * Mod ID used for identifying the config and translation templates.
     */
    val id: String

    /**
     * Mod version. Used for config display.
     */
    var version: String

    /**
     * Translation key for the config title. Defaults to `$id.config.title`.
     */
    var titleKey: TranslationKey
    /**
     * Translation key formatter for config categories. Defaults to `$id.config.title.$category`.
     */
    var categoryKey: (String) -> TranslationKey
    /**
     * Translation key formatter for category descriptions. Defaults to `$id.config.title.$category.desc`.
     */
    var categoryDescriptionKey: (String) -> TranslationKey

    /**
     * [RestrictionChecker] used by the builder. Defaults to [RestrictionChecker.standard]
     */
    var restrictionChecker: RestrictionChecker

    /**
     * Registers fields from a config class. Class **must** be annotated with @[Category].
     */
    fun register(vararg categories: KClass<*>)
    /**
     * Registers fields from a java config class.
     */
    fun register(vararg categories: Class<*>) = register(*categories.map { it.kotlin }.toTypedArray())
}