
package mainclass;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class ClientGui extends JFrame{
    private JLabel ipAdressLabel,portLabel;
    private JTextField ipAdressTextF,portTextF,messageBox;
    private JButton connectButton,sendButton;
    private JList<String> messageList;
    private DefaultListModel<String> model;
    private JScrollPane scrollPane;
    private String sendMessage;
    private String ip_adress ;
    private int portNumber;
    private PrintWriter outputToServer;
    private Encryption sifreleme;
    private SimpleDateFormat s_date;
    private Date date;
    private Log log;
    private Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try{
                String inFromServer;

                Socket clientSocket = new Socket(ip_adress ,portNumber);
                System.out.println("bağlantı açıldı");
                outputToServer = new PrintWriter(clientSocket.getOutputStream(),true);
                BufferedReader inputFromServer = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                while(true){
                    if((inFromServer=inputFromServer.readLine())==null)
                        continue;
                    inFromServer = sifreleme.decryptWord(inFromServer);
                    
                    if(inFromServer=="Server: *kapat*")
                        break;
                    model.addElement("Server: "+inFromServer);
                    String messageLine = "["+s_date.format(date)+"]"
                            +"[Server]"+"["+inFromServer+"]";
                    log.logSave(messageLine);
                }
                clientSocket.close();
            }
            catch(Exception e)
            {
                System.out.println("baglantı kapatıldı");
            }
        }
    });
    
    public ClientGui(){
        super("Client Uygulamasi");
        setLayout(null);
        sifreleme = new Encryption();
        date = new Date();
        s_date = new SimpleDateFormat();
        log = new Log();
        //İp adress girişi için label ve textfield
        ipAdressLabel = new JLabel("Server IP:");
        ipAdressLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        ipAdressLabel.setBounds(new Rectangle(new Point(30, 20), ipAdressLabel.getPreferredSize()));
        
        ipAdressTextF = new JTextField("");
        ipAdressTextF.setBounds(new Rectangle(new Point(110, 20), ipAdressTextF.getPreferredSize()));
        ipAdressTextF.setSize(110, 30);
        ipAdressTextF.setFont(new Font("Serif", Font.PLAIN, 18));
                
        //port girişi için label ve textfield
        
        portLabel = new JLabel("Port:");
        portLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        portLabel.setBounds(new Rectangle(new Point(230, 20), portLabel.getPreferredSize()));
        
        portTextF = new JTextField("");
        portTextF.setBounds(new Rectangle(new Point(270, 20), ipAdressTextF.getPreferredSize()));
        portTextF.setSize(60, 30);
        portTextF.setFont(new Font("Serif", Font.PLAIN, 18));
        
        //bağlan butonu
        
        connectButton = new JButton("BAĞLAN");
        connectButton.setSize(100,40);
        connectButton.setLocation(new Point(350,15));
        connectButton.setFont(new Font("Arial", Font.PLAIN, 16));
        connectButton.setBackground(Color.decode("#fff2cc"));
        
        //Mesajların durduğu mesaj kutulari
        
        model = new DefaultListModel<String>();
        messageList = new JList<>(model);
        messageList.setFont(new Font("Arial", Font.PLAIN, 16));
        messageList.setSelectionBackground(Color.green);
        scrollPane = new JScrollPane(messageList);
        scrollPane.setBounds(new Rectangle(new Point(30, 80), scrollPane.getPreferredSize()));
        scrollPane.setSize(420, 300);
        
        //mesaj gonderme kutusu
        
        messageBox = new JTextField("");
        messageBox.setBounds(new Rectangle(new Point(30, 420), messageBox.getPreferredSize()));
        messageBox.setSize(280, 40);
        messageBox.setFont(new Font("Serif", Font.PLAIN, 18));
        
        //mesaj gonderme butonu
        
        sendButton = new JButton("GÖNDER");
        sendButton.setSize(120,60);
        sendButton.setLocation(new Point(330,410));
        sendButton.setFont(new Font("Arial", Font.PLAIN, 16));
        sendButton.setBackground(Color.decode("#fff2cc"));
        
        /*************************************************************************/
        
        add(ipAdressLabel);
        add(ipAdressTextF);
        add(portLabel);
        add(portTextF);
        add(connectButton);
        add(scrollPane);
        add(messageBox);
        add(sendButton);
        
        
        setSize(500,550);
        setVisible(true);
    }
    
    public void start(){
        connectButton.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                ip_adress = ipAdressTextF.getText();
                portNumber = Integer.parseInt(portTextF.getText());
                t.start();
            }
        });
        sendButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage = messageBox.getText();
                model.addElement("Client: "+sendMessage);
                String messageLine = "["+s_date.format(date)+"]"
                            +"[Client]"+"["+sendMessage+"]";
                log.logSave(messageLine);
                sendMessage = sifreleme.encryptedWord(sendMessage);
                outputToServer.println(sendMessage);
                messageBox.setText("");
                sendMessage = null;
            }
        });
    }
    public static void main(String[] args) {
        ClientGui cl = new ClientGui();
        cl.start();
        cl.setDefaultCloseOperation(ClientGui.EXIT_ON_CLOSE);
    }
}
