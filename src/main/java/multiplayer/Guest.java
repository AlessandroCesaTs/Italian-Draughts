package multiplayer;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Guest {
    private final int port = 10000;
    private final String host;
    private final Socket socket;


    public Guest(String host) {
        this.host = host;
        try {
            this.socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
