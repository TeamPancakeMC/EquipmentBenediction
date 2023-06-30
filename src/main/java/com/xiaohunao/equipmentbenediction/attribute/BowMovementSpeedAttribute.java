package com.xiaohunao.equipmentbenediction.attribute;

import com.xiaohunao.equipmentbenediction.registry.AttributesRegister;
import com.xiaohunao.equipmentbenediction.util.AttributeUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.BowItem;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber
public class BowMovementSpeedAttribute extends BaseAttribute {

    public static final UUID BOW_MOVEMENT_SPEED_UUID = UUID.fromString("7107DE5E-7CE8-4030-940E-514C1F160890");
    public static final String NAME = "generic.bow_movement_speed";

    public BowMovementSpeedAttribute() {
        super(NAME);
    }

    @SubscribeEvent
    public static void onLivingEntityUseBowStart(LivingEntityUseItemEvent.Start event) {
        if (event.getItem().getItem() instanceof BowItem) {
            LivingEntity entityLiving = event.getEntityLiving();
            AttributeInstance movementSpeed = entityLiving.getAttribute(Attributes.MOVEMENT_SPEED);
            if (movementSpeed == null) return;
            AttributeModifier modifier = movementSpeed.getModifier(BOW_MOVEMENT_SPEED_UUID);
            if (modifier != null) {
                movementSpeed.removeModifier(modifier);
            }
            float attributeValue = AttributeUtil.getAttributeValue(entityLiving, AttributesRegister.BOW_MOVEMENT_SPEED);
            AttributeModifier newModifier = new AttributeModifier(BOW_MOVEMENT_SPEED_UUID, "Bow Movement Speed", attributeValue, AttributeModifier.Operation.MULTIPLY_TOTAL);
            movementSpeed.addTransientModifier(newModifier);
        }
    }

    @SubscribeEvent
    public static void onLivingEntityUseBowFinish(LivingEntityUseItemEvent.Stop event) {
        LivingEntity entityLiving = event.getEntityLiving();
        if (event.getItem().getItem() instanceof BowItem) {
            AttributeInstance movementSpeed = entityLiving.getAttribute(Attributes.MOVEMENT_SPEED);
            if (movementSpeed == null) return;
            AttributeModifier modifier = movementSpeed.getModifier(BOW_MOVEMENT_SPEED_UUID);
            if (modifier != null) {
                movementSpeed.removeModifier(modifier);
            }
        }
    }
}
