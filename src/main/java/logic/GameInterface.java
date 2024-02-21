package logic;

import Exceptions.NoPieceOnWhiteException;
import Exceptions.OutOfBoundsException;

import java.util.List;

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

    int getRoundsWithoutEating();

    Team getActiveTeam();

    Piece getPiece(int row,int col) throws NoPieceOnWhiteException;

    List<BlackTile> getFullBlackTiles() throws NoPieceOnWhiteException;

}
