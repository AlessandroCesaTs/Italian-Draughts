package logic;

import exceptions.NoPieceOnWhiteException;

import java.util.List;

public interface GameInterface {

    void playTurn(Move move);

    void Move(Piece movingPiece, NeighborPosition targetPosition);

    void eat(Piece movingPiece, NeighborPosition targetPosition) ;

    void checkGameOver();

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

    Player getPlayer1();

}
