package multiplayer;

import java.io.*;
import java.net.Socket;

public class GameHandler implements Runnable {
    private final Socket[] sockets;

    public GameHandler(Socket[] sockets) {
        this.sockets = sockets;
    }

    @Override
    public void run() {
        try {
            BufferedReader brWhite = new BufferedReader(new InputStreamReader(sockets[0].getInputStream()));
            BufferedWriter bwWhite = new BufferedWriter(new OutputStreamWriter(sockets[0].getOutputStream()));
            BufferedReader brBlack = new BufferedReader(new InputStreamReader(sockets[1].getInputStream()));
            BufferedWriter bwBlack = new BufferedWriter(new OutputStreamWriter(sockets[1].getOutputStream()));


        } catch (IOException e) {
            System.out.println(String.format("Failed or interrupted I/O operation on client connection. Error message: \"%s\"", e.getMessage()));
        }
    }
}
