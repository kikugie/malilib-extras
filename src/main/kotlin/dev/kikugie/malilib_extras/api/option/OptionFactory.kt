package dev.kikugie.malilib_extras.api.option

import dev.kikugie.malilib_extras.impl.option.*
import dev.kikugie.malilib_extras.util.TranslationKey
import fi.dy.masa.malilib.config.IConfigBase
import fi.dy.masa.malilib.config.options.*
import fi.dy.masa.malilib.hotkeys.KeybindSettings
import fi.dy.masa.malilib.util.Color4f

/**
 * Provides a simplified way to create configs.
 * Provided option wrappers are preferred to normal config creation, as it fixes the translation strings as well.
 * @param modid Mod ID of the current config. Used for option name formatting.
 * @param compact Ignores option name provider and uses its key for description instead. Default true.
 * @param defaultNameFormatter Provides translation keys for options. Default `name` if `compact`, else `$modid.config.$name`.
 * @param defaultDescriptionFormatter Provides translation keys for option descriptions. Default `$modid.config.$name` if `compact`, else `modid.config.$name.desc`
 * @see OptionWrapper
 * @see fixTranslations
 */
class OptionFactory(
    private val modid: String,
    compact: Boolean = true,
    private val defaultNameFormatter: (String) -> TranslationKey = if (compact) { name -> name }
    else { name -> "$modid.config.$name" },
    private val defaultDescriptionFormatter: (String) -> TranslationKey = if (compact) { name -> "$modid.config.$name" }
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

    private fun formatters(instance: BaseWrapper<*>) = instance.apply {
        nameTranslation = defaultNameFormatter(name)
        descriptionTranslation = defaultDescriptionFormatter(name)
    }
}