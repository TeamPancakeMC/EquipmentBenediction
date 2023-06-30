package com.xiaohunao.equipmentbenediction.attribute;

import com.xiaohunao.equipmentbenediction.registry.AttributesRegister;
import com.xiaohunao.equipmentbenediction.util.AttributeUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DistanceAttackAttribute {
    public static void damageCalculate(LivingAttackEvent event, Attribute attributeName) {
        Entity entity = event.getSource().getEntity();
        if (entity == null) return;
        if (entity.level.isClientSide) return;

        if (entity instanceof LivingEntity attack) {
            LivingEntity target = event.getEntityLiving();

            double x1 = attack.getX();
            double y1 = attack.getY();
            double z1 = attack.getZ();
            double x2 = target.getX();
            double y2 = target.getY();
            double z2 = target.getZ();
            double distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
            target.invulnerableTime = 0;
            float maxHealth = target.getMaxHealth();

            float value = AttributeUtil.getAttributeValue(attack, attributeName);
            float maxAttack = maxHealth * value;
            double damage = 0;
            if (attributeName.equals(AttributesRegister.NIGH_DISTANCE_ATTACK)) {
                damage = maxAttack * (1 - distance / 10);
            } else if (attributeName.equals(AttributesRegister.FAR_DISTANCE_ATTACK)) {
                damage = maxAttack * distance / 10;
            }
            target.hurt(DamageSource.GENERIC, (float) damage);
        }
    }

    @Mod.EventBusSubscriber
    public static class Nigh extends BaseAttribute {
        public static final String NAME = "generic.nigh_distance_attack";

        public Nigh() {
            super(NAME);
        }


        @SubscribeEvent
        public static void damageCalculate(LivingAttackEvent event) {
            LivingEntity target = event.getEntityLiving();
            if (target.level.isClientSide) return;
            if (event.getSource().getEntity() instanceof LivingEntity) {
                DistanceAttackAttribute.damageCalculate(event, AttributesRegister.NIGH_DISTANCE_ATTACK);
            }
        }
    }

    @Mod.EventBusSubscriber
    public static class Far extends BaseAttribute {
        public static final String NAME = "generic.far_distance_attack";

        public Far() {
            super(NAME);
        }

        @SubscribeEvent
        public static void damageCalculate(LivingAttackEvent event) {
            LivingEntity target = event.getEntityLiving();
            if (target.level.isClientSide) return;
            if (event.getSource().getEntity() instanceof LivingEntity) {
                DistanceAttackAttribute.damageCalculate(event, AttributesRegister.FAR_DISTANCE_ATTACK);
            }
        }
    }
}
