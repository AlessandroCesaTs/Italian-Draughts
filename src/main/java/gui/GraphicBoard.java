package gui;

import logic.*;
import observers.MoveMadeObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

public class GraphicBoard extends JPanel implements GraphicBoardInterface {
    private GameInterface gameInterface;
    final int cols = 8;
    final int rows = 8;
    Point currentTile = null;
    Point startTile = null;
    Point endTile = null;
    GraphicPiece draggedPiece = null;
    final ArrayList<GraphicPiece> pieceList = new ArrayList<>();
    private final List<MoveMadeObserver> observers = new ArrayList<>();

    public GraphicBoard(GameInterface gameInterface) {
        setGame(gameInterface);
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX() / tileSize;
                int y = e.getY() / tileSize;
                currentTile = new Point(x, y);
                repaint();
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / tileSize;
                int y = (getHeight() - e.getY()) / tileSize;
                startTile = new Point(x, y);
                draggedPiece = findPieceAtTile(startTile);

                if (draggedPiece != null && draggedPieceIsNotOfActivePlayer() || moveComesFromOtherPlayer()) {
                    draggedPiece = null;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX() / tileSize;
                int y = (getHeight() - e.getY()) / tileSize;
                endTile = new Point(x, y);

                getNeighborPosition(endTile);

                GraphicPiece piece = draggedPiece;
                if (piece != null && piece.getTeam() == gameInterface.getActiveTeam()) {
                    setCurrentTile(endTile);

                    setMoveMade(true);
                }
                draggedPiece = null;
            }
        });
    }

    private boolean moveComesFromOtherPlayer() {
        return gameInterface.getPlayer1().getRole() != getActivePlayer().getRole();
    }

    private boolean draggedPieceIsNotOfActivePlayer() {

        return draggedPiece.getPiece().getTeam() != gameInterface.getActivePlayer().getTeam();
    }

    private void setCurrentTile(Point currentTile) {
        this.currentTile = currentTile;
    }

    private void movePieceTo(GraphicPiece piece, NeighborPosition destination) {
        piece.moveTo(destination);
        repaint();
    }


    private void removePiece(GraphicPiece piece) {
        pieceList.remove(piece);
        repaint();
    }

    @Override
    public GraphicPiece getGraphicPiece(Piece piece) {
        GraphicPiece graphicPiece = null;
        for (GraphicPiece g : pieceList) {
            if (g.getPiece() == piece) {
                graphicPiece = g;
            }
        }
        return graphicPiece;
    }

    private void setGame(GameInterface gameInterface) {
        this.gameInterface = gameInterface;
        resetBoard();
    }

    private void resetBoard() {
        pieceList.clear();
        startTile = null;
        endTile = null;
        addPieces();
    }

    private void addPieces() {
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r <= 2; r++) {
                if ((r + c) % 2 == 0) {
                    Piece piece;

                    piece = gameInterface.getPiece(r, c);

                    pieceList.add(new GraphicPiece(piece, c, r, true));
                }
            }
            for (int r = 5; r < rows; r++) {
                if ((r + c) % 2 == 0) {
                    Piece piece;

                    piece = gameInterface.getPiece(r, c);

                    pieceList.add(new GraphicPiece(piece, c, r, false));
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Translate the origin to the bottom left
        g2d.translate(0, getHeight());
        g2d.scale(1, -1);


        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                g2d.setColor((c + r) % 2 == 0 ? new Color(132, 84, 35) : new Color(229, 183, 145, 255));
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

    @Override
    public Move getMoveFromGUI() {
        if (startTile == null || endTile == null || draggedPiece == null) {
            return null;
        }
        if (endTile.x > cols - 1 || endTile.y > rows - 1) {
            return null;
        }


        Piece piece = draggedPiece.getPiece();
        Player player = gameInterface.getActivePlayer();
        NeighborPosition neighborDestination = getNeighborPosition(endTile);

        return new Move(player, piece, neighborDestination);

    }

    @Override
    public void eatPiece(Piece eatingPiece, NeighborPosition destination) {
        GraphicPiece eatingGraphicPiece = getGraphicPiece(eatingPiece);
        GraphicPiece targetGraphicPiece = getGraphicPiece(eatingPiece.getTile().getNeighbor(destination).getPiece());
        removePiece(targetGraphicPiece);
        movePieceTo(eatingGraphicPiece, destination);
        movePieceTo(eatingGraphicPiece, destination);
    }

    @Override
    public void movePiece(Piece movingPiece, NeighborPosition destination) {
        GraphicPiece movingGraphicPiece = getGraphicPiece(movingPiece);
        movePieceTo(movingGraphicPiece, destination);
    }

    private GraphicPiece findPieceAtTile(Point tile) {
        return pieceList.stream()
                .filter(p -> p.col == tile.x && p.row == tile.y)
                .findFirst()
                .orElse(null);
    }

    private int rowDiff(Point endTile) {
        return endTile.y - startTile.y;
    }

    private int colDiff(Point endTile) {
        return endTile.x - startTile.x;
    }

    private boolean isOnDiagonal(Point endTile) {
        return Math.abs(rowDiff(endTile)) == Math.abs(colDiff(endTile)) && rowDiff(endTile) != 0;
    }

    private NeighborPosition getNeighborPosition(Point endTile) {

        int rowDiff = rowDiff(endTile);
        int colDiff = colDiff(endTile);
        if (isOnDiagonal(endTile)) {
            if (rowDiff > 0) {
                if (colDiff > 0) {
                    return NeighborPosition.TOP_RIGHT;
                } else {
                    return NeighborPosition.TOP_LEFT;
                }
            } else {
                if (colDiff > 0) {
                    return NeighborPosition.BOTTOM_RIGHT;
                } else {
                    return NeighborPosition.BOTTOM_LEFT;
                }
            }
        } else {
            return null;
        }
    }

    @Override
    public void addMoveMadeObserver(MoveMadeObserver observer) {
        observers.add(observer);
    }

    private void notifyMoveMadeObservers() {
        for (MoveMadeObserver observer : observers) {
            observer.onMoveMade();
        }
    }

    private void setMoveMade(boolean moveMade) {
        if (moveMade) {
            notifyMoveMadeObservers();
        }
    }

    @Override
    public Point getStartTile() {
        return startTile;
    }

    @Override
    public void setStartTile(Point startTile) {
        this.startTile = startTile;
        draggedPiece = findPieceAtTile(startTile);
    }

    @Override
    public Point getEndTile() {
        return endTile;
    }

    @Override
    public void setEndTile(Point endTile) {
        this.endTile = endTile;
        getNeighborPosition(endTile);
        setCurrentTile(endTile);

        setMoveMade(true);

        draggedPiece = null;
    }

    private Player getActivePlayer() {
        return gameInterface.getActivePlayer();
    }
}
