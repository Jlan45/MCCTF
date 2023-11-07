package darkflow.mcctf.blocks;

import darkflow.mcctf.gui.TestGUI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.client.MinecraftClient;

public class TestBlock extends Block {
    public TestBlock(){
        super(Settings.of(Material.ICE));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        MinecraftClient.getInstance().setScreen(new TestGUI());
        return ActionResult.SUCCESS;
    }
}
