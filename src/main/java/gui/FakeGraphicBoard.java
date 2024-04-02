package gui;

import logic.Move;
import logic.NeighborPosition;
import logic.Piece;
import observers.MoveMadeObserver;

import java.awt.*;

public class FakeGraphicBoard implements GraphicBoardInterface{

    @Override
    public GraphicPiece getGraphicPiece(Piece piece) {
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public Move getMoveFromGUI() {
        return null;
    }

    @Override
    public void eatPiece(Piece eatingPiece, NeighborPosition destination) {

    }

    @Override
    public void movePiece(Piece movingPiece, NeighborPosition destination) {

    }

    @Override
    public void addMoveMadeObserver(MoveMadeObserver observer) {

    }

    @Override
    public Point getStartTile() {
        return null;
    }

    @Override
    public void setStartTile(Point startTile) {

    }

    @Override
    public Point getEndTile() {
        return null;
    }

    @Override
    public void setEndTile(Point endTile) {

    }
}
