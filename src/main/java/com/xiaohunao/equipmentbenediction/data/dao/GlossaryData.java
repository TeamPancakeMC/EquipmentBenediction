package com.xiaohunao.equipmentbenediction.data.dao;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.List;
import java.util.Map;

public class GlossaryData {
    private final String id;
    private final int quality_level;
    private final ChatFormatting color;
    private final float chance;
    private final List<ItemVerifier> verifiers;
    private final List<String> slots;
    private final Map<Attribute, AttributeModifier> attributeMap;

    public GlossaryData(String id, int quality_level, ChatFormatting color, float chance, List<String> slots,
                        List<ItemVerifier> verifiers, Map<Attribute, AttributeModifier> attributeMap) {
        this.id = id;
        this.quality_level = quality_level;
        this.color = color;
        this.chance = chance;
        this.verifiers = verifiers;
        this.slots = slots;
        this.attributeMap = attributeMap;
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

    public List<String> getSlots() {
        return slots;
    }

    public Map<Attribute, AttributeModifier> getAttributeMap() {
        return attributeMap;
    }

    public boolean isValid(ResourceLocation key){
        return verifiers.stream().anyMatch(itemVerifier -> itemVerifier.isValid(key));
    }

    @Override
    public String toString() {
        return "GlossaryData{" +
                "id='" + id + '\'' +
                ", quality_level=" + quality_level +
                ", color=" + color +
                ", chance=" + chance +
                ", verifiers=" + verifiers +
                ", slots=" + slots +
                ", attributeMap=" + attributeMap +
                '}';
    }
}
