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
    void changeActivePlayerTest(){
        game.changeActivePlayer();
        assertEquals("Mario",player2.getName());
        assertEquals("Anna",player1.getName());
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
    }

    @Test
    void illegalMove() throws NoPieceOnWhiteException, IllegalMovementException, OutOfBoundsException, CantEatException, NotOnDiagonalException {
        Piece piece=board.getPiece(7,5);
        game.playTurn(new Move(player1,piece,NeighborPosition.BottomLeft));
        assertEquals(piece,BlackTile.asBlackTile(board.getTile(7,5)).getPiece());
    }

}
