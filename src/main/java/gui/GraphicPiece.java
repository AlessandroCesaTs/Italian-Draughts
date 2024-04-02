package gui;

import logic.NeighborPosition;
import logic.Piece;
import logic.Team;

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
    private final Piece piece;
    final BufferedImage black;
    final BufferedImage blackKing;
    final BufferedImage white;
    final BufferedImage whiteKing;
    private Image sprite;

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

    public GraphicPiece(Piece piece, int col, int row, boolean isWhite) {
        this.piece = piece;
        this.row = row;
        this.col = col;
        this.xPos = col * GraphicBoardInterface.tileSize;
        this.yPos = row * GraphicBoardInterface.tileSize;
        this.isWhite = isWhite;

        if (isWhite) {
            this.sprite = white.getScaledInstance(GraphicBoardInterface.tileSize, GraphicBoardInterface.tileSize, BufferedImage.SCALE_SMOOTH);
        } else {
            this.sprite = black.getScaledInstance(GraphicBoardInterface.tileSize, GraphicBoardInterface.tileSize, BufferedImage.SCALE_SMOOTH);
        }
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, null);
    }

    public void moveTo(NeighborPosition destination) {
        if (destination.equals(NeighborPosition.TOP_RIGHT)) {
            col = col + 1;
            row = row + 1;
        } else if (destination.equals(NeighborPosition.TOP_LEFT)) {
            col = col - 1;
            row = row + 1;
        } else if (destination.equals(NeighborPosition.BOTTOM_RIGHT)) {
            col = col + 1;
            row = row - 1;
        } else {
            col = col - 1;
            row = row - 1;
        }
        xPos = col * GraphicBoardInterface.tileSize;
        yPos = row * GraphicBoardInterface.tileSize;
    }

    public void promote() {
        if (isWhite) {
            sprite = whiteKing.getScaledInstance(GraphicBoardInterface.tileSize, GraphicBoardInterface.tileSize, BufferedImage.SCALE_SMOOTH);
        } else {
            sprite = blackKing.getScaledInstance(GraphicBoardInterface.tileSize, GraphicBoardInterface.tileSize, BufferedImage.SCALE_SMOOTH);
        }
    }

    public Piece getPiece() {
        return piece;
    }

    public Team getTeam() {
        return getPiece().getTeam();
    }

}
