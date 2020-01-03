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

        while(true){
            System.out.println("> ");
            String command = keyboard.readLine();
            if(command.equals("quit")){
                System.out.println("YES");
                break;
            }
            toServer.writeUTF(command);
            String serverResponse = fromServer.readUTF();
            System.out.println(serverResponse);
        }
    }
}
