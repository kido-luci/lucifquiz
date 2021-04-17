package laptt.utilities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Utilities {

    public static String decodeSHA256(String password) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] encoded = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(encoded);
    }

    private static String bytesToHex(byte[] hash) {

        StringBuilder hexString = new StringBuilder(2 * hash.length);

        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
