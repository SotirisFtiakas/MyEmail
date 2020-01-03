import javax.xml.soap.SOAPPart;
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Server{

    private final static int TEST_USERS=2;      //number of Test-Users already existing at the beginning of the server.
    private final static int TEST_EMAILS=3;     //number of Test-User Emails already existing at the beginning of the server.

    private static ArrayList<ClientHandler> clients = new ArrayList<>();

    private static List<Account> accountList = new ArrayList<>();      /**List or ArrayList?**/

    public static void main(String[] args) throws IOException {

        //int ServerPort=Integer.parseInt(args[0]);     //UNCOMMENT ON FINAL
        int ServerPort=9090;
        ServerSocket listenSocket = new ServerSocket(ServerPort);

        for(int i=0;i<TEST_USERS;i++) {
            registerInit("test" + i, "test" + i);
        }

        while(true) {

            System.out.println("[SERVER] Waiting for client connection..");
            Socket client = listenSocket.accept();
            System.out.println("[SERVER] Connected to client!");
            ClientHandler Thread = new ClientHandler(client);
            clients.add(Thread);
        }


    }


    private static void registerInit(String username, String password){        //Method used only to create the initial Test-User Emails.
        Account acc = new Account(username,password);
        for(int i=0;i<TEST_EMAILS;i++) {
            acc.addEmail(true, username, password, "testSubject" + i, "testMainBody" + i);
        }
        accountList.add(acc);
    }


    void register(String username,String password){
        boolean found=false;
        for (Account acc : accountList){
            if (acc.getUsername().equals(username)){
                found=true;
            }
        }
        if(!found){
            Account acc = new Account(username,password);
            accountList.add(acc);
        }
        else{
            /**What happens if the username is already being used?**/
        }
    }


    void login(){

    }


    void newEmail(){

    }


    void showEmails(){

    }


    void readEmail(){

    }


    void deleteEmail(){

    }


    void logOut(){

    }


    void exit(){

    }
}
