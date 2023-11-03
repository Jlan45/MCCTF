package darkflow.mcctf;

import darkflow.mcctf.blocks.BlockFlagCollecter;
import darkflow.mcctf.blocks.BlockFlagGetter;
import darkflow.mcctf.items.ItemFlag;
import darkflow.mcctf.items.ItemFlagCollecter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MCCTF implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger("mcctf");
//    public static final ItemFlagCollecter FLAG_COLLECTER=new ItemFlagCollecter();

    public static final ItemFlag FLAG=new ItemFlag();
    public static final BlockFlagGetter FLAG_GETTER_BLOCK=new BlockFlagGetter();
    public static final BlockFlagCollecter FLAG_COLLECTER_BLOCK=new BlockFlagCollecter();

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("mcctf","flag"),FLAG);
        Registry.register(Registry.BLOCK, new Identifier("mcctf","flagcollecter"),FLAG_COLLECTER_BLOCK);
        Registry.register(Registry.BLOCK,new Identifier("mcctf","flaggetter"), FLAG_GETTER_BLOCK);
//        Registry.register(Registry.ITEM, new Identifier("mcctf","flagcollecter"), new BlockItem(new BlockFlagCollecter(),new FabricItemSettings()));
        LOGGER.info("Mod initialized.");
    }
}
