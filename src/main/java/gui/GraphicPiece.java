package gui;

import logic.NeighborPosition;
import logic.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GraphicPiece extends JPanel {
    public int col, row;
    public int xPos, yPos;
    public boolean isWhite;
    BufferedImage black;
    BufferedImage blackKing;
    BufferedImage white;
    BufferedImage whiteKing;
    {
        try {
            black = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("black.png")));
            blackKing = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("blackKing.png")));
            white = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("white.png")));
            whiteKing = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("whiteKing.png")));
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
    public void moveTo(int x, int y) {
        this.col = x;
        this.row = y;
        this.xPos = x * GraphicBoard.tileSize;
        this.yPos = y * GraphicBoard.tileSize;
    }
    public void moveTo(NeighborPosition destination) {
        if (destination.equals(NeighborPosition.TopRight)){
            col=col+1;
            row=row+1;
        } else if(destination.equals(NeighborPosition.TopLeft)){
            col=col-1;
            row=row+1;
        }
        else if(destination.equals(NeighborPosition.BottomRight)){
            col=col+1;
            row=row-1;
        }else{
            col=col-1;
            row=row-1;
        }
        xPos = col * GraphicBoard.tileSize;
        yPos = row * GraphicBoard.tileSize;
    }
    public Point getPosition() {
        return new Point(col, row);
    }

}
