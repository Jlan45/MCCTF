package darkflow.mcctf.blocks;

import net.minecraft.block.Block;

public class BlockFlagGetter extends Block {
    public BlockFlagGetter() {
        super(Settings.of(net.minecraft.block.Material.ICE).dropsNothing().strength(-1f,-1f));
    }

}
