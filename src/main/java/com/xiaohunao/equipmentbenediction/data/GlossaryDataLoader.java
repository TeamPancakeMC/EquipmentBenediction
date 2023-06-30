package com.xiaohunao.equipmentbenediction.data;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.xiaohunao.equipmentbenediction.EquipmentBenediction;
import com.xiaohunao.equipmentbenediction.data.dao.AttributeData;
import com.xiaohunao.equipmentbenediction.data.dao.GlossaryData;
import com.xiaohunao.equipmentbenediction.data.dao.QualityData;
import com.xiaohunao.equipmentbenediction.registry.CapabilityRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class GlossaryDataLoader extends SimpleJsonResourceReloadListener {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private Map<String, GlossaryData> glossaryDataHashMap;

    public GlossaryDataLoader() {
        super(GSON, "glossary");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> p_10793_, ResourceManager p_10794_, ProfilerFiller p_10795_) {
        Map<String, GlossaryData> glossaryDataHashMap = Maps.newHashMap();
        for (Map.Entry<ResourceLocation, JsonElement> entry : p_10793_.entrySet()) {
            try {
                GlossaryData glossaryData = GSON.fromJson(entry.getValue(), GlossaryData.class);
                glossaryDataHashMap.put(glossaryData.getId(), glossaryData);
            } catch (IllegalArgumentException | JsonParseException exception) {
                throw new JsonParseException("解析词缀Json数据包错误" + entry.getKey(), exception);
            }
        }
        this.glossaryDataHashMap = glossaryDataHashMap;
    }

    public Map<String, GlossaryData> getGlossaryDataHashMap() {
        return glossaryDataHashMap;
    }

    public List<GlossaryData> getFilteredGlossary(ItemStack stack) {
        List<GlossaryData> glossaryDataList = new ArrayList<>();
        stack.getCapability(CapabilityRegistry.QUALITY).ifPresent(qualityCap -> {
            String id = qualityCap.getId();
            QualityData qualityData = EquipmentBenediction.QUALITY_DATA.get(id);

            int count = qualityData.getCount();
            int level = qualityData.getLevel();

            for (int i = 0; i < count; i++) {
                glossaryDataList.add(getRandomGlossaryData());
            }
            glossaryDataList.removeIf(glossaryData -> glossaryData.getQuality_level() > level);
            glossaryDataList.removeIf(glossaryData -> !glossaryData.isValid(stack.getItem().getRegistryName()));
            filterAttribute(glossaryDataList);
        });
        return glossaryDataList;
    }

    public GlossaryData get(String glossaryID) {
        return glossaryDataHashMap.get(glossaryID);
    }


    public GlossaryData getRandomGlossaryData() {
        GlossaryData glossary = null;
        List<GlossaryData> glossaryDataList = glossaryDataHashMap.values().stream().toList();
        do {
            for (GlossaryData glossaryData : glossaryDataList) {
                float chance = glossaryData.getChance();
                if (Math.random() < chance) {
                    glossary = glossaryData;
                }
            }
        } while (glossary == null);
        return glossary;
    }

    public void filterAttribute(List<GlossaryData> glossaryDataList) {
        Set<String> set = new HashSet<>();
        Iterator<GlossaryData> iterator = glossaryDataList.iterator();
        while (iterator.hasNext()) {
            GlossaryData glossaryData = iterator.next();
            List<AttributeData> attributeDataList = glossaryData.getAttribute();
            boolean isDuplicate = false;
            for (AttributeData attributeData : attributeDataList) {
                String type = attributeData.getType();
                if (set.contains(type)) {
                    isDuplicate = true;
                    break;
                } else {
                    set.add(type);
                }
            }
            if (isDuplicate) {
                iterator.remove();
            }
        }
    }
}
