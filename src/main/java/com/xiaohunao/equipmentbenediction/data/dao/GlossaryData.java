package com.xiaohunao.equipmentbenediction.data.dao;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;


import java.util.List;


public class GlossaryData {
    private final String id;
    private final int quality_level;
    private final ChatFormatting color;
    private final float chance;
    private final List<ItemVerifier> verifiers;
    private final List<AttributeData> attributeDataList;


    public GlossaryData(String id, int quality_level, ChatFormatting color, float chance, List<ItemVerifier> verifiers, List<AttributeData> attributeDataList) {
        this.id = id;
        this.quality_level = quality_level;
        this.color = color;
        this.chance = chance;
        this.verifiers = verifiers;
        this.attributeDataList = attributeDataList;
    }

    public String getId() {
        return id;
    }

    public int getQuality_level() {
        return quality_level;
    }

    public ChatFormatting getColor() {
        return color;
    }

    public float getChance() {
        return chance;
    }

    public List<ItemVerifier> getVerifiers() {
        return verifiers;
    }

    public List<AttributeData> getAttributeDataList() {
        return attributeDataList;
    }

    public boolean isValid(ResourceLocation key){
        return verifiers.stream().anyMatch(itemVerifier -> itemVerifier.isValid(key));
    }
}
