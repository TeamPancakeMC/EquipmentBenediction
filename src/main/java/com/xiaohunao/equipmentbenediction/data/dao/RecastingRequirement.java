package com.xiaohunao.equipmentbenediction.data.dao;

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
        for (ItemVerifier verifier : verifiers) {
            System.out.println("isCompleteValid name : " + name);
            if (verifier.isValid(name)) {
                boolean b = count >= this.count;
                System.out.println("isCompleteValid  count: " + b);
                if (b) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValid(ItemStack stack) {
        for (ItemVerifier verifier : verifiers) {
            if (verifier.isValid(ForgeRegistries.ITEMS.getKey(stack.getItem()))) {
                return true;
            }
        }
        return false;
    }
}
