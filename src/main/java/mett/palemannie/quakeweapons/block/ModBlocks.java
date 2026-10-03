package mett.palemannie.quakeweapons.block;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.block.custom.LightWaterBlock;
import mett.palemannie.quakeweapons.block.custom.LightAirBlock;
import mett.palemannie.quakeweapons.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(BuiltInRegistries.BLOCK, QuakeWeapons.MODID);

    public static final DeferredHolder<Block, Block> LIGHT_WATER = BLOCKS.register("light_water", () ->
            new LightWaterBlock(Fluids.WATER, BlockBehaviour.Properties.of().mapColor(MapColor.WATER).replaceable()
                    .noCollission().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable()
                    .liquid().sound(SoundType.EMPTY).lightLevel((x) -> x.getValue(BlockStateProperties.POWER))));

    public static final DeferredHolder<Block, Block> LIGHT_AIR = BLOCKS.register("light_air", () ->
            new LightAirBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AIR)
                    .lightLevel(state -> 15).noCollission().noOcclusion().air()));

    private static <T extends Block> DeferredHolder<Block, T> registerBlock(String name, Supplier<T> block) {
        DeferredHolder<Block, T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredHolder<Item, Item> registerBlockItem(String name, DeferredHolder<Block, T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
