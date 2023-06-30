package com.xiaohunao.equipmentbenediction.attribute;

import com.xiaohunao.equipmentbenediction.registry.AttributesRegister;
import com.xiaohunao.equipmentbenediction.util.AttributeUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ExecuteAttribute extends BaseAttribute {
    public static final String NAME = "generic.execute";

    public ExecuteAttribute() {
        super(NAME);
    }


    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        Entity entity = event.getSource().getEntity();
        if (entity == null) return;
        if (entity.level.isClientSide) return;

        if (entity instanceof LivingEntity livingEntity) {
            float attributeValue = AttributeUtil.getAttributeValue(livingEntity, AttributesRegister.EXECUTE);
            LivingEntity entityLiving = event.getEntityLiving();
            if (entityLiving == null) return;
            float health = entityLiving.getHealth();
            float maxHealth = entityLiving.getMaxHealth();
            if (health / maxHealth <= attributeValue) {
                entityLiving.invulnerableTime = 0;
                entityLiving.hurt(DamageSource.GENERIC, maxHealth - health);
            }
        }
    }
}
