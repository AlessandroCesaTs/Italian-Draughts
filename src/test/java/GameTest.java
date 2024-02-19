import Exceptions.*;
import logic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    MockGame game =new MockGame("Anna","Mario", Team.White,Team.Black);
    Board board=game.getBoard();
    Player player1=game.getActivePlayer();
    Player player2=game.getInactivePlayer();

    public GameTest() throws IllegalTilePlacementException, NoPieceOnWhiteException, IllegalTeamsCompositionException, CantEatException, IllegalMovementException, OutOfBoundsException, NotOnDiagonalException {
    }
    @Test
    void changeActivePlayerTest() throws NoPieceOnWhiteException, OutOfBoundsException, CantEatException, IllegalMovementException, NotOnDiagonalException {
        Piece piece=board.getPiece(2,2);
        game.playTurn(new Move(player1,piece,NeighborPosition.TopRight));
        assertEquals(player2,game.getActivePlayer());
        assertEquals(player1,game.getInactivePlayer());
    }
    @Test
    void illegalTeamCompositionTest(){
        assertThrows(IllegalTeamsCompositionException.class,()->new Game("Anna","Mario",Team.White,Team.White));
    }



    @Test
    void moveOnePieceTest() throws NoPieceOnWhiteException, OutOfBoundsException, CantEatException, IllegalMovementException, NotOnDiagonalException {
        Piece piece=board.getPiece(2,2);
        Move move=new Move(player1,piece,NeighborPosition.TopRight);
        game.playTurn(move);
        assertEquals(piece, BlackTile.asBlackTile(board.getTile(3,3)).getPiece());
        assertNull(board.getPiece(2,2));
        assertEquals(game.getRoundWithoutEating(),1);
    }

    @Test
    void CantMoveOtherPlayersPiece() throws NoPieceOnWhiteException, OutOfBoundsException, NotOnDiagonalException {
        Piece piece=board.getPiece(5,1);
        game.playTurn(new Move(player1,piece,NeighborPosition.BottomLeft));
        assertEquals(piece, BlackTile.asBlackTile(board.getTile(5,1)).getPiece());
        assertNull(board.getPiece(4,0));
    }

    @Test
    void moveTwoPiecesTest() throws NoPieceOnWhiteException, CantEatException, IllegalMovementException, OutOfBoundsException, NotOnDiagonalException {
        Piece piece1=board.getPiece(2,2);
        game.playTurn(new Move(player1,piece1,NeighborPosition.TopRight));

        Piece piece2=board.getPiece(5,1);
        game.playTurn(new Move(player2,piece2,NeighborPosition.BottomLeft));

        assertEquals(piece1, BlackTile.asBlackTile(board.getTile(3,3)).getPiece());
        assertNull(board.getPiece(2,2));
        assertEquals(piece2,BlackTile.asBlackTile(board.getTile(4,0)).getPiece());
        assertNull(board.getPiece(5,1));
        assertEquals(game.getRoundWithoutEating(),2);
    }

    @Test
    void illegalMoveTest() throws NoPieceOnWhiteException, IllegalMovementException, OutOfBoundsException, CantEatException, NotOnDiagonalException {
        Piece piece=board.getPiece(7,5);
        game.playTurn(new Move(player1,piece,NeighborPosition.BottomLeft));
        assertEquals(piece,BlackTile.asBlackTile(board.getTile(7,5)).getPiece());
    }
    @Test
    void whiteOutOfBoundsTest() throws NoPieceOnWhiteException, OutOfBoundsException, CantEatException, IllegalMovementException, NotOnDiagonalException {
        Piece piece=board.getPiece(1,7);
        game.playTurn(new Move(player1,piece,NeighborPosition.TopRight));
        assertEquals(piece,BlackTile.asBlackTile(board.getTile(1,7)).getPiece());
    }

    @Test
    void whiteEatsBlackTest() throws NoPieceOnWhiteException, CantEatException, OutOfBoundsException, IllegalMovementException, NotOnDiagonalException {
        Piece whitePiece=board.getPiece(2,2);
        Piece blackPiece=board.getPiece(5,5);

        game.playTurn(new Move(player1,whitePiece,NeighborPosition.TopRight));
        game.playTurn(new Move(player2,blackPiece,NeighborPosition.BottomLeft));
        game.playTurn(new Move(player1,whitePiece,NeighborPosition.TopRight));

        assertEquals(board.getPiece(5,5),whitePiece);
        assertNull(board.getPiece(4,4));
        assertEquals(player2.getNumberOfPieces(),11);
        assertEquals(game.getRoundWithoutEating(),0);
    }

    @Test
    void cantEatSameTeamTest() throws NoPieceOnWhiteException, OutOfBoundsException {
        Piece whitePiece=board.getPiece(1,1);
        Piece whitePiece2=board.getPiece(2,2);
        game.playTurn(new Move(player1,whitePiece,NeighborPosition.TopRight));

        assertEquals(board.getPiece(1,1),whitePiece);
        assertEquals(board.getPiece(2,2),whitePiece2);
        assertEquals(game.getActivePlayer(),player1);
    }

    @Test
    void cantEatTileAfterOccupiedTest() throws NoPieceOnWhiteException, OutOfBoundsException{
        Piece whitePiece=board.getPiece(2,6);
        Piece blackPiece=board.getPiece(5,7);

        game.playTurn(new Move(player1,whitePiece,NeighborPosition.TopRight));
        game.playTurn(new Move(player2,blackPiece,NeighborPosition.BottomLeft));
        game.playTurn(new Move(player1,whitePiece,NeighborPosition.TopLeft));

        assertEquals(board.getPiece(3,7),whitePiece);
        assertEquals(board.getPiece(4,6),blackPiece);
        assertEquals(game.getActivePlayer(),player1);
    }

    @Test
    void youHaveToEatTest() throws NoPieceOnWhiteException, OutOfBoundsException {
        Piece whitePiece1=board.getPiece(2,4);
        Piece whitePiece2=board.getPiece(2,2);
        Piece blackPiece=board.getPiece(5,7);

        game.playTurn(new Move(player1,whitePiece1,NeighborPosition.TopRight));
        game.playTurn(new Move(player2,blackPiece,NeighborPosition.BottomLeft));
        game.playTurn(new Move(player1,whitePiece2,NeighborPosition.TopRight));

        assertEquals(board.getPiece(3,5),whitePiece1);
        assertEquals(board.getPiece(4,6),blackPiece);
        assertEquals(board.getPiece(2,2),whitePiece2);
        assertEquals(game.getActivePlayer(),player1);

    }







}
