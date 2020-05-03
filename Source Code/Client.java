import java.io.*;
import java.net.Socket;


public class Client {

    public static void main(String[] args) throws IOException {

        int ServerPort=Integer.parseInt(args[1]);     //UNCOMMENT ON FINAL

        //int ServerPort=9090;                          //For testing purposes
        String ServerIP = args[0];
        Socket Server = new Socket(ServerIP,ServerPort);

        DataInputStream fromServer = new DataInputStream(Server.getInputStream());
        DataOutputStream toServer = new DataOutputStream(Server.getOutputStream());
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("----------\nMailServer:\n----------\nHello, you connected as a guest.\n" +
                "==========\n> LogIn\n> Register\n> Exit\n==========\n"); 


        while(true){
            System.out.print("> ");
            String command = keyboard.readLine();
            toServer.writeUTF(command);
            String serverResponse = fromServer.readUTF();
            System.out.println(serverResponse);
            if(command.equals("Exit")){
                toServer.close();
                fromServer.close();
                keyboard.close();
                Server.close();
                return;
            }
        }
    }
}
