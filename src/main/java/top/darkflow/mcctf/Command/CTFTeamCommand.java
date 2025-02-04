package top.darkflow.mcctf.Command;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.darkflow.mcctf.Database.DatabaseConnection;
import top.darkflow.mcctf.Model.CTFPlayer;
import top.darkflow.mcctf.Model.CTFTeam;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CTFTeamCommand implements CommandExecutor {
    @Override
    public boolean onCommand( CommandSender sender,  Command command,  String label,  String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if(player.getGameMode()== GameMode.SPECTATOR){
                player.sendMessage("请先登录");
                return false;
            }
            if (args.length == 0){
                player.sendMessage("Usage: /ctfteam create <teamName>");
                player.sendMessage("Usage: /ctfteam join <teamName>");
                player.sendMessage("Usage: /ctfteam leave");
                return false;
            }
            if (args[0].equalsIgnoreCase("create")){
                if (args.length != 2){
                    player.sendMessage("Usage: /ctfteam create <teamName>");
                    return false;
                }
                // Create team
                //检查队伍
                CTFPlayer ctfPlayer = new CTFPlayer(player.getUniqueId());
                if (ctfPlayer.hasTeam()){
                    player.sendMessage("你已经在一个队伍中了");
                    return false;
                }
                CTFTeam newTeam = new CTFTeam(UUID.randomUUID(), args[1], player.getUniqueId());
                //检查队伍名称
                if (newTeam.hasDuplicateTeamName()){
                    player.sendMessage("队伍名称重复");
                    return false;
                }
                //保存队伍
                if (!newTeam.saveTeam()){
                    player.sendMessage("队伍创建失败");
                    return false;
                }
                player.getScoreboard().registerNewTeam(newTeam.getUuid().toString());
                player.getScoreboard().getTeam(newTeam.getUuid().toString()).setDisplayName(newTeam.getName());
                player.getScoreboard().getTeam(newTeam.getUuid().toString()).setSuffix("["+newTeam.getName()+"]");
                player.getScoreboard().getTeam(newTeam.getUuid().toString()).addEntry(player.getName());
                player.sendMessage("队伍创建成功");
                ctfPlayer.updateTeam(newTeam.getUuid());
                return true;
            }
            else if (args[0].equalsIgnoreCase("join")){
                if (args.length != 2){
                    player.sendMessage("Usage: /ctfteam join <teamName>");
                    return false;
                }
                // Join team
                CTFPlayer ctfPlayer = new CTFPlayer(player.getUniqueId());
                if (ctfPlayer.hasTeam()){
                    player.sendMessage("你已经在一个队伍中了");
                    return false;
                }
                CTFTeam theTeam = new CTFTeam(args[1]);
                if (theTeam.getUuid()==null){
                    player.sendMessage("队伍不存在");
                    return false;
                }
                ctfPlayer.updateTeam(theTeam.getUuid());
                player.getScoreboard().getTeam(theTeam.getUuid().toString()).addEntry(player.getName());
                player.sendMessage("加入队伍成功");
            }
            else if (args[0].equalsIgnoreCase("leave")){
                // Leave team
                CTFPlayer ctfPlayer = new CTFPlayer(player.getUniqueId());
                if (!ctfPlayer.hasTeam()){
                    player.sendMessage("你不在任何队伍中");
                    return false;
                }
                CTFTeam currentTeam = new CTFTeam(ctfPlayer.getTeam());
                ctfPlayer.updateTeam(null);
                //如果队长退出队伍，解散队伍
                if (ctfPlayer.isTeamLeader()){
                    currentTeam.removeTeam();
                }else {
                    player.getScoreboard().getTeam(currentTeam.toString()).removeEntry(player.getName());
                    player.sendMessage("离开队伍成功");
                }
            }
            else{
                player.sendMessage("Usage: /ctfteam create <teamName>");
                player.sendMessage("Usage: /ctfteam join <teamName>");
                player.sendMessage("Usage: /ctfteam leave");
            }
        }
        return false;
    }
}
