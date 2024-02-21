package gui;

import logic.Piece;
import logic.Team;

import java.awt.image.BufferedImage;

public class NormalPiece extends GraphicPiece{
    private final Piece piece;
    public NormalPiece(GraphicBoard graphicBoard, Piece piece, int col, int row, boolean isWhite){
        super(graphicBoard);
        this.piece = piece;
        this.row = row;
        this.col = col;
        this.xPos = col * GraphicBoard.tileSize;
        this.yPos = row * GraphicBoard.tileSize;
        this.isWhite = isWhite;

        if(isWhite){
            this.sprite = white.getScaledInstance(GraphicBoard.tileSize, GraphicBoard.tileSize, BufferedImage.SCALE_SMOOTH);
        } else {
            this.sprite = black.getScaledInstance(GraphicBoard.tileSize, GraphicBoard.tileSize, BufferedImage.SCALE_SMOOTH);
        }
    }
    public Piece getPiece(){
        return piece;
    }

    public Team getTeam(){return getPiece().getTeam();}
}
