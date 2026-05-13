
import java.security.MessageDigest;

public class HashUtil{

// Converts password to SHA-1 hash
public static String toSHA1(String password) throws Exception {
    MessageDigest md = MessageDigest.getInstance("SHA-1");
    byte[] hashBytes = md.digest(password.getBytes("UTF-8"));
    StringBuilder sb = new StringBuilder();
    for (byte b : hashBytes) {
        sb.append(String.format("%02X", b));
    }
    return sb.toString();
 }
}