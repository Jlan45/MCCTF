package top.darkflow.mcctf;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import top.darkflow.mcctf.Command.*;
import top.darkflow.mcctf.Database.DatabaseConnection;
import top.darkflow.mcctf.Docker.DockerConnection;
import top.darkflow.mcctf.Listener.CTFInventoryInteractEvent;
import top.darkflow.mcctf.Listener.PlayerFirstJoinListener;
import top.darkflow.mcctf.Listener.PlayerInteractVillagerEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Mcctf extends JavaPlugin {


    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("ctf").setExecutor(new CTFCommand());
        getCommand("ctfteam").setExecutor(new CTFTeamCommand());
        getCommand("login").setExecutor(new LoginCommand());
        getCommand("register").setExecutor(new RegisterCommand());
        getCommand("ctfmanage").setExecutor(new CTFManageCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerFirstJoinListener(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractVillagerEvent(),this);
        Bukkit.getPluginManager().registerEvents(new CTFInventoryInteractEvent(),this);
        InitDatabase();
        Tools.flagSalt=generateRandomFlagSalt();
        //从环境变量中获取docker Host以及其他内容
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        DatabaseConnection.closeConnection();
    }
    public void InitDatabase(){
        String url = "jdbc:sqlite:plugins/MCCTF.db"; // 数据库文件路径
        // 创建一个新的数据库连接
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                // 创建一个新的表
                String userTableSql = "CREATE TABLE IF NOT EXISTS players (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "uuid TEXT NOT NULL UNIQUE," +
                        "password TEXT NOT NULL,"+
                        "teamUUID TEXT"+
                        ");";
                Statement stmt = conn.createStatement();
                stmt.execute(userTableSql);
                getLogger().info("玩家表创建成功");

                String teamTableSql="CREATE TABLE IF NOT EXISTS teams (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "uuid TEXT NOT NULL UNIQUE," +
                        "leaderUUID TEXT NOT NULL UNIQUE"+
                        ");";
                stmt.execute(teamTableSql);
                getLogger().info("队伍表创建成功");

                String challengeTableSql="CREATE TABLE IF NOT EXISTS challenges (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "uuid TEXT NOT NULL," +
                        "point INTEGER NOT NULL," +
                        "name TEXT NOT NULL," +
                        "description TEXT NOT NULL," +
                        "creator TEXT NOT NULL,"+
                        "staticflag TEXT,"+//如果为空说明是动态flag
                        "type TEXT NOT NULL,"+//如果为空说明是动态flag
                        "image TEXT,"+//空说明不需要docker
                        "port TEXT,"+//int spilt with，
                        "attachment TEXT,"+//附件
                        "docker INTEGER default 0"+
                        ");";
                stmt.execute(challengeTableSql);
                getLogger().info("题目表创建成功");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private String generateRandomFlagSalt() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int index = (int) (Math.random() * characters.length());
            salt.append(characters.charAt(index));
        }
        return salt.toString();
    }
}
