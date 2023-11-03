package darkflow.mcctf.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.tag.TagBuilder;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class ItemFlag extends Item {
    public ItemFlag() {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
    }



    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient) {
            if(context.getPlayer()!=null){
                context.getPlayer().sendMessage(Text.of("Flag应该放到Flag箱里"));
            }
        }
        return ActionResult.SUCCESS;
    }


//    @Override
//    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
//        // 默认为白色文本
//        tooltip.add(Text.of("flag{thisisflag}"));
//    }

}
