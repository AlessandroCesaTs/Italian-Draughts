package logic;

import Exceptions.CantEatException;
import Exceptions.IllegalMovementException;
import Exceptions.NotOnDiagonalException;
import Exceptions.OutOfBoundsException;
import gui.GraphicBoard;

public interface GameInterface {
    void playTurn() throws CantEatException, IllegalMovementException, OutOfBoundsException, NotOnDiagonalException;

    void Move(Piece movingPiece, NeighborPosition targetPosition) throws OutOfBoundsException;

    void eat(Piece movingPiece, NeighborPosition targetPosition) throws OutOfBoundsException;

    void checkGameOver() throws OutOfBoundsException;

    boolean checkMultipleEating(Piece movingPiece);

    void checkPromotion(Piece movingPiece);

    void notifyObservers();

    void changeActivePlayer();

    void setGBoard(GraphicBoard gBoard);

    Player getActivePlayer();

    Player getInactivePlayer();

    int getCurrentRound();

    Board getBoard();

    Player getWinnerPlayer();

    boolean isGameOver();

    int getRoundWithoutEating();
}
