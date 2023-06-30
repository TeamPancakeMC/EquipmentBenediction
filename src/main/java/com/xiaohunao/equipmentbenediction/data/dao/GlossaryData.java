package com.xiaohunao.equipmentbenediction.data.dao;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class GlossaryData {
    private final String id;
    private final int quality_level;
    private final String color;
    private final float chance;
    private final List<ItemVerifier> verifiers;
    private final List<AttributeData> attributes;

    public GlossaryData(String id, int quality_level, String color,
                        float chance, List<ItemVerifier> verifiers, List<AttributeData> attributes) {
        this.id = id;
        this.quality_level = quality_level;
        this.color = color;
        this.chance = chance;
        this.verifiers = verifiers;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public int getQuality_level() {
        return quality_level;
    }

    public String getColor() {
        return color;
    }

    public float getChance() {
        return chance;
    }

    public List<ItemVerifier> getVerifiers() {
        return verifiers;
    }

    public List<AttributeData> getAttribute() {
        return attributes;
    }
    public boolean isValid(ItemStack stack) {
        return isValid(stack.getItem().getRegistryName());
    }

    public boolean isValid(ResourceLocation id) {
        for (ItemVerifier verifier : this.verifiers) {
            if (verifier.isValid(id)) {
                return true;
            }
        }
        return false;
    }
}
