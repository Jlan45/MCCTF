package top.darkflow.mcctf.Command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.darkflow.mcctf.Database.CTFCache;
import top.darkflow.mcctf.Docker.DockerConnection;
import top.darkflow.mcctf.Model.CTFChallenge;
import top.darkflow.mcctf.Model.CTFDocker;
import top.darkflow.mcctf.Model.CTFPlayer;
import top.darkflow.mcctf.Model.CTFTeam;
import top.darkflow.mcctf.Tools;

import java.util.ArrayList;
import java.util.Arrays;

import static top.darkflow.mcctf.Tools.GenerateFlag;

public class CTFCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player player) {
            if (player.getGameMode() == GameMode.SPECTATOR) {
                player.sendMessage("请先登录");
                return false;
            }
            CTFPlayer ctfPlayer = new CTFPlayer(player.getUniqueId());
            //查询用户队伍，没队伍下面不走
            if (!ctfPlayer.hasTeam()) {
                player.sendMessage("你还没有队伍");
                return false;
            }
            CTFTeam team = new CTFTeam(ctfPlayer.getTeam());
            switch (args[0].toLowerCase()) {
                case "submit":
                    //ctf submit 题目UUID flag
                    //验证题目UUID合法性
                    //解析参数
                    if (args.length != 3) {
                        player.sendMessage("参数错误！");
                        return true;
                    }
                    CTFChallenge challenge = CTFChallenge.getCtfChallengeByUuid(args[1]);
                    if (challenge == null) {
                        player.sendMessage("题目不存在！");
                        return true;
                    }
                    //验证是否已经解决
                    if (team.getSolved().contains(challenge.getUuid().toString())) {
                        player.sendMessage("题目已解决！");
                        return true;
                    }
                    if (!challenge.getFlag().isEmpty()) {
                        //静态flag
                        if (args[2].equals(challenge.getFlag())) {
                            submitFlag(team, challenge,ctfPlayer);
                        }
                    }else {
                        String currentFlag = GenerateFlag(team.getUuid(), challenge.getUuid());
                        if (currentFlag.equals(args[2])){
                            submitFlag(team, challenge,ctfPlayer);
                        }
                    }
                    break;
                case "list":
                    //ctf list type
                    //查询题目列表
                    ArrayList<CTFChallenge> challengeList = CTFChallenge.getChallengeListByType(args[1]);
                    ArrayList<String> teamSolved = team.getSolved();
                    Inventory inventory = Bukkit.createInventory(null,27,args[1]);
                    for (CTFChallenge ctfChallenge : challengeList) {
                        if (teamSolved.contains(ctfChallenge.getUuid().toString())) {
                            player.sendMessage(ctfChallenge.getName() + " 已解决");
                            ItemStack challengeItem = new ItemStack(Material.PLAYER_HEAD);
                            ItemMeta im = challengeItem.getItemMeta();
                            im.setDisplayName(ctfChallenge.getName());
                            im.setUnbreakable(true);
                            ArrayList<String> loreList = new ArrayList<>(Arrays.asList(ctfChallenge.getDescription().split("\n")));
                            loreList.add(ChatColor.GRAY+ctfChallenge.getUuid().toString());
                            im.setLore(loreList);
                            challengeItem.setItemMeta(im);
                            inventory.addItem(challengeItem);
                        } else {
                            player.sendMessage(ctfChallenge.getName() + " 未解决");
                            ItemStack challengeItem = new ItemStack(Material.ZOMBIE_HEAD);
                            ItemMeta im = challengeItem.getItemMeta();
                            im.setDisplayName(ctfChallenge.getName());
                            im.setUnbreakable(true);
                            ArrayList<String> loreList = new ArrayList<>(Arrays.asList(ctfChallenge.getDescription().split("\n")));
                            loreList.add(ChatColor.GRAY+ctfChallenge.getUuid().toString());

                            im.setLore(loreList);

                            challengeItem.setItemMeta(im);
                            inventory.addItem(challengeItem);
                        }
                    }
                    player.openInventory(inventory);
                    return true;
                case "start":
                    // handle start
                    //ctf start 题目UUID
                    //验证题目UUID合法性
                    CTFChallenge ctfChallenge = CTFChallenge.getCtfChallengeByUuid(args[1]);
                    if (ctfChallenge == null) {
                        player.sendMessage("题目不存在！");
                        return true;
                    }
                    if (!ctfChallenge.isDocker()){
                        player.sendMessage("题目无需启用docker！");
                        return true;
                    }
                    //检查队伍是否存在现有docker
                    if (team.getContainerByChallenge(ctfChallenge.getUuid().toString())!=null){
                        player.sendMessage("队伍已经启动过该题目！");
                        return true;
                    }
                    //启动docker
                    String[] portList=ctfChallenge.getPort().split(",");
                    for (int i = 0; i < portList.length; i++) {
                        portList[i]= CTFCache.CurrentPort+":"+portList[i];
                        CTFCache.CurrentPort++;
                    }
                    CTFDocker ctfDocker = new CTFDocker(team.getUuid(),ctfChallenge.getImage(),portList,ctfChallenge.getUuid());
                    if (ctfDocker.startDockerContainer()){
                        player.sendMessage("启动成功！");
                        ArrayList<CTFDocker> teamOpened = CTFCache.challengeCache.get(team.getUuid().toString());
                        if (teamOpened==null){
                            teamOpened=new ArrayList<>();
                        }
                        teamOpened.add(ctfDocker);
                        CTFCache.challengeCache.put(team.getUuid().toString(),teamOpened);
                    }
                    break;
                case "stop":
                    //ctf stop 题目UUID
                    //验证题目UUID合法性
                    CTFChallenge ctfChallenge1 = CTFChallenge.getCtfChallengeByUuid(args[1]);
                    if (ctfChallenge1 == null) {
                        player.sendMessage("题目不存在！");
                        return true;
                    }
                    if (!ctfChallenge1.isDocker()){
                        player.sendMessage("题目无需启用docker！");
                        return true;
                    }
                    //检查队伍是否存在现有docker
                    CTFDocker ctfDocker1 = team.getContainerByChallenge(ctfChallenge1.getUuid().toString());
                    if (ctfDocker1==null){
                        player.sendMessage("队伍未启动过该题目！");
                        return true;
                    }
                    //停止docker
                    DockerConnection.dockerClient.stopContainerCmd(ctfDocker1.getHash()).exec();
                    DockerConnection.dockerClient.removeContainerCmd(ctfDocker1.getHash()).exec();
                    team.removeContainerByChallenge(ctfChallenge1.getUuid().toString());
                    player.sendMessage("停止成功！");
                    break;
                default:
                    player.sendMessage("Invalid command");
                    break;
            }
        }
        return false;
    }
    public void submitFlag(CTFTeam team,CTFChallenge challenge,CTFPlayer ctfPlayer){
        ArrayList<String> currentTeamSloved = CTFCache.teamCache.get(team.getUuid().toString());
        if (currentTeamSloved == null) {
            currentTeamSloved = new ArrayList<>();
        }
        currentTeamSloved.add(challenge.getUuid().toString());
        CTFCache.teamCache.put(team.getUuid().toString(), currentTeamSloved);
        ArrayList<String> currentPlayerSloved = CTFCache.playerCache.get(ctfPlayer.getUuid().toString());
        if (currentPlayerSloved == null) {
            currentPlayerSloved = new ArrayList<>();
        }
        currentPlayerSloved.add(challenge.getUuid().toString());
        CTFCache.playerCache.put(team.getUuid().toString(), currentPlayerSloved);
    }
}
