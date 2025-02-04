package top.darkflow.mcctf.Listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Redstone;
import top.darkflow.mcctf.Model.CTFChallenge;

public class PlayerInteractVillagerEvent implements Listener {
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity.getType() == EntityType.VILLAGER) {
            Villager villager = (Villager) entity;
            if (villager.getCustomName()==null) {
                return;
            }
            else{
                player.sendMessage("/ctf list "+villager.getCustomName());
            }

            event.setCancelled(true); // 取消默认的交互行为
            // 执行自定义逻辑，例如打开自定义界面
            player.performCommand("ctf list "+villager.getCustomName());
        }
    }
}