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
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, QuakeWeapons.MODID);


    public static final RegistryObject<Block> LIGHT_WATER = BLOCKS.register("light_water", () ->
            new LightWaterBlock(Fluids.WATER, BlockBehaviour.Properties.of().setId(BLOCKS.key("light_water"))
                    .mapColor(MapColor.WATER).replaceable().noCollision().strength(100.0F)
                    .pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)
                    .lightLevel((x)
                            -> x.getValue(BlockStateProperties.POWER))));

    public static final RegistryObject<Block> LIGHT_AIR =
            BLOCKS.register("light_air", () ->
                    new LightAirBlock(BlockBehaviour.Properties.of().setId(BLOCKS.key("light_air"))
                            .replaceable().noCollision().noLootTable().air().randomTicks().lightLevel((x)
                                    -> x.getValue(BlockStateProperties.POWER)).noLootTable().air()));


    public static void register(BusGroup eventBus) {
        BLOCKS.register(eventBus);
    }
}
