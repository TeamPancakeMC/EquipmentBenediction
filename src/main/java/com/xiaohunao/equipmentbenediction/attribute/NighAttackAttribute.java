package com.xiaohunao.equipmentbenediction.attribute;

import com.xiaohunao.equipmentbenediction.registry.AttributesRegister;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NighAttackAttribute extends DistanceAttackAttribute {
    public static final String NAME = "generic.nigh_distance_attack";

    public NighAttackAttribute() {
        super(NAME);
        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public void damageCalculate(LivingAttackEvent event) {
        LivingEntity target = event.getEntityLiving();
        if (target.level.isClientSide) return;
        if (event.getSource().getEntity() instanceof LivingEntity) {
            super.damageCalculate(event, AttributesRegister.NIGH_DISTANCE_ATTACK.get());
        }
    }
}
