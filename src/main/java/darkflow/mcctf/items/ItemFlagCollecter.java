package darkflow.mcctf.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class ItemFlagCollecter extends Item {
    public ItemFlagCollecter(){
        super(new FabricItemSettings().maxCount(64).rarity(Rarity.EPIC));
    }
}
