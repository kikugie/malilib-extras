package dev.kikugie.malilib_extras.mixin.malilib;

import fi.dy.masa.malilib.config.options.ConfigBase;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Some options don't have a pretty name parameter in public constructors.
 * This allows modifying it after the option is created.
 */
@Environment(EnvType.CLIENT)
@Mixin(value = ConfigBase.class, remap = false)
public interface ConfigBaseAccessor {
    @Mutable
    @Accessor("prettyName")
    void setPrettyName(String prettyName);
}
