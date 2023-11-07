package darkflow.mcctf.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import darkflow.mcctf.MCCTF;
import net.minecraft.command.argument.ColorArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static darkflow.mcctf.MCCTF.PLAYER_CONTEST_SCORE;
import static net.minecraft.command.argument.ColorArgumentType.getColor;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class TestCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        dispatcher.register(literal("test")
                .requires(source -> source.hasPermissionLevel(0)) // 只有管理员能够执行命令。命令不会对非操作员或低于 1 级权限的玩家显示在 tab-完成中，也不会让他们执行。
                        .then(argument("message", greedyString())
                                .executes(ctx -> test(ctx.getSource(), getString(ctx, "message"))))); // 你可以在这里处理参数，并处理成命令。
    }

    public static int test(ServerCommandSource source, String message) {
        ServerPlayerEntity player=source.getPlayer();
        //获取玩家死亡次数
        StatHandler sh=player.getStatHandler();
        player.increaseStat(PLAYER_CONTEST_SCORE,1);
        player.resetStat(Stats.CUSTOM.getOrCreateStat(PLAYER_CONTEST_SCORE));
        int score=sh.getStat(Stats.CUSTOM.getOrCreateStat(PLAYER_CONTEST_SCORE));
        player.sendMessage(Text.of("你的分数是"+score),false);
        return Command.SINGLE_SUCCESS; // 成功
    }
}
