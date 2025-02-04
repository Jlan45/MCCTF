package top.darkflow.mcctf.Model;

import top.darkflow.mcctf.Database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CTFPlayer {
    private UUID Uuid;
    private String Name;
    private String Password;
    private UUID Team;
    public CTFPlayer(UUID uuid) {
        this.Uuid = uuid;
    }
    public CTFPlayer(UUID uuid, String name, String password, UUID team) {
        this.Uuid = uuid;
        this.Name = name;
        this.Password = password;
        this.Team = team;
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public UUID getTeam() {
        return Team;
    }

    public void setTeam(UUID team) {
        Team = team;
    }

    public boolean hasTeam() {
        String checkTeamSql = "SELECT teamUUID FROM players WHERE uuid = ? LIMIT 1";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(checkTeamSql);
            ps.setString(1, Uuid.toString());
            ResultSet rs = ps.executeQuery();
            rs.next();
            String teamUUID = rs.getString("teamUUID");
            if (teamUUID.isEmpty()) {
                return false;
            }
            this.Team = UUID.fromString(teamUUID);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean exist(){
        String checkPlayerSql = "SELECT * FROM players WHERE uuid = ? LIMIT 1";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(checkPlayerSql);
            ps.setString(1, Uuid.toString());
            ResultSet rs = ps.executeQuery();
            if ( rs.next()){
                this.Name = rs.getString("name");
                this.Password = rs.getString("password");
                if (rs.getString("teamUUID").isEmpty()){
                    this.Team = null;
                }else {
                    this.Team = UUID.fromString(rs.getString("teamUUID"));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void create(){
        String createPlayerSql = "INSERT INTO players (uuid, name, password, teamUUID) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(createPlayerSql);
            ps.setString(1, Uuid.toString());
            ps.setString(2, Name);
            ps.setString(3, Password);
            if (Team == null) {
                ps.setString(4, "");
            }
            else{
                    ps.setString(4, Team.toString());
                }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean checkPassword(String password){
        return this.Password.equals(password);
    }
    public boolean updateTeam(UUID team){
        String updateTeamSql = "UPDATE players SET teamUUID = ? WHERE uuid = ?";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(updateTeamSql);
            if (team == null) {
                ps.setString(1, "");
            }
            else {
                ps.setString(1, team.toString());
            }
            ps.setString(2, this.Uuid.toString());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isTeamLeader(){
        String checkLeaderSql = "SELECT leaderUUID FROM teams WHERE leaderUUID = ? LIMIT 1";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(checkLeaderSql);
            ps.setString(1, Uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
