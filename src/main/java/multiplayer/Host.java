package multiplayer;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Host {

    private final int port = 10000;
    private final String host = "127.0.0.1";
    private final LocalServer localServer;

    public Host() {
        this.localServer = new LocalServer(port);

    }

    public LocalServer getLocalServer() {
        return localServer;
    }
}
