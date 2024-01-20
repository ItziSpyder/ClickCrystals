package io.github.itzispyder.clickcrystals.mixins;

import io.github.itzispyder.clickcrystals.Global;
import io.github.itzispyder.clickcrystals.gui.hud.Hud;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.modules.modules.rendering.NoOverlay;
import io.github.itzispyder.clickcrystals.modules.modules.rendering.NoScoreboard;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud implements Global {

    @Inject(method = "renderSpyglassOverlay", at = @At("HEAD"), cancellable = true)
    public void renderSpyglassOverlay(MatrixStack context, float scale, CallbackInfo ci) {
        if (Module.isEnabled(NoOverlay.class)) {
            ci.cancel();
        }
    }

    @Inject(method = "renderPortalOverlay", at = @At("HEAD"), cancellable = true)
    public void renderPortalOverlay(MatrixStack context, float nauseaStrength, CallbackInfo ci) {
        if (Module.isEnabled(NoOverlay.class)) {
            ci.cancel();
        }
    }

    @Inject(method = "renderVignetteOverlay", at = @At("HEAD"), cancellable = true)
    public void renderVignetteOverlay(MatrixStack context, Entity entity, CallbackInfo ci) {
        if (Module.isEnabled(NoOverlay.class)) {
            ci.cancel();
        }
    }

    @Inject(method = "renderOverlay", at = @At("HEAD"), cancellable = true)
    public void renderOverlay(MatrixStack context, Identifier texture, float opacity, CallbackInfo ci) {
        if (!texture.getPath().contains("pumpkinblur")) {
            return;
        }
        if (Module.isEnabled(NoOverlay.class)) {
            ci.cancel();
        }
    }

    @Inject(method = "renderScoreboardSidebar", at = @At("HEAD"), cancellable = true)
    public void renderOverlay(MatrixStack context, ScoreboardObjective objective, CallbackInfo ci) {
        if (Module.isEnabled(NoScoreboard.class)) {
            ci.cancel();
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void renderHuds(MatrixStack context, float tickDelta, CallbackInfo ci) {
        if (mc.currentScreen != null) {
            return;
        }
        for (Hud hud : system.huds().values()) {
            if (hud.canRender()) {
                hud.render(context);
            }
        }
    }
}
