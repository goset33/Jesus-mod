package com.goset33.jesus;

import com.goset33.jesus.event.EveryTickHandler;
import com.goset33.jesus.fluid.WineFluid;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Jesus implements ModInitializer {
    public static final String MOD_ID = "jesus";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Block WINE;
    public static FlowableFluid STILL_WINE;
    public static FlowableFluid FLOWING_WINE;
    public static Item WINE_BUCKET;
    public static Potion WINE_BOTTLE;

    @Override
    public void onInitialize() {
        STILL_WINE = Registry.register(Registries.FLUID, new Identifier(MOD_ID, "wine"), new WineFluid.Still());
        FLOWING_WINE = Registry.register(Registries.FLUID, new Identifier(MOD_ID, "flowing_wine"), new WineFluid.Flowing());
        WINE_BUCKET = Registry.register(Registries.ITEM, new Identifier(MOD_ID, "wine_bucket"),
                new BucketItem(STILL_WINE, new FabricItemSettings().rarity(Rarity.RARE).recipeRemainder(Items.BUCKET).maxCount(1)));
        WINE_BOTTLE = Registry.register(Registries.POTION, new Identifier(MOD_ID, "wine_bottle"), new Potion()); //Текстуру даль нельзя. Может стоит сделать кастомный эффект нужного цвета?

        WINE = Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "wine_block"),
                new FluidBlock(STILL_WINE, FabricBlockSettings.copy(Blocks.WATER)));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((itemGroup) -> itemGroup.add(WINE_BUCKET));

        ClientTickEvents.START_WORLD_TICK.register(new EveryTickHandler());
    }
}
