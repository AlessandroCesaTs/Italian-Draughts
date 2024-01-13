package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphicBoard extends JPanel {
    public static int tileSize = 85;
    int cols = 8;
    int rows = 8;

    ArrayList<GraphicPiece> pieceList = new ArrayList<>();

    public GraphicBoard() {
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        addPieces();
    }

    public void addPieces() {
        for (int c=0; c < cols; c++) {
            for (int r = 0; r <= 2; r++) {
                if ((r+c) % 2 != 0){
                    pieceList.add(new NormalPiece(this, c, r, false));
                }
            }
            for (int r = 5; r < rows; r++) {
                if ((r+c) % 2 != 0){
                    pieceList.add(new NormalPiece(this, c, r, true));
                }
            }
        }
    }

    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        for (int r=0; r < rows; r++) {
            for (int c=0; c < cols; c++) {
                g2d.setColor((c+r) %2 ==0 ? new Color(229, 183, 145, 255) : new Color(132, 84, 35));
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
            }
        }

        for (GraphicPiece graphicPiece : pieceList) {
            graphicPiece.paint(g2d);
        }
    }
}
