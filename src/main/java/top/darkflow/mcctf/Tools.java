package top.darkflow.mcctf;

import org.bukkit.Bukkit;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Tools {
    public static String flagSalt;

    public static String GenerateFlag(UUID teamUUID, UUID challengeUUID){
        String combined = teamUUID.toString() + challengeUUID.toString() + flagSalt;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(combined.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return "flag{"+hexString.toString()+"}";
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
