package dev.kikugie.malilib_extras.impl.config

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dev.kikugie.malilib_extras.MalilibExtras
import dev.kikugie.malilib_extras.api.config.MalilibConfig
import dev.kikugie.malilib_extras.util.FabricUtils
import fi.dy.masa.malilib.config.ConfigUtils
import fi.dy.masa.malilib.config.IConfigHandler
import fi.dy.masa.malilib.util.JsonUtils
import kotlin.io.path.exists
import kotlin.io.path.isReadable
import kotlin.io.path.isRegularFile
import kotlin.io.path.reader

class ConfigLoader(val config: MalilibConfig) : IConfigHandler {
    private val LOGGER = MalilibExtras.logger(this::class)
    override fun load() {
        LOGGER.debug("Loading config ${config.id}")
        val file = FabricUtils.fabric.configDir.resolve("${config.id}.json")
        if (!file.exists() || !file.isRegularFile() || !file.isReadable())
            LOGGER.debug("Config file {} doesn't exist, skipping", file)
        else try {
            loadConfig(JsonParser.parseReader(file.reader()).asJsonObject)
        } catch (e: Exception) {
            LOGGER.warn("Failed to parse config file $file: ", e)
        }
    }

    private fun loadConfig(json: JsonObject) {
        config.categories.forEach { cat ->
            ConfigUtils.readConfigBase(json, cat.id, config.groups[cat.id]?.map { it.config } ?: throw IllegalStateException("Ghost category ${cat.id}"))
        }
    }

    override fun save() {
        LOGGER.debug("Saving config ${config.id}")
        val file = FabricUtils.fabric.configDir.resolve("${config.id}.json")
        JsonUtils.writeJsonToFile(saveConfig(), file.toFile())
    }

    private fun saveConfig(): JsonObject {
        val json = JsonObject()
        config.categories.forEach { cat ->
            ConfigUtils.writeConfigBase(json, cat.id, config.groups[cat.id]?.map { it.config } ?: throw IllegalStateException("Ghost category ${cat.id}"))
        }
        return json
    }
}