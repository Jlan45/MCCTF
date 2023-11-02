package darkflow.mcctf.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static darkflow.mcctf.api.funcs.verifyFlag;

public class BlockFlagCollecter extends Block {
    public BlockFlagCollecter() {
        super(Settings.of(Material.ICE).hardness(-1f).dropsNothing());
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Iterable<ItemStack> itemStacks=player.getHandItems();
        for (ItemStack itemStack : itemStacks) {
            if(itemStack.getItem().getTranslationKey().equals("item.mcctf.flag")){
                //TODO flag验证
                if(verifyFlag(player,"testflag")){
                    player.sendMessage(Text.of("Flag验证成功"),false);
                    player.getInventory().setStack(player.getInventory().selectedSlot,null);
                }
            }
        }
        player.sendMessage(Text.of("Hello, world!"), false);

        return ActionResult.SUCCESS;
    }
}
