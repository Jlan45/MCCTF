package top.darkflow.mcctf.Model;

import top.darkflow.mcctf.Database.DatabaseConnection;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CTFChallenge {
    private UUID Uuid;
    private int Point;
    private String Name;
    private String Description;
    private String Creator;
    private String Flag;
    private String Type;
    private String Image;
    private String Port;//int spilt with,
    private String Attachment;//webURL
    private boolean Docker;

    public CTFChallenge(UUID uuid, int point, String name, String description, String creator, String flag, String type, String image, String port, String attachment,boolean docker) {
        this.Uuid = uuid;
        this.Point = point;
        this.Name = name;
        this.Description = description;
        this.Creator = creator;
        this.Flag = flag;
        this.Type = type;
        this.Image = image;
        this.Port = port;
        this.Attachment = attachment;
        this.Docker=docker;
    }
    public static CTFChallenge getCtfChallengeByUuid(String uuid){
        String getChallengeSql="SELECT * FROM challenges WHERE uuid = ? LIMIT 1";
        try {
            PreparedStatement ps3= DatabaseConnection.getConnection().prepareStatement(getChallengeSql);
            ps3.setString(1,uuid);
            ResultSet rs3=ps3.executeQuery();
            if (!rs3.next()){
                return null;
            }

            CTFChallenge challenge = new CTFChallenge(
                    UUID.fromString(rs3.getString("uuid")),
                    rs3.getInt("point"),
                    rs3.getString("name"),
                    rs3.getString("description"),
                    rs3.getString("creator"),
                    rs3.getString("staticflag"),
                    rs3.getString("type"),
                    rs3.getString("image"),
                    rs3.getString("port"),
                    rs3.getString("attachment"),
                    rs3.getBoolean("docker")
            );
            return challenge;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }

    public String getFlag() {
        if (Flag==null){
            return "";
        }
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public UUID getUuid() {
        return Uuid;
    }

    public void setUuid(UUID uuid) {
        Uuid = uuid;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPort() {
        return Port;
    }

    public void setPort(String port) {
        Port = port;
    }

    public String getAttachment() {
        return Attachment;
    }

    public void setAttachment(String attachment) {
        Attachment = attachment;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }
    public static ArrayList<CTFChallenge> getChallengeListByType(String type){
        String getChallengeListSql="SELECT * FROM challenges WHERE type = ?";
        try {
            PreparedStatement ps3= DatabaseConnection.getConnection().prepareStatement(getChallengeListSql);
            ps3.setString(1,type.toLowerCase());
            ResultSet rs3=ps3.executeQuery();
            ArrayList<CTFChallenge> challenges = new ArrayList<CTFChallenge>();
            int i = 0;
            while (rs3.next()){
                challenges.add(new CTFChallenge(
                        UUID.fromString(rs3.getString("uuid")),
                        rs3.getInt("point"),
                        rs3.getString("name"),
                        rs3.getString("description"),
                        rs3.getString("creator"),
                        rs3.getString("staticflag"),
                        rs3.getString("type"),
                        rs3.getString("image"),
                        rs3.getString("port"),
                        rs3.getString("attachment"),
                        rs3.getBoolean("docker")
                ));
            }
            return challenges;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isDocker() {
        return Docker;
    }

    public void setDocker(boolean docker) {
        Docker = docker;
    }
}
