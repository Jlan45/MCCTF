package darkflow.mcctf.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static darkflow.mcctf.MCCTF.FLAG;

public class BlockWorldCreater extends Block {
    public BlockWorldCreater() {
        super(Settings.of(net.minecraft.block.Material.ICE).dropsNothing().strength(-1f,-1f));
    }

//    @Override
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        if(world.isClient) {
//            this.open
//        }
//
//        return ActionResult.SUCCESS;
//
//
//    }
}
