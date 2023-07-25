package com.xiaohunao.equipmentbenediction.data.dao;


import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemVerifier {
    private final String id;
    private final String tag;
    private final String class_name;

    public ItemVerifier(String id, String tag, String className) {
        this.id = id;
        this.tag = tag;
        class_name = className;
    }
    public boolean isValid(ResourceLocation item_Key){
        Item item = ForgeRegistries.ITEMS.getValue(item_Key);
        if (item == null){
            return false;
        }
        if (id != null) {
            return item_Key.toString().equals(id);
        }
        if (tag != null) {
            TagKey<Item> itemTag = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(tag));
            ItemStack stack = new ItemStack(item);
            return stack.is(itemTag);
        }
        if (class_name != null) {
            try {
                return Class.forName(class_name).isAssignableFrom(item.getClass());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Json解析错误,verifier找不到类: " + class_name,e);
            }
        }
        return false;
    }
}