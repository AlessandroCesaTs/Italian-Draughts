package multiplayer;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MoveListener extends Thread {

    private final Socket socket;
    private final MultiplayerActions multiRole;

    public MoveListener(Socket socket, MultiplayerActions multiRole) {
        this.socket = socket;
        this.multiRole = multiRole;
    }

    @Override
    public void run(){
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                multiRole.setReceivedMove(receiveMove(br),1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Point[] receiveMove(BufferedReader br) {
        try {
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
}
