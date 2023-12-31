package dev.kikugie.malilib_extras.impl.config

import dev.kikugie.malilib_extras.MalilibExtras
import dev.kikugie.malilib_extras.api.config.Category
import dev.kikugie.malilib_extras.api.config.Exclude
import dev.kikugie.malilib_extras.api.config.ConfigBuilder
import dev.kikugie.malilib_extras.api.config.ConfigEntry
import dev.kikugie.malilib_extras.api.config.MalilibConfig
import dev.kikugie.malilib_extras.api.restriction.Restriction
import dev.kikugie.malilib_extras.api.restriction.RestrictionChecker
import fi.dy.masa.malilib.config.IConfigBase
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

class ConfigBuilderImpl(override val id: String, override var version: String = "") : ConfigBuilder {
    override var titleKey: String = "$id.config.title"
    override var categoryKey: (String) -> String = { "$id.config.title.$it" }
    override var categoryDescriptionKey: (String) -> String = { "$id.config.title.$it.desc" }
    override var restrictionChecker: RestrictionChecker = RestrictionChecker.standard

    private val categories = mutableMapOf<String, MutableList<ConfigEntry>>().withDefault { ArrayList() }
    private val options = ArrayList<ConfigEntry>()
    override fun register(vararg categories: KClass<*>) {
        categories.forEach { processClass(it) }
    }

    fun build(): MalilibConfig {
        val cats = categories.map { (key, _) ->
            ConfigCategoryImpl(
                key,
                categoryKey(key),
                categoryDescriptionKey(key)
            )
        }
        return MalilibConfigImpl(
            id,
            version,
            titleKey,
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

        val restriction = field.findAnnotation<Restriction>()
        return ConfigEntryImpl(
            value,
            restriction,
            restrictionChecker.testRestriction(restriction),
            false
        )
    }

    override fun toString() = "${this::class.simpleName.toString()}(" + buildString {
        append("id = $id, ")
        append("version = $version, ")
        append("titleKey = $titleKey, ")
        append("categoryKey = ${categoryKey("\$it")}, ")
        append("categoryDescriptionKey = ${categoryDescriptionKey("\$it")}, ")
        append("categories = $categories, ")
        append("options = $options, ")
    } + ")"

    companion object {
        private val LOGGER = MalilibExtras.logger(this::class)
    }
}