package com.xiaohunao.equipmentbenediction.data.dao;

import com.xiaohunao.equipmentbenediction.recasting.RecastingRequirement;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;


public class QualityData {
    private final String id;
    private final ChatFormatting color;
    private final float chance;
    private final int level;
    private final int count;
    private final List<ItemVerifier> verifiers;
    private final List<RecastingRequirement> recastingRequirement;

    public QualityData(String id, ChatFormatting color, float chance, int level, int count,
                       List<ItemVerifier> verifiers, List<RecastingRequirement> recastingRequirement) {
        this.id = id;
        this.color = color;
        this.chance = chance;
        this.level = level;
        this.count = count;
        this.verifiers = verifiers;
        this.recastingRequirement = recastingRequirement;
    }

    public String getId() {
        return id;
    }

    public ChatFormatting getColor() {
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

    public List<ItemVerifier> getVerifiers() {
        return verifiers;
    }

    public List<RecastingRequirement> getRecastingRequirement() {
        return recastingRequirement;
    }

    public boolean isValid(ResourceLocation key){
        return verifiers.stream().anyMatch(itemVerifier -> itemVerifier.isValid(key));
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
}
