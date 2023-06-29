package com.xiaohunao.equipmentbenediction.data.dao;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeData {
    private final String type;
    private final Modifier modifier;
    private final List<String> slots;

    public AttributeData(String type, Modifier modifier, List<String> slots) {
        this.type = type;
        this.modifier = modifier;
        this.slots = slots;
    }

    public String getType() {
        return type;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public List<String> getSlots() {
        return slots;
    }

    public Map<Attribute, AttributeModifier> getAttributeMap() {
        HashMap<Attribute, AttributeModifier> map = new HashMap<>();
        Attribute value = ForgeRegistries.ATTRIBUTES.getValue(ResourceLocation.tryParse(this.type));
        if (value == null) {
            throw new NullPointerException("Attribute " + this.type + " 是无效的属性！");
        }
        AttributeModifier.Operation operation = AttributeModifier.Operation.fromValue(this.modifier.getOperation());
        AttributeModifier attributeModifier = new AttributeModifier(this.modifier.getUUID(), this.modifier.getName(), this.modifier.getAmount(), operation);
        map.put(value, attributeModifier);
        return map;
    }

    public boolean checkSlots(EquipmentSlot slot) {
        for (String slots : this.slots) {
            if (slots.equals(slot.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "AttributeData{" +
                "type='" + type + '\'' +
                ", modifier=" + modifier +
                ", slots=" + slots +
                '}';
    }
}