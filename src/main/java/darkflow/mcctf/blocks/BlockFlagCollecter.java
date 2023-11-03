package darkflow.mcctf.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
        if(!world.isClient) {
            ItemStack itemStack = player.getHandItems().iterator().next();
            if (itemStack.getItem().getTranslationKey().equals("item.mcctf.flag")) {
                //TODO flag验证
                String userFlag=itemStack.getNbt().getString("flag");
                if (verifyFlag(player, userFlag)) {
                    player.sendMessage(Text.of("Flag验证成功"), false);
                    ItemStack itemStack1 = new ItemStack(Items.AIR);
                    player.setStackInHand(hand, itemStack1);
                }
                else {
                    player.sendMessage(Text.of("Flag验证失败"), false);
                }
            }
        }
        return ActionResult.SUCCESS;

    }
}
