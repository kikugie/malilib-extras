package dev.kikugie.malilib_extras.impl.gui

import dev.kikugie.malilib_extras.api.config.ConfigCategory
import dev.kikugie.malilib_extras.api.config.MalilibConfig
import dev.kikugie.malilib_extras.util.translate
import fi.dy.masa.malilib.gui.GuiConfigsBase
import fi.dy.masa.malilib.gui.button.ButtonBase
import fi.dy.masa.malilib.gui.button.ButtonGeneric
import net.minecraft.client.gui.screen.Screen

class MalilibConfigGui(
    val config: MalilibConfig,
    parent: Screen?
) : GuiConfigsBase(10, 50, config.id, parent, config.name, config.version) {
    private var tab: String = config.categories.first().id
    private val cache = mutableMapOf<String, List<ConfigOptionWrapper>>()

    override fun getConfigs(): List<ConfigOptionWrapper> =
        cache.computeIfAbsent(tab) { key -> config.groups[key]?.map { ConfigOptionWrapper(it.config) } ?: emptyList() }

    override fun initGui() {
        super.initGui()

        var x = 10
        val y = 26

        config.categories.forEach {
            x += createNavigationButton(x, y, it)
        }
    }

    private fun createNavigationButton(x: Int, y: Int, category: ConfigCategory): Int {
        val button = ButtonGeneric(x, y, -1, 20, category.name.translate())
        button.setEnabled(tab != category.id)
        button.setHoverStrings(category.description.translate().split("\n"))
        addButton(button) { _: ButtonBase, _: Int ->
            refresh(tab, category.id) { tab = category.id }
        }
        return button.width + 2
    }

    private fun <T> refresh(currentValue: T, newValue: T, valueSetter: Runnable) {
        if (newValue != currentValue) {
            valueSetter.run()
            reCreateListWidget()
            listWidget?.resetScrollbarPosition()
            initGui()
        }
    }
}