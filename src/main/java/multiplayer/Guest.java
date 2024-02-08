package multiplayer;

import java.io.IOException;
import java.net.Socket;

public class Guest {

    private final int port = 10000;
    private final String host = "192.168.137.1";
    private final Socket socket;

    public Guest() {
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
