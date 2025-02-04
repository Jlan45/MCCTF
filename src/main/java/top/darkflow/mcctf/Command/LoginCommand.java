package top.darkflow.mcctf.Command;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.darkflow.mcctf.Model.CTFPlayer;

public class LoginCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length != 1){
                player.sendMessage("Usage: /login <password>");
                return false;
            }
            CTFPlayer ctfPlayer = new CTFPlayer(player.getUniqueId());
            if (!ctfPlayer.exist()){
                player.sendMessage("你还没有注册");
                return false;
            }
            if (!ctfPlayer.checkPassword(args[0])){
                player.sendMessage("密码错误");
                return false;
            }
            player.sendMessage("登录成功");
            player.setGameMode(GameMode.SURVIVAL);
            return true;
        }
        return false;
    }
}
