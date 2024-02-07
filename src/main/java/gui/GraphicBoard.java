package gui;

import Exceptions.*;
import logic.*;
import observers.GameObserver;
import observers.MoveMadeObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GraphicBoard extends JPanel{
    private Game game;
    public static int tileSize = 100;
    int cols = 8;
    int rows = 8;
    Point currentTile = null;
    Point startTile = null;
    Point endTile = null;
    GraphicPiece draggedPiece = null;
    ArrayList<GraphicPiece> pieceList = new ArrayList<>();
    private List<MoveMadeObserver> observers = new ArrayList<>();
    private boolean moveMade = false;

    public GraphicBoard(Game game) {
        setGame(game);
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX() / tileSize;
                int y = e.getY() / tileSize;
                currentTile = new Point(x, y);
                repaint();
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedPiece != null) {
                    int x = e.getX() / tileSize;
                    int y = (getHeight() - e.getY()) / tileSize;
                    if ((x + y) % 2 == 0) {
                        draggedPiece.moveTo(x, y);
                        repaint();
                    }
                }
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / tileSize;
                int y = (getHeight() - e.getY()) / tileSize;
                startTile = new Point(x, y);
                draggedPiece = findPieceAtTile(startTile);

                if (draggedPiece != null && ((NormalPiece) draggedPiece).getPiece().getTeam() != game.getActivePlayer().getTeam()) {
                    draggedPiece = null;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX() / tileSize;
                int y = (getHeight() - e.getY()) / tileSize;
                endTile = new Point(x, y);

                GraphicPiece piece = draggedPiece;
                if (piece != null && ((NormalPiece) piece).getPiece().getTeam() == game.getActivePlayer().getTeam()) {
                    piece.moveTo(endTile.x, endTile.y);
                    try {
                        setMoveMade(true);
                    } catch (NotOnDiagonalException | CantEatException | IllegalMovementException | OutOfBoundsException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                draggedPiece = null;
                repaint();
            }
        });
    }
    public void setGame(Game game) {
        this.game = game;
        resetBoard();
    }
    public void resetBoard() {
        pieceList.clear();
        startTile = null;
        endTile = null;
        addPieces();
    }
    public void addPieces() {
        for (int c=0; c < cols; c++) {
            for (int r = 0; r <= 2; r++) {
                if ((r+c) % 2 == 0){
                    Piece piece = null;
                    try {
                        piece = game.getBoard().getPiece(r, c);
                    } catch (NoPieceOnWhiteException e) {
                        throw new RuntimeException(e);
                    }
                    pieceList.add(new NormalPiece(this, piece, c, r, true));
                }
            }
            for (int r = 5; r < rows; r++) {
                if ((r+c) % 2 == 0){
                    Piece piece = null;
                    try {
                        piece = game.getBoard().getPiece(r, c);
                    } catch (NoPieceOnWhiteException e) {
                        throw new RuntimeException(e);
                    }
                    pieceList.add(new NormalPiece(this, piece, c, r, false));
                }
            }
        }
    }

    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        // Translate the origin to the bottom left
        g2d.translate(0, getHeight());
        g2d.scale(1, -1);


        for (int r=0; r < rows; r++) {
            for (int c=0; c < cols; c++) {
                g2d.setColor((c+r) %2 ==0 ? new Color(132, 84, 35) : new Color(229, 183, 145, 255));
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
                if (currentTile != null && currentTile.x == c && currentTile.y == rows - r - 1) {
                    g2d.setColor(Color.RED); // set the border color
                    g2d.setStroke(new BasicStroke(3)); // set the border thickness
                    g2d.drawRect(c * tileSize, r * tileSize, tileSize, tileSize);
                }
            }
        }

        for (GraphicPiece graphicPiece : pieceList) {
            graphicPiece.paint(g2d);
        }
    }
    public Move getMoveFromGUI() throws NotOnDiagonalException, CantEatException, IllegalMovementException, OutOfBoundsException {
        if (startTile == null || endTile == null || draggedPiece == null) {
            return null;
        }
        if(endTile.x>cols-1 || endTile.y>rows-1){
            return null;
        }

        Piece piece = ((NormalPiece) draggedPiece).getPiece();
        Player player = game.getActivePlayer();
        BlackTile destination = game.getBoard().getTileAtPosition(endTile);
        NeighborPosition neighborDestination = getNeighborPosition(endTile);
        System.out.println("piece"+piece+",player"+player+",destination"+destination+",neighborDestination"+neighborDestination);

         Move move=new Move(player, piece, destination, neighborDestination);
         if (move.getIfMoveIsValid()){
             return move;
         }else{
             return null;
         }

    }
    public GraphicPiece findPieceAtTile(Point tile) {
        return pieceList.stream()
                .filter(p -> p.col == tile.x && p.row == tile.y)
                .findFirst()
                .orElse(null);
    }

    public int rowDiff(Point endTile){
        return endTile.y-startTile.y;
    }
    public int colDiff(Point endTile){
        return endTile.x-startTile.x;
    }
    public boolean isOnDiagonal(Point endTile){
        return Math.abs(rowDiff(endTile))==Math.abs(colDiff(endTile)) && rowDiff(endTile)!=0;
    }
    public NeighborPosition getNeighborPosition(Point endTile) throws NotOnDiagonalException {

        int rowDiff=rowDiff(endTile);
        int colDiff=colDiff(endTile);
        if (isOnDiagonal(endTile)) {
            if (rowDiff > 0) {
                if (colDiff > 0) {
                    return NeighborPosition.TopRight;
                } else {
                    return NeighborPosition.TopLeft;
                }
            } else {
                if (colDiff > 0) {
                    return NeighborPosition.BottomRight;
                } else {
                    return NeighborPosition.BottomLeft;
                }
            }
        }else{
            throw new NotOnDiagonalException();
        }
    }

        /*
        if (dx == 1 && dy == 1) {
            return NeighborPosition.BottomRight;
        } else if (dx == -1 && dy == 1) {
            return NeighborPosition.BottomLeft;
        } else if (dx == 1 && dy == -1) {
            return NeighborPosition.TopRight;
        } else if (dx == -1 && dy == -1) {
            return NeighborPosition.TopLeft;
        } else {
            return null;
        }
         */
    public void addMoveMadeObserver(MoveMadeObserver observer) {
        observers.add(observer);
    }
    public void notifyMoveMadeObservers() throws NotOnDiagonalException, CantEatException, IllegalMovementException, OutOfBoundsException {
        for (MoveMadeObserver observer : observers) {
            observer.onMoveMade();
        }
    }
    public void setMoveMade(boolean moveMade) throws NotOnDiagonalException, CantEatException, IllegalMovementException, OutOfBoundsException {
        this.moveMade = moveMade;
        if (moveMade) {
            notifyMoveMadeObservers();
        }
    }
}
