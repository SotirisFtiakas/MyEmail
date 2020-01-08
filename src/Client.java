import java.io.*;
import java.net.Socket;


public class Client {

    private static final String ServerIp = "127.0.0.1";

    public static void main(String[] args) throws IOException {

        //int ServerPort=Integer.parseInt(args[0]);     //UNCOMMENT ON FINAL

        int ServerPort=9090;
        Socket Server = new Socket(ServerIp,ServerPort);

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
        }
    }
}
