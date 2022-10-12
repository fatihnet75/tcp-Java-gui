/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package soru;
 import java.util.Scanner;
/**
 *
 * @author Fatih
 */
public class SORU {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     
        String students[][] = new String[3][2];
        Scanner okut =new Scanner(System.in);
        for (int i=0;i<3;i++)
        {
            System.out.println( i+1 +" ogrencinin ismini ve soy ismini  giriniz" );
            for(int j=0;j<1;j++)
            {
                students [i][j] =okut.nextLine();
                 System.out.println( i+1 +" ogrencinin numarasinı  giriniz" );
                students [i][j+1]= okut.nextLine();
                
            }
            
            
        }
        
         for(int i=0;i<3;i++)
         {
             for (int j = 0; j < 1; j++) 
             {
              System.out.println(students[i][j] + "|" + students[i][j+1]);
                 System.out.println("--------------------------------");
             }
             
         }
        
    }
    
}
