package mett.palemannie.quakeweapons.block;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.block.custom.LightWaterBlock;
import mett.palemannie.quakeweapons.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/*
* Credit goes to AtomicStrykers Dynamic Lights mod
* https://github.com/AtomicStryker/atomicstrykers-minecraft-mods/tree/1.21.4/DynamicLights
* https://github.com/AtomicStryker/atomicstrykers-minecraft-mods
*/

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, QuakeWeapons.MODID);


    /*public static final RegistryObject<Block> LIGHT_WATER = BLOCKS.register("light_water", () ->
            new LightWaterBlock(Fluids.WATER, BlockBehaviour.Properties.of().setId(BLOCKS.key("light_water"))
                    .mapColor(MapColor.WATER).replaceable().noCollission().strength(100.0F)
                    .pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)
                    .lightLevel((x)
                            -> x.getValue(BlockStateProperties.POWER))));*/

    public static final RegistryObject<Block> LIGHT_WATER = BLOCKS.register("light_water", () ->
            new LightWaterBlock(Fluids.WATER, BlockBehaviour.Properties.of().setId(BLOCKS.key("light_water"))
                    .mapColor(MapColor.WATER).replaceable().noCollision().strength(100.0F)
                    .pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)
                    .lightLevel((x)
                            -> x.getValue(BlockStateProperties.POWER))));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, name)))));
    }

    public static void register(BusGroup eventBus) {
        BLOCKS.register(eventBus);
    }
}
