package gui;

import java.awt.image.BufferedImage;

public class NormalPiece extends GraphicPiece{
    public NormalPiece(GraphicBoard graphicBoard, int col, int row, boolean isWhite){
        super(graphicBoard);
        this.row = row;
        this.col = col;
        this.xPos = col * GraphicBoard.tileSize;
        this.yPos = row * GraphicBoard.tileSize;
        this.isWhite = isWhite;

        if(isWhite){
            this.sprite = white.getScaledInstance(GraphicBoard.tileSize, GraphicBoard.tileSize, BufferedImage.SCALE_SMOOTH);
        } else {
            this.sprite = red.getScaledInstance(GraphicBoard.tileSize, GraphicBoard.tileSize, BufferedImage.SCALE_SMOOTH);
        }
    }
}
