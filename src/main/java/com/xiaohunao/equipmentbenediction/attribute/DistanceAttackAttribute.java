package com.xiaohunao.equipmentbenediction.attribute;


import com.xiaohunao.equipmentbenediction.registry.AttributesRegister;
import com.xiaohunao.equipmentbenediction.util.AttributeUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;



public class DistanceAttackAttribute extends BaseAttribute{

    public DistanceAttackAttribute(String name) {
        super(name);
    }

    public void damageCalculate(LivingAttackEvent event, Attribute attributeName) {
        Entity entity = event.getSource().getEntity();
        if (entity == null) return;
        Level level = entity.level();
        if (level.isClientSide) return;

        if (entity instanceof LivingEntity attack) {
            LivingEntity target = event.getEntity();

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
            if (attributeName.equals(AttributesRegister.NIGH_DISTANCE_ATTACK.get())) {
                damage = maxAttack * (1 - distance / 10);
            } else if (attributeName.equals(AttributesRegister.FAR_DISTANCE_ATTACK.get())) {
                damage = maxAttack * distance / 10;
            }
            target.hurt(level.damageSources().generic(), (float) damage);
        }
    }
}

