@file:Suppress("KotlinConstantConditions", "MemberVisibilityCanBePrivate")

package dev.kikugie.malilib_extras.impl.option

import dev.kikugie.malilib_extras.api.option.BaseWrapper
import fi.dy.masa.malilib.config.options.*
import fi.dy.masa.malilib.hotkeys.KeybindSettings
import fi.dy.masa.malilib.util.Color4f

class BooleanWrapper(name: String, val default: Boolean) : BaseWrapper<ConfigBoolean>(name) {
    override fun get() = ConfigBoolean(name, default, descriptionTranslation, nameTranslation).apply {
        fixTranslations()
    }
}

class IntegerWrapper(name: String, val default: Int) : BaseWrapper<ConfigInteger>(name) {
    var min = Int.MIN_VALUE
    var max = Int.MAX_VALUE
    var slider = false
    override fun get() = ConfigInteger(name, default, min, max, slider, descriptionTranslation).apply {
        fixTranslations()
    }
}

class DoubleWrapper(name: String, val default: Double) : BaseWrapper<ConfigDouble>(name) {
    var min = Double.MIN_VALUE
    var max = Double.MAX_VALUE
    var slider = false
    override fun get() = ConfigDouble(name, default, min, max, slider, descriptionTranslation).apply {
        fixTranslations()
    }
}

class StringWrapper(name: String, val default: String) : BaseWrapper<ConfigString>(name) {
    override fun get() = ConfigString(name, default, descriptionTranslation).apply {
        fixTranslations()
    }
}

class ColorWrapper(name: String, val default: Color4f) : BaseWrapper<ConfigColor>(name) {
    override fun get() = ConfigColor(name, String.format("#%08X", default.intValue), descriptionTranslation).apply {
        fixTranslations()
    }
}

class HotkeyWrapper(name: String, val keybind: String) : BaseWrapper<ConfigHotkey>(name) {
    var settings: KeybindSettings = KeybindSettings.DEFAULT
    var callback: () -> Unit = {}
    override fun get() = ConfigHotkey(name, keybind, settings, descriptionTranslation).apply {
        fixTranslations()
        keybind.setCallback { _, _ -> callback(); true }
    }
}

class BooleanHotkeyWrapper(name: String, val default: Boolean, val keybind: String) : BaseWrapper<ConfigBooleanHotkeyed>(name) {
    var settings: KeybindSettings = KeybindSettings.DEFAULT
    var callback: () -> Unit = {}
    override fun get() =
        ConfigBooleanHotkeyed(name, default, keybind, settings, descriptionTranslation, nameTranslation).apply {
            fixTranslations()
            keybind.setCallback { _, _ -> callback(); true }
        }
}