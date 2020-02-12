import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Arrays;

public class test {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String password = "somepass";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, hash);
        StringBuilder encryptedStr = new StringBuilder(number.toString(16));
        while (encryptedStr.length() < 32)
        {
            encryptedStr.insert(0, '0');
        }
        System.out.println(encryptedStr.toString());
    }
}
