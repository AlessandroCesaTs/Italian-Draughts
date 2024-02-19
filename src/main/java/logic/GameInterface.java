package logic;

import Exceptions.CantEatException;
import Exceptions.IllegalMovementException;
import Exceptions.NotOnDiagonalException;
import Exceptions.OutOfBoundsException;
import gui.GraphicBoard;

public interface GameInterface {

    void playTurn(Move move) throws OutOfBoundsException;

    void Move(Piece movingPiece, NeighborPosition targetPosition) throws OutOfBoundsException;

    void eat(Piece movingPiece, NeighborPosition targetPosition) throws OutOfBoundsException;

    void checkGameOver() throws OutOfBoundsException;

    boolean checkMultipleEating(Piece movingPiece);

    void changeActivePlayer();

    Player getActivePlayer();

    Player getInactivePlayer();

    int getCurrentRound();

    Board getBoard();

    Player getWinnerPlayer();

    boolean isGameOver();

    int getRoundWithoutEating();
}
