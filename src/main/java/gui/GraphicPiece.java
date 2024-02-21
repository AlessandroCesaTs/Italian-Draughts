package gui;

import logic.NeighborPosition;

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
    final BufferedImage black;
    final BufferedImage blackKing;
    final BufferedImage white;
    final BufferedImage whiteKing;
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
    public void promote(){
        if (isWhite) {
            sprite = whiteKing.getScaledInstance(GraphicBoard.tileSize, GraphicBoard.tileSize, BufferedImage.SCALE_SMOOTH);
        } else {
            sprite = blackKing.getScaledInstance(GraphicBoard.tileSize, GraphicBoard.tileSize, BufferedImage.SCALE_SMOOTH);
        }
    }
    public Point getPosition() {
        return new Point(col, row);
    }

}
