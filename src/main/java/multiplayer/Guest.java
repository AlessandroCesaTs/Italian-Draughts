package multiplayer;

import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Guest implements MultiplayerActions {
    private final int port = 10000;
    private final String host;
    private final Socket socket;
    private Point[] receivedMove;
    private final MoveListener ml;
    private int newMove;

    public Guest(String host) {
        this.host = host;
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ml = new MoveListener(socket, this);
        ml.start();
    }

    @Override
    public void sendMove(Point startTitle, Point endTitle, int typeOfMove) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String moveProtocol = String.format("0;%d;%d;%d;%d;%d", (int) startTitle.getX(), (int) startTitle.getY(),
                                                (int) endTitle.getX(), (int) endTitle.getY(), typeOfMove);
            bw.write(moveProtocol + System.lineSeparator());
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Point[] getReceivedMove() {
        if (newMove == 1) {
            newMove = 0;
            return receivedMove;
        } else {
            return null;
        }
    }

    public void setReceivedMove(Point[] receivedMove, int newMove) {
        this.newMove = newMove;
        this.receivedMove = receivedMove;
    }
}
