package utils;

import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import services.ServiceException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static String hashPassword(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void error(ServiceException e) {
        error(e.getMessage());
    }

    public static void error(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        Text text = new Text(message);
        text.setWrappingWidth(400);
        alert.getDialogPane().setContent(text);
        alert.setTitle("Warning!");
        alert.show();
    }
}
