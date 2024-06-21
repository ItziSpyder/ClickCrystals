package io.github.itzispyder.clickcrystals.util.minecraft;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public final class NbtUtils {

    public static int getEnchantLvL(ItemStack stack, Enchantment enchant) {
        NbtList list = stack.getEnchantments();
        Identifier enchantId = Registries.ENCHANTMENT.getId(enchant);
        int len = list.size();

        if (enchantId == null || len == 0)
            return 0;

        for (int i = 0; i < len; i++) {
            NbtCompound nbt = list.getCompound(i);
            Identifier nbtId = Identifier.tryParse(nbt.getString("id"));
            if (enchantId.equals(nbtId))
                return nbt.getInt("lvl");
        }
        return 0;
    }
}
