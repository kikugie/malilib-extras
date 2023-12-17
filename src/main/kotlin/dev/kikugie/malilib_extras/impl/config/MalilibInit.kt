package dev.kikugie.malilib_extras.impl.config

import dev.kikugie.malilib_extras.api.config.ConfigRegistry
import fi.dy.masa.malilib.config.ConfigManager
import fi.dy.masa.malilib.event.InitializationHandler
import fi.dy.masa.malilib.event.InputEventHandler
import fi.dy.masa.malilib.hotkeys.*
import fi.dy.masa.malilib.interfaces.IInitializationHandler

object MalilibInit : IKeybindProvider, IKeyboardInputHandler, IMouseInputHandler, IInitializationHandler {
    fun init() {
        InitializationHandler.getInstance().registerInitializationHandler(this)
    }

    override fun addKeysToMap(manager: IKeybindManager) {
        ConfigRegistry.configs.forEach { config ->
            config.options.mapNotNull { it as? IHotkey }.forEach { manager.addKeybindToMap(it.keybind) }
        }
    }

    override fun addHotkeys(manager: IKeybindManager) {
        ConfigRegistry.configs.forEach { config ->
            config.groups.forEach { (cat, entries) ->
                manager.addHotkeysForCategory(config.id, cat, entries.mapNotNull { it as? IHotkey })
            }
        }
    }

    override fun registerModHandlers() {
        ConfigRegistry.freeze()
        ConfigRegistry.configs.forEach { config ->
            ConfigManager.getInstance().registerConfigHandler(config.id, ConfigLoader(config))
            InputEventHandler.getKeybindManager().registerKeybindProvider(this)
        }
    }
}