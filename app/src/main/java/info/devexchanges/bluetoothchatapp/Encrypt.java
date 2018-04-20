package info.devexchanges.bluetoothchatapp;

/**
 * Created by abhey on 20/4/18.
 */

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {

    Encrypt(){

    }


    public String encrypt(String data){
        SecretKeySpec key = generateKey("BlueChat");
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE,key);
            byte[] encVal = c.doFinal(data.getBytes());
            return android.util.Base64.encodeToString(encVal,1);
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String enc){
        SecretKeySpec key=generateKey("BlueChat");
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE,key);
            byte[] decodedValue =android.util.Base64.decode(enc,1);
            byte[] decVal = c.doFinal(decodedValue);
            return new String(decVal);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private SecretKeySpec generateKey(String password) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //SecureRandom secureRandom = new SecureRandom();
            //byte[] bytes = new byte[12];
            //secureRandom.nextBytes(bytes);
            byte[] bytes = password.getBytes();
            digest.update(bytes,0,bytes.length);
            byte[] key = digest.digest();
            return new SecretKeySpec(key,"AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}