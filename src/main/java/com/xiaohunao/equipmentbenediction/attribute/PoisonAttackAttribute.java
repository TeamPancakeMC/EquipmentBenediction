package com.xiaohunao.equipmentbenediction.attribute;


import com.xiaohunao.equipmentbenediction.registry.AttributesRegister;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class PoisonAttackAttribute extends BaseEffectAttribute{
    public static final String NAME = "generic.poison_attack";
    public PoisonAttackAttribute() {
        super(NAME);
    }

    @Override
    Attribute getAttribute() {
        return AttributesRegister.ATTACK_POISON;
    }

    @Override
    MobEffect getMobEffect() {
        return MobEffects.POISON;
    }

    @Override
    int getDuration() {
        return 60;
    }

    @Override
    int getLevel() {
        return 1;
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        super.onLivingAttack(event);
    }
}
