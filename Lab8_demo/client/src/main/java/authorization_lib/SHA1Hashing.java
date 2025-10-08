package authorization_lib;

import javafx.stage.Stage;
import logging.LogUtil;
import gui.utilities.tools.AlertUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Hashing {
    public static String hashStringSHA1(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");

            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            LogUtil.logTrace(e);
            AlertUtil.showErrorAlert("Unsuccessfully", "Hashing failed!", (Stage)null);
        }
        return null;
    }
}
