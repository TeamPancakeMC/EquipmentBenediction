package com.xiaohunao.equipmentbenediction.attribute;

import com.xiaohunao.equipmentbenediction.registry.AttributesRegister;
import com.xiaohunao.equipmentbenediction.util.AttributeUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AbsorbBloodAttribute extends BaseAttribute {
    public static final String NAME = "generic.absorb_blood";

    public AbsorbBloodAttribute() {
        super(NAME);
        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        Entity entity = event.getSource().getEntity();
        if (entity == null) return;
        if (entity.level.isClientSide) return;

        if (entity instanceof LivingEntity livingEntity) {
            float attributeValue = AttributeUtil.getAttributeValue(livingEntity, AttributesRegister.ABSORB_BLOOD.get());
            float amount = event.getAmount();
            livingEntity.heal(amount * attributeValue);
        }
    }
}