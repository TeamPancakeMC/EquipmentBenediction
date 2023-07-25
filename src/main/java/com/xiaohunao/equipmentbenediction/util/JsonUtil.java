package com.xiaohunao.equipmentbenediction.util;

import com.google.common.collect.Lists;

import com.google.gson.*;
import com.xiaohunao.equipmentbenediction.data.dao.AttributeData;
import com.xiaohunao.equipmentbenediction.data.dao.GlossaryData;
import com.xiaohunao.equipmentbenediction.data.dao.ItemVerifier;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public class JsonUtil {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(GlossaryData.class, new AttributeDataDeserializer())
            .registerTypeAdapter(ChatFormatting.class, new ChatFormattingDeserializer())
            .setPrettyPrinting()
            .create();

    public static class AttributeDataDeserializer implements JsonDeserializer<GlossaryData> {
        @Override
        public GlossaryData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context){
            JsonObject asJsonObject = json.getAsJsonObject();

            String id = asJsonObject.get("id").getAsString();
            int quality_level = asJsonObject.get("quality_level").getAsInt();
            String color = asJsonObject.get("color").getAsString();

            ChatFormatting chatFormatting = GSON.fromJson(color, ChatFormatting.class);

            float chance = asJsonObject.get("chance").getAsFloat();
            List<ItemVerifier> verifiers = Lists.newArrayList();
            asJsonObject.get("verifiers").getAsJsonArray().iterator().forEachRemaining(jsonElement -> {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                verifiers.add(GSON.fromJson(jsonObject, ItemVerifier.class));
            });

            List<AttributeData> attributeDataList = Lists.newArrayList();
            asJsonObject.get("attributes").getAsJsonArray().iterator().forEachRemaining(jsonElement -> {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                String type = jsonObject.get("type").getAsString();

                JsonObject modifier = jsonObject.get("modifier").getAsJsonObject();
                String name = modifier.get("name").getAsString();
                double amount = modifier.get("amount").getAsDouble();
                int operation = modifier.get("operation").getAsInt();
                UUID uuid = UUID.nameUUIDFromBytes((name + amount + operation).getBytes());

                Attribute attribute = ForgeRegistries.ATTRIBUTES.getValue(ResourceLocation.tryParse(type));
                if (attribute == null) {
                    throw new NullPointerException("Attribute " + type + " 是无效的属性！");
                }
                AttributeModifier.Operation operationValue = AttributeModifier.Operation.fromValue(operation);
                AttributeModifier attributeModifier = new AttributeModifier(uuid, name, amount, operationValue);

                List<String> slots = Lists.newArrayList();
                JsonArray slotsArray = jsonObject.get("slots").getAsJsonArray();
                slotsArray.forEach(slot -> slots.add(slot.getAsString()));

                AttributeData attributeData = new AttributeData(attribute, attributeModifier, slots);
                attributeDataList.add(attributeData);
            });

            return new GlossaryData(id, quality_level, chatFormatting, chance, verifiers,attributeDataList);
        }
    }

    public static class ChatFormattingDeserializer implements JsonDeserializer<ChatFormatting> {

        @Override
        public ChatFormatting deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return ChatFormatting.valueOf(json.getAsString().toUpperCase());
        }
    }
}
