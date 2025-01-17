import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

class Server{

    private final static int TEST_USERS=2;      //number of Test-Users already existing at the beginning of the server.
    private final static int TEST_EMAILS=3;     //number of Test-User Emails already existing at the beginning of the server.

    private static List<Account> accountList = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        int ServerPort=Integer.parseInt(args[0]);     //UNCOMMENT ON FINAL
        //int ServerPort=9090;                          //For testing purposes
        ServerSocket listenSocket = new ServerSocket(ServerPort);

        for(int i=0;i<TEST_USERS;i++) {
            registerInit("test" + i, "test" + i);
        }
        try {
            while (true) {

                System.out.println("[SERVER] Waiting for client connection..");
                Socket client = listenSocket.accept();
                System.out.println("[SERVER] Connected to client!");
                Thread Thread = new ClientHandler(client);
                Thread.start();
                //clients.add(Thread);
            }
        }finally {
            listenSocket.close();
        }
    }

    static String MailServerMsg = "\n----------\nMailServer:\n----------\n";
    static String LoggedInOptions = "\n==========\n> NewEmail\n> ShowEmails\n> ReadEmail\n> DeleteEmail\n> LogOut\n> Exit\n==========\n";
    static String LoggedOutOptions = "\n==========\n> LogIn\n> Register\n> Exit\n==========\n";

    private static void registerInit(String username, String password){        //Method used only to create the initial Test-User Emails.
        Account acc = new Account(username,password);
        for(int i=0;i<TEST_EMAILS;i++) {
            acc.addEmail(true, username, password, "testSubject" + i, "testMainBody" + i);
        }
        accountList.add(acc);
    }


    static boolean searchUsername(String username){
        boolean found=false;
        for (Account acc : accountList){
            if (acc.getUsername().equals(username)){
                found=true;
                break;
            }
        }
        return found;
    }

    static Account returnUser(String username){
        for (Account acc : accountList){
            if (acc.getUsername().equals(username)){
                return acc;
            }
        }
        return null;
    }

    static void register(String username, String password){
            Account acc = new Account(username,password);
            accountList.add(acc);
        }


    static boolean login(String username, String password){
        boolean found=false;
        for (Account acc : accountList){
            if (acc.getUsername().equals(username) && acc.getPassword().equals(password)){
                found=true;
                break;
            }
        }
        return found;
    }


    static void newEmail(Account receiver, String senderUsername, String receiverUsername, String subject, String mainBody){
        receiver.addEmail(true, senderUsername,receiverUsername,subject,mainBody);
    }


    static String showEmails(Account user){
        String msg = MailServerMsg + "Id       From                 Subject\n";
        int count=0;
        int temp;

        for (Email mail: user.getMailbox()){
            count++;
            msg += count + ".";
            if (mail.getIsNew()){
                msg += " [New] ";
            }else{
                msg += "       ";
            }
            msg += mail.getSender();
            temp = 20-mail.getSender().length();
            for(int i=-1;i<temp;i++){
                msg += " ";
            }
            msg += mail.getSubject();
            msg += "\n";
        }
        if(msg.equals(MailServerMsg + "Id       From                 Subject\n")){
            msg += "\n          NO MESSAGES FOUND          ";
        }
        msg += LoggedInOptions;
        return msg;
    }


    static String readEmail(Account user, int id){
        String msg = MailServerMsg + "From                 Subject\n";
        int count=1;
        int temp;
        for (Email mail: user.getMailbox()){
            if(count==id){
                msg += mail.getSender();
                temp = 20-mail.getSender().length();
                for(int i=-1;i<temp;i++){
                    msg += " ";
                }
                msg += mail.getSubject();
                msg += "\n\nMain Body:\n";
                msg += mail.getMainBody();
                msg += LoggedInOptions;
                mail.setIsNew(false);
                return msg;
            }
            count++;
        }
        return msg;
    }


    static void deleteEmail(Account user, int id){
        int count = 1;
        for (Email mail: user.getMailbox()) {
            if(count==id){
                user.getMailbox().remove(mail);
                return;
            }
            count++;
        }
    }


    static String logOut(){

        return (MailServerMsg + "You have been successfully LoggedOut" + LoggedOutOptions);
    }

    static String noSuchCommand(boolean logged){
        if(logged) {
            return (MailServerMsg + "Sorry, no such command exists." + LoggedInOptions);
        }else{
            return (MailServerMsg + "Sorry, no such command exists." + LoggedOutOptions);
        }
    }


    static String exit(){
        return (MailServerMsg + "The connection has successfully terminated. Thanks for using our Email Services!");
    }
}
