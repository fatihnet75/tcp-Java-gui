
package odev4hazirlik;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

//server uygulaması
//client uygulaması=halil ibrahim ates

public class ServerGui extends JFrame {
    
    private JLabel portlabel;
    private JTextField porttext;
    private JTextField mesajbox;
    private JButton serverbaslat;
    private JButton sendbutton;
    private JList<String> mesajlist;
    private JScrollPane scrollPane;
    private DefaultListModel<String> model;
    private String sendMessage;
    private int portNumber;
    private PrintWriter outFromServer;
    private Encryption sifreleme;
    private SimpleDateFormat s_date;
    private Date date;
    private Log log;
    
    
    private Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try{
                String input;
                System.out.println(portNumber);
                ServerSocket serverSocket = new ServerSocket(portNumber);
                
                Socket connectionSocket = serverSocket.accept();
                System.out.println("bağlantı başlatıldı");
                
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                outFromServer = new PrintWriter(connectionSocket.getOutputStream(),true);
                
                while(true) {
                    if((input = inFromClient.readLine())==null)
                        continue;
                    input=sifreleme.decryptWord(input);
                    
                    if(input=="Client: *kapat*")
                        break;
                    model.addElement("Client: "+input);
                    String messageLine = "["+s_date.format(date)+"]"
                            +"[Client]"+"["+input+"]";
                    
                    log.logSave(messageLine);
                    
                }
                serverSocket.close();
                
            }catch(Exception e){
                System.out.println("Bağlantı kesildi");
            }
        }
    });

    public ServerGui()  {
        super("Server uygulama");
        setLayout(null);
        sifreleme = new Encryption();
        s_date=new SimpleDateFormat();
        log=new Log();
        date=new Date();
        
        portlabel = new JLabel("Dinlencek port numarası:");
        portlabel.setFont(new Font("Serif", Font.PLAIN, 20));
        portlabel.setBounds(new Rectangle(new Point(15, 20),portlabel.getPreferredSize()));
        portlabel.setSize(250,20);
        
        //port no girdimiz
        porttext = new JTextField();
        porttext.setBounds(new Rectangle(new Point(230,20),porttext.getPreferredSize()));
        porttext.setSize(70,30);
        porttext.setFont(new Font("Serif", Font.PLAIN, 20));
        
        //eşleme için gereken buton
        serverbaslat = new JButton("Server Başlat");
        serverbaslat.setBounds(320, 15, 140 , 40);
        serverbaslat.setBackground(Color.decode("#fff2cc"));
        serverbaslat.setFont(new Font("Serif", Font.PLAIN, 20));
        //mesajlarımızın gorundugu ekran
        model = new DefaultListModel<>();
        mesajlist = new JList<>(model);
        
        scrollPane = new JScrollPane(mesajlist);
        scrollPane.setBounds(new Rectangle(new Point(15,60),scrollPane.getPreferredSize()));
        scrollPane.setSize(445,330 );
        scrollPane.setFont(new Font("Serif", Font.PLAIN, 20));
        scrollPane.setEnabled(false);
        scrollPane.setFont(new Font("Serif", Font.PLAIN, 20));
        //gondermek istedigimiz ilet
        mesajbox = new JTextField();
        mesajbox.setBounds(new Rectangle(new Point(20,410),porttext.getPreferredSize()));
        mesajbox.setSize(300,70);
        mesajbox.setFont(new Font("Serif", Font.PLAIN, 20));
        
        //gönderme butonu
        sendbutton = new JButton("Gönder");
        sendbutton.setBounds(320, 410, 140 , 70);
        sendbutton.setBackground(Color.decode("#fff2cc"));
       
        
        add(portlabel);
        add(porttext);
        add(serverbaslat);
        add(mesajbox);
        add(sendbutton);
        add(scrollPane);
       
         
        setSize(500,550);
        setVisible(true);
    }
    public void start(){
        serverbaslat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                portNumber = Integer.parseInt(porttext.getText());
                t.start();
            }
        });
        sendbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                sendMessage = mesajbox.getText();
                model.addElement("Server: "+sendMessage);
                String messageLine = "["+s_date.format(date)+"]"
                            +"[Server]"+"["+sendMessage+"]";
                log.logSave(messageLine);
                sendMessage=sifreleme.encryptedWord(sendMessage);
                outFromServer.println(sendMessage);
                mesajbox.setText("");
                sendMessage = null;
            }
        });
    }
    
    public static void main(String[] args) {
        ServerGui server = new ServerGui();
        server.start();
        server.setDefaultCloseOperation(ServerGui.EXIT_ON_CLOSE);;
    }
}