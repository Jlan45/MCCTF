package darkflow.mcctf;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import darkflow.mcctf.blocks.BlockFlagCollecter;
import darkflow.mcctf.blocks.BlockFlagGetter;
import darkflow.mcctf.commands.BroadCastCommand;
import darkflow.mcctf.items.ItemFlag;
import darkflow.mcctf.items.ItemFlagCollecter;
import darkflow.mcctf.items.ItemFlagGetter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


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
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> BroadCastCommand.register(dispatcher));
        Registry.register(Registry.ITEM, new Identifier("mcctf","flag"),FLAG);
        Registry.register(Registry.BLOCK, new Identifier("mcctf","flagcollecter"),FLAG_COLLECTER_BLOCK);
        Registry.register(Registry.BLOCK,new Identifier("mcctf","flaggetter"), FLAG_GETTER_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("mcctf","flagcollecter"), FLAG_COLLECTER_ITEM);
        Registry.register(Registry.ITEM, new Identifier("mcctf","flaggetter"), FLAG_GETTER_ITEM);
        //读取salt文件，如果不存在就随机生成8位salt并写入文件中


        LOGGER.info("Mod initialized.");
    }
}
