package io.github.itzispyder.clickcrystals.modules.modules.anchoring;

import io.github.itzispyder.clickcrystals.events.EventHandler;
import io.github.itzispyder.clickcrystals.events.Listener;
import io.github.itzispyder.clickcrystals.events.events.PacketSendEvent;
import io.github.itzispyder.clickcrystals.modules.Categories;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.util.BlockUtils;
import io.github.itzispyder.clickcrystals.util.HotbarUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.math.BlockPos;

public class AnchorSwitch extends Module implements Listener {

    public AnchorSwitch() {
        super("AnchorSwitch", Categories.ANCHORING,"Whenever you place an anchor, switch to glowstone then back after it has been charged.");
    }

    @Override
    protected void onEnable() {
        system.addListener(this);
    }

    @Override
    protected void onDisable() {
        system.removeListener(this);
    }

    /**
     * Module function
     * @param e packet send event
     */
    @EventHandler
    private void onPacketSend(PacketSendEvent e) {
        if (e.getPacket() instanceof PlayerInteractBlockC2SPacket packet) {
            final BlockPos pos = packet.getBlockHitResult().getBlockPos();
            final BlockState state = mc.player.getWorld().getBlockState(pos);

            if (state == null) return;
            if (!HotbarUtils.has(Items.GLOWSTONE)) return;
            if (!HotbarUtils.has(Items.RESPAWN_ANCHOR)) return;

            try {
                if (HotbarUtils.isHolding(Items.RESPAWN_ANCHOR)) {
                    if (!state.isOf(Blocks.RESPAWN_ANCHOR)) HotbarUtils.search(Items.GLOWSTONE);
                    else {
                        int charges = state.get(RespawnAnchorBlock.CHARGES);
                        if (charges >= 1) return;
                        e.setCancelled(true);
                        HotbarUtils.search(Items.GLOWSTONE);
                        BlockUtils.interact(pos,packet.getBlockHitResult().getSide());
                    }
                }
                else if (HotbarUtils.isHolding(Items.GLOWSTONE)) {
                    if (!state.isOf(Blocks.RESPAWN_ANCHOR)) {
                        e.setCancelled(true);
                        HotbarUtils.search(Items.RESPAWN_ANCHOR);
                        BlockUtils.interact(pos,packet.getBlockHitResult().getSide());
                        HotbarUtils.search(Items.GLOWSTONE);
                    }
                    else HotbarUtils.search(Items.RESPAWN_ANCHOR);
                }
            } catch (Exception ignore) {}
        }
    }
}
