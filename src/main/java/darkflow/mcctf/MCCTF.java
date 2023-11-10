package darkflow.mcctf;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import darkflow.mcctf.blocks.BlockFlagCollecter;
import darkflow.mcctf.blocks.BlockFlagGetter;
import darkflow.mcctf.commands.BroadCastCommand;
import darkflow.mcctf.commands.TestCommand;
import darkflow.mcctf.items.ItemFlag;
import darkflow.mcctf.items.ItemFlagCollecter;
import darkflow.mcctf.items.ItemFlagGetter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static net.minecraft.client.render.entity.feature.TridentRiptideFeatureRenderer.BOX;


public class MCCTF implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger("mcctf");

    public static final ItemFlag FLAG=new ItemFlag();
    public static final BlockFlagGetter FLAG_GETTER_BLOCK=new BlockFlagGetter();
    public static final BlockFlagCollecter FLAG_COLLECTER_BLOCK=new BlockFlagCollecter();
    public static final ItemFlagCollecter FLAG_COLLECTER_ITEM=new ItemFlagCollecter(FLAG_COLLECTER_BLOCK,new FabricItemSettings().maxCount(1));
    public static final ItemFlagGetter FLAG_GETTER_ITEM=new ItemFlagGetter(FLAG_GETTER_BLOCK,new FabricItemSettings().maxCount(1));
    public static final Identifier PLAYER_CONTEST_SCORE = new Identifier("mcctf", "contest_score");




    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> BroadCastCommand.register(dispatcher));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> TestCommand.register(dispatcher));



        Registry.register(Registry.ITEM, new Identifier("mcctf","flag"),FLAG);
        Registry.register(Registry.BLOCK, new Identifier("mcctf","flag_collecter"),FLAG_COLLECTER_BLOCK);
        Registry.register(Registry.BLOCK,new Identifier("mcctf","flag_getter"), FLAG_GETTER_BLOCK);
//        Registry.register(Registry.BLOCK,new Identifier("mcctf","world_creater"),WORLD_CREATER_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("mcctf","flag_collecter"), FLAG_COLLECTER_ITEM);
        Registry.register(Registry.ITEM, new Identifier("mcctf","flag_getter"), FLAG_GETTER_ITEM);

        //读取salt文件，如果不存在就随机生成8位salt并写入文件中



        Registry.register(Registry.CUSTOM_STAT, new Identifier("mcctf","contest_score"), PLAYER_CONTEST_SCORE);
        Stats.CUSTOM.getOrCreateStat(MCCTF.PLAYER_CONTEST_SCORE);
        LOGGER.info("Mod initialized.");
    }
}
