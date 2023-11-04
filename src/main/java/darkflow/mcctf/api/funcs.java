package darkflow.mcctf.api;

import darkflow.mcctf.models.Question;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.player.PlayerEntity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;


public class funcs {
    public static String SALT;

    public static boolean verifyFlag(PlayerEntity player, Question question, String Flag) throws NoSuchAlgorithmException {
        if (Flag.equals(GernerateFlag(player, question))){
            return true;
        }
        //TODO
        return true;
    }
    public static String GernerateFlag(PlayerEntity player, Question question) throws NoSuchAlgorithmException {
        MessageDigest md=MessageDigest.getInstance("MD5");
        byte[] input=(player.getUuidAsString()+SALT+question.getUUID()).getBytes();
        byte[] hashBytes = md.digest(input);
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return "flag{"+sb+"}";
    }
    public static String InitSalt(){
        File saltFile=new File("salt");
        if(!saltFile.exists()){
            String salt= GernerateRandomString(8);
            try {
                FileWriter writer=new FileWriter(saltFile);
                writer.write(salt);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FileReader reader=new FileReader(saltFile);
            char[] salt = new char[8];
            reader.read(salt);
            SALT=new String(salt);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SALT;
    }
    public static String GernerateRandomString(int stringLength){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // 创建Random对象
        Random random = new Random();

        // 创建一个StringBuilder来存储生成的随机字符串
        StringBuilder sb = new StringBuilder();

        // 生成8位随机字符串
        for (int i = 0; i < stringLength; i++) {
            // 从字符集中随机选择一个字符
            char randomChar = characters.charAt(random.nextInt(characters.length()));

            // 将选中的字符添加到StringBuilder中
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
