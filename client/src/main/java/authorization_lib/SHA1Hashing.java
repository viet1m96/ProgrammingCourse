package authorization_lib;

import exceptions.log_exceptions.LogException;
import logging.LogUtil;
import printer_options.RainbowPrinter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Hashing {
    public static String hashStringSHA1(String input) throws LogException {
        try {
          MessageDigest digest = MessageDigest.getInstance("SHA-1");

          byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

          StringBuilder hexString = new StringBuilder();
          for (byte b : hash) {
              hexString.append(String.format("%02x", b));
          }
          return hexString.toString();
        } catch(NoSuchAlgorithmException e) {
            LogUtil.logTrace(e);
            throw new LogException();
        }
    }
}
