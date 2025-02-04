package top.darkflow.mcctf.Listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import top.darkflow.mcctf.Model.CTFPlayer;

public class PlayerFirstJoinListener implements Listener {
    @EventHandler
    public void onPlayerFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setGameMode(GameMode.SPECTATOR);

        CTFPlayer ctfPlayer = new CTFPlayer(player.getUniqueId());
        if (ctfPlayer.exist()){
            player.sendMessage("欢迎来到MCCTF，请输入 /login <密码> 登录");
            return;
        }
        player.sendMessage("欢迎来到MCCTF，请输入 /register <密码> 注册");

    }
}
