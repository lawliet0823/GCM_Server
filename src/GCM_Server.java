/**
 * Created by L on 2015/10/31.
 */

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GCM_Server {
    public static void main(String args[]) throws IOException {

        ArrayList<String> regID = new ArrayList<String>();
        String apiKey = "AIzaSyDMK8mwtxPjvxyGMTpMZwp3ylD2WTaYuSs";
        Sender sender = new Sender(apiKey);

        int port = 80;
        System.out.println("server start to listen client");

        ServerSocket ss = new ServerSocket(port);
        Socket cs = ss.accept();

        while (true) {
            try {
                DataInputStream in = new DataInputStream(cs.getInputStream());

                System.out.println("IP:"
                        + cs.getRemoteSocketAddress().toString());
                if(cs.isConnected())
                    System.out.println("Connected");
                else {
                    System.out.println("NO");
                }

                String receiveMessage = in.readUTF();

                if (receiveMessage.length() > 100) {
                    regID.add(receiveMessage);
                    System.out.println("Got it");
                } else {
                    System.out.println(receiveMessage);
                    Message message = new Message.Builder().addData("Message",
                            receiveMessage).build();
                    sender.send(message, regID, 10);
                }

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

    }
}