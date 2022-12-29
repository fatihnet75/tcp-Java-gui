
package odev4hazirlik;

import java.io.*;

public class Log {
    private File file;
    public Log(){
       file = new File("C:\\Users\\Fatih\\Desktop\\odev4\\Server\\odev4hazirlik\\Log\\logServer.txt");
    }
    
    public void logSave(String line){
        try{
            FileWriter file_writer = new FileWriter(file,true);
            file_writer.write(line+"\n");
            file_writer.close();
        }catch(IOException e){
            System.out.println("Log yazdırma işleminde hata oluştu");
        }
    }
}
