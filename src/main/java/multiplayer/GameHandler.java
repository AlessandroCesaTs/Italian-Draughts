package multiplayer;

import java.io.*;
import java.net.Socket;

public class GameHandler implements Runnable {
    private final Socket[] sockets;
    private int turnListener;

    public GameHandler(Socket[] sockets) {
        this.sockets = sockets;
        turnListener = 0;
    }

    @Override
    public void run() {
        try {
            BufferedReader brWhite = new BufferedReader(new InputStreamReader(sockets[0].getInputStream()));
            BufferedWriter bwWhite = new BufferedWriter(new OutputStreamWriter(sockets[0].getOutputStream()));
            BufferedReader brBlack = new BufferedReader(new InputStreamReader(sockets[1].getInputStream()));
            BufferedWriter bwBlack = new BufferedWriter(new OutputStreamWriter(sockets[1].getOutputStream()));

            while (true) {

                if (turnListener == 0) {
                    turnSender(brWhite, bwBlack);
                } else {
                    turnSender(brBlack, bwWhite);
                }

            }

        } catch (IOException e) {
            System.out.printf("Failed or interrupted I/O operation on client connection. Error message: \"%s\"", e.getMessage());
        }
    }

    private void turnSender(BufferedReader br, BufferedWriter bw) {
        try {

            String line = br.readLine();
            String[] command = line.split(";");

            if (command.length == 6)
                turnListener = Integer.parseInt(command[5]);

            bw.write(line);
            bw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
