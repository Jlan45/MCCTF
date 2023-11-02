package darkflow.mcctf;

import darkflow.mcctf.items.ItemFlag;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class MCCTF implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    public static final Item FLAG_ITEM = new ItemFlag();
    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("mcctf","flag"), FLAG_ITEM);

    }
}
