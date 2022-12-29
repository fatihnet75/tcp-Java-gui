
package mainclass;

import java.util.Scanner;

public class Encryption {
    private final String key;
    private final String value;
    
    public Encryption() {
        key = "iQWdfJKLgzERbnIhjklmTYpUO:*,?-_.PoCwVBqerNMtyuAH ZXasxSDFGcv";
        value = "Ú¬ÓÝÍ½Û¢ÐØ¤Ì¡Ô¥«á¿§©ÞÀ¶¤£µÑÁÙÒÈ¯à¼Ë×°¦ßÏÂ»Ü¹ºÊÅÉ­Ö¾±²®ÆÕª¨Î&";
    }
    
    public String encryptedWord(String decrypt){
        String result = "";
        
        for(int i = 0;i < decrypt.length();i++){
            int j;
            for(j = 0;j < key.length();j++){
                if(decrypt.charAt(i)==key.charAt(j))
                    break;
            }
            result += value.charAt(j);
        }
        return result;
    }
    public String decryptWord(String encrypted){
        String result = "";
        
        for(int i = 0;i < encrypted.length();i++){
            int j;
            for(j = 0;j < value.length();j++){
                if(encrypted.charAt(i)==value.charAt(j))
                    break;
            }
            result += key.charAt(j);
        }
        return result;
    }
}
