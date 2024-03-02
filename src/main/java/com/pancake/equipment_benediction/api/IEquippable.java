package com.pancake.equipment_benediction.api;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;



public interface IEquippable<T> {
    boolean checkEquippable(Player player,Ingredient ingredient);

    T getSlotType();
}
