package com.xiaohunao.equipmentbenediction.attribute;

import com.xiaohunao.equipmentbenediction.registry.AttributesRegister;
import com.xiaohunao.equipmentbenediction.util.AttributeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class SummonLightningAttribute extends BaseAttribute {
    public static final String NAME = "generic.summon_lightning";

    public SummonLightningAttribute() {
        super(NAME);
    }


    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        Entity entity = event.getSource().getEntity();
        if (entity == null) return;
        if (entity.level.isClientSide) return;

        if (entity instanceof LivingEntity living) {
            LivingEntity entityLiving = event.getEntityLiving();
            BlockPos pos = entityLiving.getOnPos();
            float attributeValue = AttributeUtil.getAttributeValue(living, AttributesRegister.SUMMON_LIGHTNING);
            if (new Random().nextFloat() <= attributeValue) {
                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(entityLiving.level);
                if (lightningBolt != null) {
                    lightningBolt.setPos(pos.getX(), pos.getY(), pos.getZ());
                    ;
                    entityLiving.level.addFreshEntity(lightningBolt);
                }
            }
        }
    }

}
