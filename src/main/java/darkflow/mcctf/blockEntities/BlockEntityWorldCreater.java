package darkflow.mcctf.blockEntities;

import darkflow.mcctf.MCCTF;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.MinecraftClient;

public class BlockEntityWorldCreater extends BlockEntity {
    public BlockEntityWorldCreater(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
