package com.xiaohunao.equipmentbenediction.data.dao;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.List;

public class AttributeData {
    private final Attribute attribute;
    private final AttributeModifier attributeModifier;
    private final List<String> slots;

    public AttributeData(Attribute attribute, AttributeModifier attributeModifier, List<String> slots) {
        this.attribute = attribute;
        this.attributeModifier = attributeModifier;
        this.slots = slots;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public AttributeModifier getAttributeModifier() {
        return attributeModifier;
    }

    public List<String> getSlots() {
        return slots;
    }
}
