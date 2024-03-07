package multiplayer;

import gui.GraphicBoard;
import logic.Game;

import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Guest implements MultiplayerActions,Runnable {
    private final String host;
    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private boolean running = false;
    private final Game game;

    public Guest(String host,Game game) {
        this.host = host;
        this.game = game;
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
            String[] command = line.split(";");
            if (command.length != 5)
                throw new RuntimeException("Move is not passed correctly, something has gone wrong!");
            Point oppStartTitle = new Point(Integer.parseInt(command[0]), Integer.parseInt(command[1]));
            Point oppEndTitle = new Point(Integer.parseInt(command[2]), Integer.parseInt(command[3]));
            Point oppTurnNotify = new Point(Integer.parseInt(command[4]), 0);
            return new Point[]{oppStartTitle, oppEndTitle, oppTurnNotify};
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void connect(){
        try {
            socket = new Socket(host, 10000);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            new Thread(this).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close(){
        try {
            running = false;
            br.close();
            bw.close();
            socket.close();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            running = true;

            while (running){
                setAdversaryMove(receiveMove());
            }

            close();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void setAdversaryMove (Point[] advMove) {
        GraphicBoard graphicBoard = game.getGBoard();
        graphicBoard.setStartTile(advMove[0]);
        graphicBoard.setEndTile(advMove[1]);
        if (advMove[2].getX() == 1)
            setAdversaryMove(receiveMove());
    }

}
