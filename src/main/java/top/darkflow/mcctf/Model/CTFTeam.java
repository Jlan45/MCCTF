package top.darkflow.mcctf.Model;

import org.bukkit.Bukkit;
import top.darkflow.mcctf.Database.CTFCache;
import top.darkflow.mcctf.Database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Stream;

public class CTFTeam {
    private UUID Uuid;
    private String Name;
    private UUID Leader;
    public CTFTeam(UUID uuid, String name, UUID leader) {
        this.Uuid = uuid;
        this.Name = name;
        this.Leader = leader;
    }

    public CTFTeam(UUID team) {
        this.Uuid=team;
    }
    public CTFTeam(String name) {
        this.Name=name;
        String getTeamSql = "SELECT * FROM teams WHERE name = ? LIMIT 1";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(getTeamSql);
            ps.setString(1, Name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Uuid = UUID.fromString(rs.getString("uuid"));
                Leader = UUID.fromString(rs.getString("leaderUUID"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UUID getLeader() {
        return Leader;
    }

    public String getName() {
        return Name;
    }

    public UUID getUuid() {
        return Uuid;
    }

    public void setUuid(UUID uuid) {
        Uuid = uuid;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setLeader(UUID leader) {
        Leader = leader;
    }
    public boolean hasDuplicateTeamName(){
        //检查队伍名称
        String checkTeamNameSql = "SELECT name FROM teams WHERE name = ? LIMIT 1";
        try{
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(checkTeamNameSql);
            ps.setString(1, Name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public boolean saveTeam(){
        //保存队伍
        String createTeamSql = "INSERT INTO teams (uuid, name, leaderUUID) VALUES (?, ?, ?)";
        try{
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(createTeamSql);
            ps.setString(1, Uuid.toString());
            ps.setString(2, Name);
            ps.setString(3, Leader.toString());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public UUID[] getMembers(){
        //获取队伍成员
        String getMembersSql = "SELECT uuid FROM players WHERE teamUUID = ?";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(getMembersSql);
            ps.setString(1, Uuid.toString());
            ResultSet rs = ps.executeQuery();
            return Stream.generate(() -> {
                try {
                    if (rs.next()) {
                        return UUID.fromString(rs.getString("uuid"));
                    }
                    return null;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }).takeWhile(uuid -> uuid != null).toArray(UUID[]::new);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean removeTeam(){
        //解散队伍
        for (UUID member : getMembers()){
            CTFPlayer ctfPlayer = new CTFPlayer(member);
            ctfPlayer.updateTeam(null);
        }
        String deleteTeamSql = "DELETE FROM teams WHERE uuid = ?";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(deleteTeamSql);
            ps.setString(1, Uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeam(Uuid.toString()).unregister();
        return true;
    }
    public ArrayList<String> getSolved(){
        //获取队伍解决的题目
        ArrayList<String> solved = CTFCache.teamCache.get(Uuid.toString());
        if (solved==null){
            solved=new ArrayList<>();
        }
        return solved;
    }
    public CTFDocker getContainerByChallenge(String challenge){
        //获取队伍的docker容器
        ArrayList<CTFDocker> dockers = CTFCache.challengeCache.get(Uuid.toString());
        if (dockers==null){
            dockers=new ArrayList<>();
            CTFCache.challengeCache.put(Uuid.toString(),dockers);
            return null;
        }
        for (CTFDocker docker : dockers){
            if (docker.getChallenge().toString().equals(challenge)){
                return docker;
            }
        }
        return null;
    }
    public boolean removeContainerByChallenge(String challenge){
        //移除队伍的docker容器
        ArrayList<CTFDocker> dockers = CTFCache.challengeCache.get(Uuid.toString());
        if (dockers==null){
            return false;
        }
        for (CTFDocker docker : dockers){
            if (docker.getChallenge().toString().equals(challenge)){
                dockers.remove(docker);
                CTFCache.challengeCache.put(Uuid.toString(),dockers);
                return true;
            }
        }
        return false;
    }
}