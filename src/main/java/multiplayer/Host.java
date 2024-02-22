package multiplayer;

import logic.Game;

import java.awt.*;
import java.io.*;
import java.net.Socket;


public class Host implements MultiplayerActions,Runnable {

    private final int port = 10000;
    private final String host = "127.0.0.1";
    private Socket socket;
    private final LocalServer localServer;
    private BufferedReader br;
    private BufferedWriter bw;
    private boolean running = false;
    private final Game game;



    public Host(Game game) {
        this.game = game;
        localServer = new LocalServer(port);
        localServer.start();
        }

    @Override
    public void sendMove(Point startTitle, Point endTitle, int typeOfMove) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String moveProtocol = String.format("0;%d;%d;%d;%d;%d", (int) startTitle.getX(), (int) startTitle.getY(),
                                (int) endTitle.getX(), (int) endTitle.getY(),typeOfMove);
            bw.write(moveProtocol + System.lineSeparator());
            bw.flush();
            System.out.println("Mossa mandata Host");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Point[] receiveMove() {
        try {
            System.out.println("Sto ascoltando");
            String line = br.readLine();
            System.out.println("Mossa ricevuta host");
            String[] command = line.split(";");
            switch (Integer.parseInt(command[0])){
                case 0:
                    if (command.length != 6)
                        throw new RuntimeException("Move is not passed correctly, something has gone wrong!");
                    Point oppStartTitle = new Point(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                    Point oppEndTitle = new Point(Integer.parseInt(command[3]), Integer.parseInt(command[4]));
                    Point oppTurnNotify =  new Point(Integer.parseInt(command[5]), 0);
                    System.out.println("la mossa è" + line);
                    return new Point[]{oppStartTitle, oppEndTitle, oppTurnNotify};
                case 1:
                    return null; //aggiungere fine partita
                default:
                    throw new RuntimeException("Something has gone wrong!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connect(){
        try {
            socket = new Socket(host, port);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            new Thread(this).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
        game.getGBoard().setStartTile(advMove[0]);
        game.getGBoard().setEndTile(advMove[1]);
        if (advMove[2].getX() == 1)
            setAdversaryMove(receiveMove());
        else
            game.changeActivePlayer();
    }

    public LocalServer getLocalServer() {
        return localServer;
    }

}
