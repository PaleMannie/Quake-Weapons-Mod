package mett.palemannie.quakeweapons.block;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.block.custom.LightAirBlock;
import mett.palemannie.quakeweapons.block.custom.LightWaterBlock;
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
            new LightWaterBlock(Fluids.WATER, BlockBehaviour.Properties.of().setId(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.BLOCK, net.minecraft.resources.Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "light_water")))
                    .mapColor(MapColor.WATER).replaceable().noCollision().strength(100.0F)
                    .pushReaction(PushReaction.POPPED).noLootTable().liquid().sound(SoundType.EMPTY)
                    .lightLevel((x)
                            -> x.getValue(BlockStateProperties.POWER))));

    public static final DeferredHolder<Block, Block> LIGHT_AIR =
            BLOCKS.register("light_air", () ->
                    new LightAirBlock(BlockBehaviour.Properties.of().setId(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.BLOCK, net.minecraft.resources.Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "light_air")))
                            .replaceable().noCollision().noLootTable().air().randomTicks().lightLevel((x)
                                    -> x.getValue(BlockStateProperties.POWER)).noLootTable().air()));


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
