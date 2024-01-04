package dev.kikugie.malilib_extras.mixin.malilib;

import dev.kikugie.malilib_extras.access.ConfigCommentAccessor;
import fi.dy.masa.malilib.config.options.ConfigBase;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Option comments in Malilib are hardcoded to "config.comment.$name". This allows overriding the key.
 */
@Environment(EnvType.CLIENT)
@Mixin(value = ConfigBase.class, remap = false)
public abstract class ConfigBaseMixin implements ConfigCommentAccessor {
    @Shadow
    private String comment;
    @Nullable
    @Unique
    private String commentPath;

    @Shadow
    public abstract String getName();

    @Override
    public void malilib_extras$setCommentPath(String key) {
        this.commentPath = key;
    }

    @Override
    public String malilib_extras$getRawComment() {
        return this.comment;
    }

    @ModifyArg(method = "getComment", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/util/StringUtils;getTranslatedOrFallback(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;"), index = 0)
    private String modifyCommentPath(String original) {
        return this.commentPath == null ? original : this.commentPath;
    }
}