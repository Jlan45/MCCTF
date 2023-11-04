package darkflow.mcctf.commands;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.ColorArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.minecraft.command.argument.ColorArgumentType.getColor;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;
// Import everything in the CommandManager
import static net.minecraft.server.command.CommandManager.*;

import static net.minecraft.server.command.CommandManager.*;


public final class SetQuestionCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        dispatcher.register(literal("setquestion")
                .requires(source -> source.hasPermissionLevel(2)) // 只有管理员能够执行命令。命令不会对非操作员或低于 1 级权限的玩家显示在 tab-完成中，也不会让他们执行。
                .then(argument("color", ColorArgumentType.color())
                        .then(argument("message", greedyString())
                                .executes(ctx -> broadcast(ctx.getSource(), getColor(ctx, "color"), getString(ctx, "message")))))); // 你可以在这里处理参数，并处理成命令。
    }

    public static int broadcast(ServerCommandSource source, Formatting formatting, String message) {
        Text text = Text.literal(message).formatted(formatting);
        source.getServer().getPlayerManager().broadcast(text, false); // 广播消息
        return Command.SINGLE_SUCCESS; // 成功
    }
}