package darkflow.mcctf;

import darkflow.mcctf.blocks.BlockFlagCollecter;
import darkflow.mcctf.items.ItemFlag;
import darkflow.mcctf.items.ItemFlagCollecter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class MCCTF implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("mcctf","flag"), new ItemFlag());
        Registry.register(Registry.BLOCK, new Identifier("mcctf","flagcollecter"),new BlockFlagCollecter());
//        Registry.register(Registry.ITEM, new Identifier("mcctf","flagcollecter"), new BlockItem(new BlockFlagCollecter(),new FabricItemSettings()));
    }
}
