package com.xiaohunao.equipmentbenediction.data.dao;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;


public class QualityData {
    private final String id;
    private final List<ItemVerifier> verifiers;
    private final String color;
    private final float chance;
    private final int level;
    private final int count;
    private final List<RecastingRequirement> recastingRequirement;


    public QualityData(String id, List<ItemVerifier> verifiers, String color, float chance, int level, int count, List<RecastingRequirement> recastingRequirement) {
        this.id = id;
        this.verifiers = verifiers;
        this.color = color;
        this.chance = chance;
        this.level = level;
        this.count = count;
        this.recastingRequirement = recastingRequirement;
    }

    public String getId() {
        return id;
    }

    public List<ItemVerifier> getVerifiers() {
        return verifiers;
    }

    public String getColor() {
        return color;
    }

    public float getChance() {
        return chance;
    }

    public int getLevel() {
        return level;
    }

    public int getCount() {
        return count;
    }

    public List<RecastingRequirement> getRecastingRequirement() {
        return recastingRequirement;
    }

    public boolean isValid(ItemStack stack) {
        return isValid(stack.getItem().getRegistryName());
    }

    public boolean isValid(ResourceLocation id) {
        for (ItemVerifier verifier : verifiers) {
            if (verifier.isValid(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean Recasting(ItemStack stack) {
        for (RecastingRequirement requirement : this.recastingRequirement) {
            if (requirement.isCompleteValid(stack)) {
                stack.shrink(requirement.getCount());
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "EquipmentQualityData{" +
                "id='" + id + '\'' +
                ", verifiers=" + verifiers +
                ", color='" + color + '\'' +
                ", chance=" + chance +
                ", level=" + level +
                ", count=" + count +
                '}';
    }
}