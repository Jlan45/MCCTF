package top.darkflow.mcctf.Command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class CTFManageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()) {
            Player player = (Player) sender;
            switch (args[0]) {
                case "createnpc":
                    // Create NPC
                    String typeName=args[1];
                    Villager villager = player.getWorld().spawn(player.getLocation(), Villager.class);
                    villager.setCustomName(typeName);
                    villager.setCustomNameVisible(true);
                    villager.setInvulnerable(true);
                    villager.setAdult();
                    villager.setAI(false);
                    return true;
            }
        }
        return false;
    }
}
