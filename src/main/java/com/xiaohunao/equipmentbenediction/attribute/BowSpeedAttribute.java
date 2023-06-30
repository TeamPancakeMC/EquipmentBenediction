package com.xiaohunao.equipmentbenediction.attribute;

import com.xiaohunao.equipmentbenediction.registry.AttributesRegister;
import com.xiaohunao.equipmentbenediction.util.AttributeUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BowSpeedAttribute extends BaseAttribute{
    public static final String NAME = "generic.bow_speed";
    public BowSpeedAttribute() {
        super(NAME);
    }

    @SubscribeEvent
    public static void onLivingEntityUseItem(LivingEntityUseItemEvent.Start event) {
        ItemStack stack = event.getItem();
        if (stack.getItem() instanceof BowItem) {
            LivingEntity entityLiving = event.getEntityLiving();
            float attributeValue = AttributeUtil.getAttributeValue(entityLiving, AttributesRegister.BOW_SPEED);
            int duration = event.getDuration();
            duration -= attributeValue * duration;
            event.setDuration(duration);
        }
    }
}
