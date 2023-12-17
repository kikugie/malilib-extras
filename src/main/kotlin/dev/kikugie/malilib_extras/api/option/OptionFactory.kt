package dev.kikugie.malilib_extras.api.option

import dev.kikugie.malilib_extras.impl.option.*
import fi.dy.masa.malilib.config.IConfigBase
import fi.dy.masa.malilib.config.options.*
import fi.dy.masa.malilib.hotkeys.KeybindSettings
import fi.dy.masa.malilib.util.Color4f

class OptionFactory(
    private val modid: String,
    private val compact: Boolean = true,
    private val defaultNameFormatter: (String) -> String = if (compact) { name -> name } else { name -> "$modid.config.$name" },
    private val defaultDescriptionFormatter: (String) -> String = if (compact) { name -> "$modid.config.$name" }
    else { name -> "$modid.config.$name.desc" }
) {
    // Java users QOL
    constructor(modId: String) : this(modId, true)

    @JvmOverloads
    fun <T : ConfigBoolean> create(name: String, default: Boolean, init: BooleanWrapper.() -> Unit = { }) =
        BooleanWrapper(name, default).apply(::formatters).apply(init).get()

    @JvmOverloads
    fun <T : ConfigInteger> create(name: String, default: Int, init: IntegerWrapper.() -> Unit = { }) =
        IntegerWrapper(name, default).apply(::formatters).apply(init).get()

    @JvmOverloads
    fun <T : ConfigDouble> create(name: String, default: Double, init: DoubleWrapper.() -> Unit = { }) =
        DoubleWrapper(name, default).apply(::formatters).apply(init).get()

    @JvmOverloads
    fun <T : ConfigString> create(name: String, default: String, init: StringWrapper.() -> Unit = { }) =
        StringWrapper(name, default).apply(::formatters).apply(init).get()

    @JvmOverloads
    fun <T : ConfigColor> create(name: String, default: Color4f, init: ColorWrapper.() -> Unit = { }) =
        ColorWrapper(name, default).apply(::formatters).apply(init).get()

    fun <T : ConfigHotkey> create(name: String, keybind: String, init: HotkeyWrapper.() -> Unit) =
        HotkeyWrapper(name, keybind).apply(::formatters).apply(init).get()

    fun <T : ConfigHotkey> create(
        name: String,
        keybind: String,
        settings: KeybindSettings,
        callback: () -> Boolean
    ) = HotkeyWrapper(name, keybind).apply(::formatters).apply {
        this.settings = settings
        this.callback = callback
    }.get()

    fun <T : ConfigBooleanHotkeyed> create(
        name: String,
        default: Boolean,
        keybind: String,
        init: BooleanHotkeyWrapper.() -> Unit
    ) = BooleanHotkeyWrapper(name, default, keybind).apply(::formatters).apply(init).get()

    fun <T : ConfigBooleanHotkeyed> create(
        name: String,
        default: Boolean,
        keybind: String,
        settings: KeybindSettings,
        callback: () -> Boolean
    ) = BooleanHotkeyWrapper(name, default, keybind).apply(::formatters).apply {
        this.settings = settings
        this.callback = callback
    }.get()

    private fun <T : IConfigBase> formatters(instance: BaseWrapper<T>) = instance.apply {
        nameTranslation = defaultNameFormatter(name)
        descriptionTranslation = defaultDescriptionFormatter(name)
    }
}