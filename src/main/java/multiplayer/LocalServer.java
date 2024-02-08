package multiplayer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalServer extends Thread{
    private final int port;
    private int nConnection;

    public LocalServer(int port) {
        this.nConnection = 0;
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port);
             ExecutorService executorService = Executors.newFixedThreadPool(2)
        ) {
            while (true) {
                Socket socket = serverSocket.accept();
                nConnection++;
                if(nConnection == 1)
                    System.out.println("Player 1 connected");
                if(nConnection == 2)
                    System.out.println("Player 2 connected");
            }
        } catch (Exception e) {
            System.out.println(String.format("Generic error occurred. Error message: \"%s\"", e.getMessage()));
        }
    }

    public int getnConnection() {
        return nConnection;
    }
}
