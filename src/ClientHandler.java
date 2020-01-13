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
    private String username;
    private Account userAcc;


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
                    if (!LoggedIn) {
                        toClient.writeUTF(Server.MailServerMsg + "Type your username:");
                        username = fromClient.readUTF();
                        boolean found = Server.searchUsername(username);
                        if (!found) {
                            toClient.writeUTF(Server.MailServerMsg + "Username doesn't exist." + Server.LoggedOutOptions);
                        } else {
                            toClient.writeUTF(Server.MailServerMsg + "Type your password:");
                            String password = fromClient.readUTF();
                            boolean successfulLogin = Server.login(username, password);
                            if (!successfulLogin) {
                                toClient.writeUTF(Server.MailServerMsg + "Wrong password." + Server.LoggedOutOptions);
                            } else {
                                toClient.writeUTF(Server.MailServerMsg + "Welcome back " + username + "!" + Server.LoggedInOptions);
                                userAcc = Server.returnUser(username);
                                LoggedIn = true;
                            }
                        }
                    } else {
                        toClient.writeUTF(Server.MailServerMsg + "You are already LoggedIn to an account." + Server.LoggedInOptions);
                    }
                } else if (request.equals("Register")) {       // REGISTER REQUEST
                    if (!LoggedIn) {
                        toClient.writeUTF(Server.MailServerMsg + "Type your username:");
                        username = fromClient.readUTF();
                        while (username.length() > 20) {
                            toClient.writeUTF(Server.MailServerMsg + "Username can't be longer than 20 characters. Type again:\n");
                            username = fromClient.readUTF();
                        }
                        boolean found = Server.searchUsername(username);
                        if (found) {
                            toClient.writeUTF(Server.MailServerMsg + "Username already exists." + Server.LoggedOutOptions);
                        } else {
                            toClient.writeUTF(Server.MailServerMsg + "Type your password:");
                            String password = fromClient.readUTF();
                            Server.register(username, password);
                            LoggedIn = true;
                            userAcc = Server.returnUser(username);
                            toClient.writeUTF(Server.MailServerMsg + "You have successfully registered!" + Server.LoggedInOptions);
                        }
                    } else {
                        toClient.writeUTF(Server.MailServerMsg + "You are already LoggedIn to an account." + Server.LoggedInOptions);
                    }
                } else if (request.equals("Exit")) {
                    toClient.writeUTF(Server.exit());
                    return;
                } else if (request.equals("NewEmail")) {
                    if (LoggedIn) {
                        toClient.writeUTF(Server.MailServerMsg + "Receiver:\n");
                        String receiver = fromClient.readUTF();
                        Account recAcc = Server.returnUser(receiver);
                        if (recAcc == null) {
                            toClient.writeUTF(Server.MailServerMsg + "No such user exists." + Server.LoggedInOptions);
                        } else {
                            toClient.writeUTF(Server.MailServerMsg + "Subject:\n");
                            String subject = fromClient.readUTF();

                            while (subject.equals("")) {
                                toClient.writeUTF(Server.MailServerMsg + "Subject can't be empty");
                                subject = fromClient.readUTF();
                            }
                            while (subject.length() > 60) {
                                toClient.writeUTF(Server.MailServerMsg + "Subject can't have more than 60 characters:\n");
                                subject = fromClient.readUTF();
                            }

                            toClient.writeUTF(Server.MailServerMsg + "Main Body:\n");
                            String mainBody = fromClient.readUTF();

                            while (mainBody.equals("")) {
                                toClient.writeUTF(Server.MailServerMsg + "Subject can't be empty");
                                mainBody = fromClient.readUTF();
                            }

                            Server.newEmail(recAcc, username, receiver, subject, mainBody);
                            toClient.writeUTF(Server.MailServerMsg + "Email sent successfully!" + Server.LoggedInOptions);

                        }
                    } else {
                        toClient.writeUTF(Server.MailServerMsg + "You are currently not LoggedIn to an account." + Server.LoggedOutOptions);
                    }
                } else if (request.equals("ShowEmails")) {
                    if (LoggedIn) {
                        toClient.writeUTF(Server.showEmails(userAcc));
                    }else{
                        toClient.writeUTF(Server.MailServerMsg + "You are currently not LoggedIn to an account." + Server.LoggedOutOptions);
                    }
                } else if (request.equals("ReadEmail")) {
                    if (LoggedIn) {
                        toClient.writeUTF("Type the Id Number of the email: ");
                        String IDString = fromClient.readUTF();
                        int ID = Integer.parseInt(IDString);
                        if (ID>userAcc.getMailbox().size() || ID<=0){
                            toClient.writeUTF(Server.MailServerMsg + "No such Id." + Server.LoggedInOptions);
                        }else{
                            toClient.writeUTF(Server.readEmail(userAcc,ID));
                        }
                    }else{
                        toClient.writeUTF(Server.MailServerMsg + "You are currently not LoggedIn to an account." + Server.LoggedOutOptions);
                    }
                } else if (request.equals("DeleteEmail")) {
                    if (LoggedIn) {
                        toClient.writeUTF("Type the Id Number of the email: ");
                        String IDString = fromClient.readUTF();
                        int ID = Integer.parseInt(IDString);
                        if (ID>userAcc.getMailbox().size() || ID<=0){
                            toClient.writeUTF(Server.MailServerMsg + "No such Id." + Server.LoggedInOptions);
                        }else {
                            Server.deleteEmail(userAcc, ID);
                            toClient.writeUTF(Server.MailServerMsg + "Email deleted successfully!" + Server.LoggedInOptions);
                        }
                    }else{
                        toClient.writeUTF(Server.MailServerMsg + "You are currently not LoggedIn to an account." + Server.LoggedOutOptions);
                    }
                } else if(request.equals("LogOut")){
                    if (LoggedIn) {
                        LoggedIn = false;
                        toClient.writeUTF(Server.logOut());
                    }else{
                        toClient.writeUTF(Server.MailServerMsg + "You are currently not LoggedIn to an account." + Server.LoggedOutOptions);
                    }
                }else {
                    toClient.writeUTF(Server.noSuchCommand(LoggedIn));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                toClient.close();
                fromClient.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
