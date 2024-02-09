package multiplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class LocalServer extends Thread {

    private final int port;
    private int nConnections;
    private Socket[] sockets;

    public LocalServer(int port) {
        this.port = port;
        this.nConnections = 0;
        sockets = new Socket[2];
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port);
             ExecutorService executorService = Executors.newFixedThreadPool(2)
        ) {
            while (true) {
                sockets[nConnections] = serverSocket.accept();
                nConnections++;
                if (nConnections == 2) {
                    executorService.execute(new GameHandler(sockets));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getnConnections() {
        return nConnections;
    }
}
