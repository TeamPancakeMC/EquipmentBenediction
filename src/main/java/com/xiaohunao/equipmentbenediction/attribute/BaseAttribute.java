package com.xiaohunao.equipmentbenediction.attribute;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class BaseAttribute extends RangedAttribute {
    public BaseAttribute(String name, double defaultValue, double minValue, double maxValue) {
        super("attribute.name." + name, defaultValue, minValue, maxValue);
        this.setRegistryName(name);
        this.setSyncable(true);
    }
    public BaseAttribute(String name) {
        super("attribute.name." + name, 0.0, 0.0, 1.0);
        this.setRegistryName(name);
        this.setSyncable(true);
    }

}
