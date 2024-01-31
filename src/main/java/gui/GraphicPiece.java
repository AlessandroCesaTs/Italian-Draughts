package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphicPiece extends JPanel {
    public int col, row;
    public int xPos, yPos;
    public boolean isWhite;

    BufferedImage red;
    BufferedImage redKing;
    BufferedImage white;
    BufferedImage whiteKing;
    {
        try {
            red = ImageIO.read(ClassLoader.getSystemResourceAsStream("red.png"));
            redKing = ImageIO.read(ClassLoader.getSystemResourceAsStream("redKing.png"));
            white = ImageIO.read(ClassLoader.getSystemResourceAsStream("white.png"));
            whiteKing = ImageIO.read(ClassLoader.getSystemResourceAsStream("whiteKing.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    Image sprite;
    GraphicBoard graphicBoard;
    public GraphicPiece(GraphicBoard graphicBoard) {
        this.graphicBoard = graphicBoard;
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, null);
    }
}
