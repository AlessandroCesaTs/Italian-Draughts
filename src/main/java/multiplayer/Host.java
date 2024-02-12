package multiplayer;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Host {

    private final int port = 10000;
    private final String host = "127.0.0.1";
    private final Socket socket;
    private final LocalServer localServer;



    public Host() {
        localServer = new LocalServer(port);
        try {
            localServer.start();
            socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMove(Point startTitle, Point endTitle, int messageType) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String moveProtocol = String.format("0;%d;%d;%d;%d;%d", startTitle.getX(), startTitle.getY(),
                                                endTitle.getX(), endTitle.getY(),messageType);
            bw.write(moveProtocol);
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Point[] receiveMove() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = br.readLine();
            String[] command = line.split(";");
            switch (Integer.parseInt(command[0])){
                case 0:
                    if (command.length != 6)
                        throw new RuntimeException("Move is not passed correctly, something has gone wrong!");
                    Point oppStartTitle = new Point(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                    Point oppEndTitle = new Point(Integer.parseInt(command[3]), Integer.parseInt(command[4]));
                    return new Point[]{oppStartTitle, oppEndTitle};
                case 1:
                    return null; //aggiungere fine partita
                default:
                    throw new RuntimeException("Something has gone wrong!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LocalServer getLocalServer() {
        return localServer;
    }

}
