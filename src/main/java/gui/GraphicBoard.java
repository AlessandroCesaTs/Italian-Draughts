package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

public class GraphicBoard extends JPanel {
    public static int tileSize = 100;
    int cols = 8;
    int rows = 8;
    Point currentTile = null;
    Point startTile = null;
    Point endTile = null;
    GraphicPiece draggedPiece = null;
    ArrayList<GraphicPiece> pieceList = new ArrayList<>();

    public GraphicBoard() {
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        addPieces();
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
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX() / tileSize;
                int y = (getHeight() - e.getY()) / tileSize;
                endTile = new Point(x, y);

                GraphicPiece piece = findPieceAtTile(startTile);
                if (piece != null) {
                    piece.moveTo(endTile.x, endTile.y);
                }
                draggedPiece = null;

                repaint();
            }
        });
    }

    public void addPieces() {
        for (int c=0; c < cols; c++) {
            for (int r = 0; r <= 2; r++) {
                if ((r+c) % 2 == 0){
                    pieceList.add(new NormalPiece(this, c, r, true));
                }
            }
            for (int r = 5; r < rows; r++) {
                if ((r+c) % 2 == 0){
                    pieceList.add(new NormalPiece(this, c, r, false));
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
    public GraphicPiece findPieceAtTile(Point tile) {
        return pieceList.stream()
                .filter(p -> p.col == tile.x && p.row == tile.y)
                .findFirst()
                .orElse(null);
    }
    public void resetBoard() {
        pieceList.clear();
        startTile = null;
        endTile = null;
        addPieces();
    }
}
