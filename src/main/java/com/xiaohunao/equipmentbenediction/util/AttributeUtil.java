package com.xiaohunao.equipmentbenediction.util;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AttributeUtil {
    public static float getAttributeValue(@Nonnull LivingEntity livingEntity, @Nonnull Attribute attribute) {
        double base = attribute.getDefaultValue();
        AttributeInstance instance = livingEntity.getAttribute(attribute);
        if (instance != null) {
            base = instance.getValue();
        }
        List<Double> zero = new ArrayList<>();
        List<Double> one = new ArrayList<>();
        List<Double> two = new ArrayList<>();
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack stack = livingEntity.getItemBySlot(equipmentSlot);
            Multimap<Attribute, AttributeModifier> attributeModifiers = stack.getAttributeModifiers(equipmentSlot);
            Collection<AttributeModifier> modifiers = attributeModifiers.get(attribute);
            for (AttributeModifier attributeModifier : modifiers) {
                int operationValue = attributeModifier.getOperation().toValue();
                double amount = attributeModifier.getAmount();
                if (operationValue == 0) {
                    zero.add(amount);
                } else if (operationValue == 1) {
                    one.add(amount);
                } else {
                    two.add(amount);
                }

            }
        }
        
        for (double amount : zero) {
            base += amount;
        }
        for (double amount : one) {
            base += base * amount;
        }

        for (double amount : two) {
            base *= 1.0D + amount;
        }
        return (float) base;
    }
}