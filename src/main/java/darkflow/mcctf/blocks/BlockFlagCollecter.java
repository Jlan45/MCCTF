package darkflow.mcctf.blocks;

import darkflow.mcctf.MCCTF;
import darkflow.mcctf.gui.TestGUI;
import darkflow.mcctf.models.Question;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                String questionUUID=itemStack.getNbt().getString("question");
                Question tmpQuestion=new Question("test", UUID.randomUUID().toString(),1000,"","");
                try {
                    if (verifyFlag(player,tmpQuestion, userFlag)) {
                        player.sendMessage(Text.of("Flag验证成功"), false);
                        player.increaseStat(MCCTF.PLAYER_CONTEST_SCORE,tmpQuestion.getScore());
                        ItemStack itemStack1 = new ItemStack(Items.AIR);
                        player.setStackInHand(hand, itemStack1);
                    }
                    else {
                        player.sendMessage(Text.of("Flag验证失败"), false);
                    }
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ActionResult.SUCCESS;

    }
}
