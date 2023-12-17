package dev.kikugie.malilib_extras.impl.config

import dev.kikugie.malilib_extras.api.annotation.Category
import dev.kikugie.malilib_extras.api.annotation.DevOnly
import dev.kikugie.malilib_extras.api.annotation.Exclude
import dev.kikugie.malilib_extras.api.config.ConfigBuilder
import dev.kikugie.malilib_extras.api.config.ConfigEntry
import dev.kikugie.malilib_extras.api.config.MalilibConfig
import dev.kikugie.malilib_extras.util.TranslationUtils.translation
import dev.kikugie.malilib_extras.util.restriction.SimpleRestrictionChecker
import fi.dy.masa.malilib.config.IConfigBase
import me.fallenbreath.conditionalmixin.api.annotation.Restriction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

open class ConfigBuilderImpl(final override val id: String, override val version: String) : ConfigBuilder {
    override val titleKey: String = "$id.config.title"
    override val categoryKey: (String) -> String = { "$id.config.title.$it" }
    override val categoryDescriptionKey: (String) -> String = { "$id.config.title.$it.desc" }

    private val categories = mutableMapOf<String, MutableList<ConfigEntry>>().withDefault { ArrayList() }
    private val options = ArrayList<ConfigEntry>()
    override fun register(vararg categories: KClass<*>) {
        categories.forEach { processClass(it) }
    }

    fun build(): MalilibConfig {
        val cats = categories.map { (key, _) ->
            ConfigCategoryImpl(
                key,
                categoryKey(key).translation(),
                categoryDescriptionKey(key).translation()
            )
        }
        return MalilibConfigImpl(
            id,
            version,
            titleKey.translation(),
            cats,
            categories,
            options
        )
    }

    @Throws(IllegalArgumentException::class)
    protected fun processClass(clazz: KClass<*>) {
        val category = clazz.findAnnotation<Category>()
            ?: throw IllegalArgumentException("Config class ${clazz.simpleName} is missing @Category annotation")
        clazz.declaredMemberProperties.forEach { prop ->
            val entry = processField(clazz, prop) ?: return@forEach
            options.add(entry)
            categories.computeIfAbsent(category.id) { ArrayList() }.add(entry)
        }
    }

    protected fun processField(clazz: KClass<*>, field: KProperty<*>): ConfigEntry? {
        val fullName = "${clazz.simpleName}.${field.name}"
        field.findAnnotation<Exclude>()?.also {
            LOGGER.debug("Field $fullName is disabled, skipping")
            return null
        }

        val value = try {
            field.getter.call(clazz.objectInstance)
        } catch (e: Exception) {
            LOGGER.warn("Failed to get value of field $fullName")
            return null
        }

        if (value !is IConfigBase) {
            LOGGER.debug("Field $fullName doesn't implement IConfigBase, skipping")
            return null
        }

        return ConfigEntryImpl(
            value,
            field.findAnnotation<Restriction>(),
            SimpleRestrictionChecker.test(field),
            field.findAnnotation<DevOnly>() != null
        )
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(ConfigBuilder::class.simpleName)
    }
}