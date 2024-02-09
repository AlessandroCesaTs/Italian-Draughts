package multiplayer;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Host {

    private final int port = 10000;
    private final String host = "127.0.0.1";
    private final Socket socket;
    private final LocalServer localServer;

    public Host() {
        localServer = new LocalServer(port);
        try {
            localServer.start();
            socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LocalServer getLocalServer() {
        return localServer;
    }
}
