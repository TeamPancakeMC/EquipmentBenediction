package com.xiaohunao.equipmentbenediction.attribute;

import com.xiaohunao.equipmentbenediction.registry.AttributesRegister;
import com.xiaohunao.equipmentbenediction.util.AttributeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class SummonLightningAttribute extends BaseAttribute {
    public static final String NAME = "generic.summon_lightning";

    public SummonLightningAttribute() {
        super(NAME);
        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        Entity entity = event.getSource().getEntity();
        if (entity == null) return;
        Level level = entity.level;
        if (level.isClientSide) return;

        if (entity instanceof LivingEntity living) {
            LivingEntity entityLiving = event.getEntityLiving();
            BlockPos pos = entityLiving.getOnPos();
            float attributeValue = AttributeUtil.getAttributeValue(living, AttributesRegister.SUMMON_LIGHTNING.get());
            if (new Random().nextFloat() <= attributeValue) {
                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level);
                if (lightningBolt != null) {
                    lightningBolt.setPos(pos.getX(), pos.getY(), pos.getZ());
                    level.addFreshEntity(lightningBolt);
                }
            }
        }
    }

}
