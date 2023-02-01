/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cifra;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author rodrigo
 */
public class Cifra {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // TODO code application logic here
        Provider[] prv = Security.getProviders();

        for (int i = 0; i < prv.length; i++) {
            System.out.println(prv[i]);
            System.out.println("name: " + prv[i].getName());
            System.out.println("version: " + prv[i].getVersionStr());
            System.out.println();
        }

        System.out.println("Services 1ª Provider:");
        System.out.println(prv[0].getServices());

        System.out.println();
        System.out.println(Security.getAlgorithms("Cipher"));

        KeyGenerator gen = KeyGenerator.getInstance("AES");
        SecretKey secret = gen.generateKey();

        System.out.println();
        System.out.println(secret);

        Cipher cipher;

        cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        /* para vários blocos usar-se-ia o método update() (multiple-part encryption)
            até ao penúltimo bloco, seguido de doFinal()
         */
        String MSG = "aaabbbbbb";
        byte[] plaintext = MSG.getBytes();
        byte[] ciphertext = cipher.doFinal(plaintext);  // cifrar num "bloco" só

        // teste rápido para ver o q fica, que não será visível:
        System.out.println(new String(ciphertext));
        
        
    }

}
