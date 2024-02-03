package gui;

import Exceptions.CantEatException;
import Exceptions.IllegalMovementException;
import Exceptions.NoPieceOnWhiteException;
import Exceptions.OutOfBoundsException;
import logic.*;
import observers.GameObserver;

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

                if (draggedPiece != null) {
                    System.out.println("Dragged piece's team: " + ((NormalPiece) draggedPiece).getPiece().getTeam());
                    System.out.println("Active player's team: " + game.getActivePlayer().getTeam());
                    if (((NormalPiece) draggedPiece).getPiece().getTeam() != game.getActivePlayer().getTeam()) {
                        draggedPiece = null;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX() / tileSize;
                int y = (getHeight() - e.getY()) / tileSize;
                endTile = new Point(x, y);

                GraphicPiece piece = findPieceAtTile(startTile);
                if (piece != null && ((NormalPiece) piece).getPiece().getTeam() == game.getActivePlayer().getTeam()) {
                    piece.moveTo(endTile.x, endTile.y);
                    setMoveMade(true);
                }
                draggedPiece = null;

                repaint();

                setMoveMade(false);
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
    public Move getMoveFromGUI() {
        if (startTile == null || endTile == null || draggedPiece == null) {
            return null;
        }

        Piece piece = ((NormalPiece) draggedPiece).getPiece();
        Player player = game.getActivePlayer();
        BlackTile destination = game.getBoard().getTileAtPosition(endTile);
        NeighborPosition neighborDestination = getNeighborPosition(startTile, endTile);

        try {
            return new Move(player, piece, destination, neighborDestination);
        } catch (IllegalMovementException | CantEatException | OutOfBoundsException e) {
            return null;
        }
    }
    public GraphicPiece findPieceAtTile(Point tile) {
        return pieceList.stream()
                .filter(p -> p.col == tile.x && p.row == tile.y)
                .findFirst()
                .orElse(null);
    }
    public NeighborPosition getNeighborPosition(Point startTile, Point endTile) {
        int dx = endTile.x - startTile.x;
        int dy = endTile.y - startTile.y;

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
    }
    public boolean hasMoveBeenMade() {
        return moveMade;
    }
    public void setMoveMade(boolean moveMade) {
        this.moveMade = moveMade;
    }
}
