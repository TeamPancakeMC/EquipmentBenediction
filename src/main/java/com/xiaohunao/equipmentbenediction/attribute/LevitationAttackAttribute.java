package com.xiaohunao.equipmentbenediction.attribute;

import com.xiaohunao.equipmentbenediction.registry.AttributesRegister;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LevitationAttackAttribute extends BaseEffectAttribute{
    public static final String NAME = "generic.levitation_attack";
    public LevitationAttackAttribute() {
        super(NAME);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    Attribute getAttribute() {
        return AttributesRegister.LEVITATION_ATTACK.get();
    }

    @Override
    MobEffect getMobEffect() {
        return MobEffects.LEVITATION;
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
