package dev.kikugie.malilib_extras.api.option

import dev.kikugie.malilib_extras.impl.option.*
import dev.kikugie.malilib_extras.util.TranslationKey
import fi.dy.masa.malilib.config.options.*
import fi.dy.masa.malilib.util.Color4f

/**
 * Provides a simplified way to create configs.
 * Provided option wrappers are preferred to normal config creation, as they fix option translation strings as well.
 * @param nameFormatter Provides translation keys for options. Default `name` if `compact`, else `$modid.config.$name`.
 * @param descriptionFormatter Provides translation keys for option descriptions. Default `$modid.config.$name` if `compact`, else `modid.config.$name.desc`
 * @see OptionWrapper
 * @see [BaseWrapper.fixTranslations]
 */
class OptionFactory(
    private val nameFormatter: (String) -> TranslationKey,
    private val descriptionFormatter: (String) -> TranslationKey
) {
    companion object {
        /**
         * Creates a factory that doesn't format option names.
         * Sets description keys to "$modid.config.$option".
         *
         * For custom formatters use the primary constructor.
         * @param modid Mod ID used for formatting.
         */
        fun compact(modid: String) = OptionFactory(
            { it },
            { "$modid.config.$it" }
        )

        /**
         * Creates a factory with translated option names and descriptions.
         * Name keys are set to "$modid.config.$option", and description to "$modid.config.$option.desc".
         *
         * For custom formatters use the primary constructor.
         * @param modid Mod ID used for formatting.
         */
        fun pretty(modid: String) = OptionFactory(
            { "$modid.config.$it" },
            { "$modid.config.$it.desc" }
        )
    }

    fun <T : ConfigBoolean> create(name: String, default: Boolean, init: BooleanWrapper.() -> Unit = { }) =
        BooleanWrapper(name, default).apply(::formatters).apply(init).get()

    fun <T : ConfigInteger> create(name: String, default: Int, init: IntegerWrapper.() -> Unit = { }) =
        IntegerWrapper(name, default).apply(::formatters).apply(init).get()

    fun <T : ConfigDouble> create(name: String, default: Double, init: DoubleWrapper.() -> Unit = { }) =
        DoubleWrapper(name, default).apply(::formatters).apply(init).get()

    fun <T : ConfigString> create(name: String, default: String, init: StringWrapper.() -> Unit = { }) =
        StringWrapper(name, default).apply(::formatters).apply(init).get()

    fun <T : ConfigColor> create(name: String, default: Color4f, init: ColorWrapper.() -> Unit = { }) =
        ColorWrapper(name, default).apply(::formatters).apply(init).get()

    fun <T : ConfigHotkey> create(
        name: String,
        keybind: String,
        callback: () -> Unit,
        init: HotkeyWrapper.() -> Unit
    ) =
        HotkeyWrapper(name, keybind)
            .apply(::formatters)
            .apply { this.callback = callback }
            .apply(init)
            .get()

    fun <T : ConfigHotkey> create(
        name: String,
        keybind: String,
        callback: () -> Unit
    ) = HotkeyWrapper(name, keybind).apply(::formatters).apply {
        this.callback = callback
    }.get()

    fun <T : ConfigBooleanHotkeyed> create(
        name: String,
        default: Boolean,
        keybind: String,
        callback: () -> Unit,
        init: BooleanHotkeyWrapper.() -> Unit
    ) = BooleanHotkeyWrapper(name, default, keybind)
        .apply(::formatters)
        .apply { this.callback = callback }
        .apply(init)
        .get()

    fun <T : ConfigBooleanHotkeyed> create(
        name: String,
        default: Boolean,
        keybind: String,
        callback: () -> Unit
    ) = BooleanHotkeyWrapper(name, default, keybind).apply(::formatters).apply {
        this.callback = callback
    }.get()

    private fun formatters(instance: BaseWrapper<*>) = instance.apply {
        nameTranslation = nameFormatter(name)
        descriptionTranslation = descriptionFormatter(name)
    }
}