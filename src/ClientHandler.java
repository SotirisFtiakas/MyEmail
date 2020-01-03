import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Runnable;
import java.net.Socket;


public class ClientHandler implements Runnable {

    private Socket client;
    private DataInputStream fromServer;
    private DataOutputStream toServer;


    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        this.fromServer = new DataInputStream(client.getInputStream());
        this.toServer = new DataOutputStream(client.getOutputStream());
    }


    @Override
    public void run() {
        try {
            String request = fromServer.readUTF();
            if (request.equals("exit")){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                toServer.close();
                fromServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
