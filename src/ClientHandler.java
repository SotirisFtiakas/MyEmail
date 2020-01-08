import sun.rmi.runtime.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Runnable;
import java.net.Socket;


public class ClientHandler extends Thread {

    private Socket client;
    private DataInputStream fromClient;
    private DataOutputStream toClient;
    private boolean LoggedIn = false;


    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        fromClient = new DataInputStream(this.client.getInputStream());
        toClient = new DataOutputStream(this.client.getOutputStream());

    }


    @Override
    public void run() {
        try {
            while(true) {
                String request = fromClient.readUTF();

                if (request.equals("LogIn")) {              // LOGIN REQUEST
                    if(!LoggedIn) {
                        toClient.writeUTF(Server.MailServerMsg + "Type your username:");
                        String username = fromClient.readUTF();
                        boolean found = Server.searchUsername(username);
                        if (!found) {
                            toClient.writeUTF(Server.MailServerMsg + "Username doesn't exist." + Server.LoggedOutOptions);
                        }else{
                            toClient.writeUTF(Server.MailServerMsg + "Type your password:");
                            String password = fromClient.readUTF();
                            boolean successfulLogin = Server.login(username,password);
                            if(!successfulLogin){
                                toClient.writeUTF(Server.MailServerMsg + "Wrong password." + Server.LoggedOutOptions);
                            }else{
                                toClient.writeUTF(Server.MailServerMsg + "Welcome back " + username + "!" + Server.LoggedInOptions);
                                LoggedIn=true;
                            }
                        }
                    }else{
                        toClient.writeUTF(Server.MailServerMsg + "You are already LoggedIn to an account." + Server.LoggedInOptions);
                    }
                }
                else if (request.equals("Register")) {       // REGISTER REQUEST
                    if(!LoggedIn) {
                        toClient.writeUTF(Server.MailServerMsg + "Type your username:");
                        String username = fromClient.readUTF();
                        boolean found = Server.searchUsername(username);
                        if (found) {
                            toClient.writeUTF(Server.MailServerMsg + "Username already exists." + Server.LoggedOutOptions);
                        }else{
                            toClient.writeUTF(Server.MailServerMsg + "Type your password:");
                            String password = fromClient.readUTF();
                            Server.register(username,password);
                            toClient.writeUTF(Server.MailServerMsg + "You have successfully registered!" + Server.LoggedInOptions);     //Need more
                            LoggedIn=true;
                        }
                    }else{
                        toClient.writeUTF(Server.MailServerMsg + "You are already LoggedIn to an account." + Server.LoggedInOptions);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                toClient.close();
                fromClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
