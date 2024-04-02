package gui;

import logic.Move;
import logic.NeighborPosition;
import logic.Piece;
import observers.MoveMadeObserver;

import java.awt.*;

public interface GraphicBoardInterface {
    int tileSize = 75;

    GraphicPiece getGraphicPiece(Piece piece);

    void paintComponent(Graphics g);

    Move getMoveFromGUI();

    void eatPiece(Piece eatingPiece, NeighborPosition destination);

    void movePiece(Piece movingPiece, NeighborPosition destination);

    void addMoveMadeObserver(MoveMadeObserver observer);

    Point getStartTile();

    void setStartTile(Point startTile);

    Point getEndTile();

    void setEndTile(Point endTile);
}
