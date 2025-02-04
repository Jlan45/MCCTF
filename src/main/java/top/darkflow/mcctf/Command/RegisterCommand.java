package top.darkflow.mcctf.Command;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.darkflow.mcctf.Model.CTFPlayer;

public class RegisterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length != 1){
                player.sendMessage("Usage: /register <password>");
                return false;
            }
            CTFPlayer ctfPlayer = new CTFPlayer(player.getUniqueId());
            if (ctfPlayer.exist()){
                player.sendMessage("你已经注册过了");
                return false;
            }
            ctfPlayer.setPassword(args[0]);
            ctfPlayer.setName(player.getName());
            ctfPlayer.create();
            player.sendMessage("注册成功");
            player.setGameMode(GameMode.SURVIVAL);
            return true;
        }
        return false;
    }
}
