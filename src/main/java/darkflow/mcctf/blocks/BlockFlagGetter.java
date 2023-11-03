package darkflow.mcctf.blocks;

import darkflow.mcctf.items.ItemFlag;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static darkflow.mcctf.MCCTF.FLAG;

public class BlockFlagGetter extends Block {
    public BlockFlagGetter() {
        super(Settings.of(net.minecraft.block.Material.ICE).dropsNothing().strength(-1f,-1f));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient) {
            NbtCompound tag = new NbtCompound();
            tag.putString("flag", "FLAG{TEST}");
            ItemStack itemStack = new ItemStack(FLAG);
            itemStack.setNbt(tag);
            player.giveItemStack(itemStack);
        }
//        else{
//            player.sendMessage(Text.of("要在服务器上跑才可以哦"));
//        }
        return ActionResult.SUCCESS;


    }
}
