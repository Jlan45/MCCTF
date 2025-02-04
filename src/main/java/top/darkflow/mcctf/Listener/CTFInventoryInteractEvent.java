package top.darkflow.mcctf.Listener;


import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.darkflow.mcctf.Model.CTFChallenge;
import top.darkflow.mcctf.Model.CTFPlayer;
import top.darkflow.mcctf.Model.CTFTeam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


public class CTFInventoryInteractEvent implements Listener {
    @EventHandler
    public void onPlayerSelectChallenge(InventoryClickEvent event) {
        Player player= (Player) event.getWhoClicked();

        System.out.println(event.getCurrentItem());
        ItemStack currentItem = event.getCurrentItem();
        if (currentItem==null){
            return;
        }
        if (currentItem.getType()== Material.ZOMBIE_HEAD||currentItem.getType()==Material.PLAYER_HEAD&&event.getAction()== InventoryAction.PICKUP_ALL){
            if (!currentItem.getItemMeta().getDisplayName().isEmpty()){
                List<String> lore = currentItem.getItemMeta().getLore();
                String uuid = lore.get(lore.size() - 1);
                CTFChallenge challenge =CTFChallenge.getCtfChallengeByUuid(uuid.replace(ChatColor.GRAY.toString(),""));
                if (challenge!=null){
                    event.setCancelled(true);
                    sendChallengeInfo(challenge,player);
                    openChallengeAnvil(player,challenge, new CTFPlayer(player.getUniqueId()));
                }
                return;
            }
        }
    }
    public static void sendChallengeInfo(CTFChallenge challenge, Player player){
        //生成分割线
        String line = "--------------------------------\n";
        player.sendMessage(ChatColor.RED+line+challenge.getName()+"\t"+"by "+challenge.getCreator()+"\n");
        player.sendMessage(ChatColor.YELLOW+line+challenge.getDescription());
        player.sendMessage(ChatColor.GREEN+line+"Attachment: "+challenge.getAttachment());
    }
    public static void openChallengeAnvil(Player player, CTFChallenge challenge, CTFPlayer ctfPlayer){
        if (challenge.isDocker()){
            //用anvilGUI
            if (!ctfPlayer.hasTeam()){
                player.sendMessage("You don't have a team");
                return;
            }
            UUID team = ctfPlayer.getTeam();
            CTFTeam ctft = new CTFTeam(team);
            ItemStack itemLeft;
            if(ctft.getContainerByChallenge(challenge.getUuid().toString())==null){
                itemLeft=new ItemStack(Material.GREEN_WOOL);//启动或者停止
                ItemMeta iml=itemLeft.getItemMeta();
                iml.setDisplayName(ChatColor.GREEN+"Start");
                itemLeft.setItemMeta(iml);
            }else {
                itemLeft=new ItemStack(Material.RED_WOOL);//启动或者停止
                ItemMeta iml=itemLeft.getItemMeta();
                iml.setDisplayName(ChatColor.GREEN+"Stop");
                itemLeft.setItemMeta(iml);
            }


            ItemStack itemRight=new ItemStack(Material.BROWN_WOOL);//RENEW
            ItemMeta imr=itemRight.getItemMeta();
            imr.setDisplayName(ChatColor.GRAY+"Renew");
            itemRight.setItemMeta(imr);

            ItemStack itemOutput=new ItemStack(Material.GRAY_WOOL);//Submit
            ItemMeta imo=itemOutput.getItemMeta();
            imo.setDisplayName(ChatColor.GRAY+"Submit");
            itemOutput.setItemMeta(imo);

            new AnvilGUI.Builder()
                    .onClick((slot, stateSnapshot) -> { // Either use sync or async variant, not both
                        if (slot==AnvilGUI.Slot.INPUT_LEFT) {
//                            stateSnapshot.getPlayer().sendMessage("Left");
                            if (stateSnapshot.getLeftItem().getType()==Material.GREEN_WOOL){
                                stateSnapshot.getPlayer().performCommand("ctf start "+challenge.getUuid().toString());

                            }else if (stateSnapshot.getLeftItem().getType()==Material.RED_WOOL){
                                stateSnapshot.getPlayer().performCommand("ctf stop "+challenge.getUuid().toString());
                            }
                        } else if (slot==AnvilGUI.Slot.OUTPUT) {
                            stateSnapshot.getPlayer().performCommand("ctf submit "+challenge.getUuid().toString()+" "+stateSnapshot.getText());
//                            stateSnapshot.getPlayer().sendMessage("ctf submit "+challenge.getUuid().toString()+" "+stateSnapshot.getText());
                        } else if (slot==AnvilGUI.Slot.INPUT_RIGHT) {
//                            stateSnapshot.getPlayer().sendMessage("Right");
                            stateSnapshot.getPlayer().performCommand("ctf renew "+challenge.getUuid().toString());
                        }
                        return Arrays.asList(AnvilGUI.ResponseAction.close());
                    }).itemLeft(itemLeft)
                    .itemOutput(itemOutput)
                    .itemRight(itemRight)
                    .title(challenge.getName())                                       //set the title of the GUI (only works in 1.14+)
                    .plugin(Bukkit.getPluginManager().getPlugin("mcctf"))                                          //set the plugin instance
                    .open(player);

        }
    }
}
