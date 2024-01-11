package multiplayer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalServer {
    private final int port;

    public LocalServer(int port) {
        this.port = port;
    }

    public int run() {
        try (ServerSocket serverSocket = new ServerSocket(port);
             ExecutorService executorService = Executors.newFixedThreadPool(2)
        ) {
            while (true) {
                Socket socket = serverSocket.accept();
            }
        } catch (Exception e) {
            System.out.println(String.format("Generic error occurred. Error message: \"%s\"", e.getMessage()));
        }
        return 0;   //we will never get here
    }
}
