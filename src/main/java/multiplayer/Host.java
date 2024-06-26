package multiplayer;

import gui.GraphicBoardInterface;
import logic.Game;

import java.awt.*;
import java.io.*;
import java.net.Socket;


public class Host implements MultiplayerActions, Runnable {

    private Socket socket;
    private final LocalServer localServer;
    private BufferedReader br;
    private BufferedWriter bw;
    private boolean running = false;
    private final Game game;

    public Host(Game game) {
        this.game = game;
        localServer = new LocalServer(10000);
        localServer.start();
    }

    @Override
    public void sendMove(Point startTitle, Point endTitle, int typeOfMove) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String moveProtocol = String.format("%d;%d;%d;%d;%d", (int) startTitle.getX(), (int) startTitle.getY(),
                    (int) endTitle.getX(), (int) endTitle.getY(), typeOfMove);
            bw.write(moveProtocol + System.lineSeparator());
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Point[] receiveMove() {
        try {
            String line = br.readLine();
            if (line == null)
                throw new RuntimeException("Something went wrong!");
            else {
                String[] command = line.split(";");

                if (Integer.parseInt(command[4]) == 2) {
                    close();
                    return null;
                }

                if (command.length != 5)
                    throw new RuntimeException("Move is not passed correctly, something has gone wrong!");
                Point oppStartTitle = new Point(Integer.parseInt(command[0]), Integer.parseInt(command[1]));
                Point oppEndTitle = new Point(Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                Point oppTurnNotify = new Point(Integer.parseInt(command[4]), 0);
                return new Point[]{oppStartTitle, oppEndTitle, oppTurnNotify};
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connect() {
        try {
            socket = new Socket("127.0.0.1", 10000);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            new Thread(this).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            running = false;
            br.close();
            bw.close();
            socket.close();
            localServer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            running = true;

            while (running) {
                setAdversaryMove(receiveMove());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setAdversaryMove(Point[] advMove) {
        if (advMove == null)
            return;

        GraphicBoardInterface graphicBoard = game.getGBoard();
        graphicBoard.setStartTile(advMove[0]);
        graphicBoard.setEndTile(advMove[1]);
        if (advMove[2].getX() == 1)
            setAdversaryMove(receiveMove());
    }

    public LocalServer getLocalServer() {
        return localServer;
    }

}
