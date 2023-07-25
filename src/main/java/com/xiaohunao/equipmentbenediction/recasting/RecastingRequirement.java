package com.xiaohunao.equipmentbenediction.recasting;

import com.xiaohunao.equipmentbenediction.data.dao.ItemVerifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class RecastingRequirement {
    private final int count;
    private final List<ItemVerifier> verifiers;

    public RecastingRequirement(int count, List<ItemVerifier> verifiers) {
        this.count = count;
        this.verifiers = verifiers;
    }

    public int getCount() {
        return count;
    }

    public List<ItemVerifier> getVerifiers() {
        return verifiers;
    }

    public boolean isCompleteValid(ItemStack stack) {
        int count = stack.getCount();
        ResourceLocation name = ForgeRegistries.ITEMS.getKey(stack.getItem());
        return verifiers.stream().anyMatch(itemVerifier -> itemVerifier.isValid(name)) && count >= this.count;
    }

    public boolean isValid(ItemStack stack) {
        return verifiers.stream().anyMatch(itemVerifier -> itemVerifier.isValid(ForgeRegistries.ITEMS.getKey(stack.getItem())));
    }
}
