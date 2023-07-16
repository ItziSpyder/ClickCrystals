package io.github.itzispyder.clickcrystals.mixins;

import io.github.itzispyder.clickcrystals.client.ClickCrystalsSystem;
import io.github.itzispyder.clickcrystals.events.events.KeyPressEvent;
import io.github.itzispyder.clickcrystals.gui.ClickType;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {

    private static final ClickCrystalsSystem system = ClickCrystalsSystem.getInstance();

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        this.handleKeyPress(key, action, ci);
    }

    private void handleKeyPress(int key, int action, CallbackInfo ci) {
        ClickType a = ClickType.of(action);
        KeyPressEvent e = new KeyPressEvent(key, a);

        system.eventBus.pass(e);
        if (e.isCancelled()) ci.cancel();
    }
}
