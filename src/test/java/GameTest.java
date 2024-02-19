import Exceptions.*;
import logic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    MockGame game =new MockGame("Anna","Mario", Team.White,Team.Black);

    public GameTest() throws IllegalTilePlacementException, NoPieceOnWhiteException, IllegalTeamsCompositionException, CantEatException, IllegalMovementException, OutOfBoundsException, NotOnDiagonalException {
    }
    @Test
    void changeActivePlayerTest(){
        game.changeActivePlayer();
        Player active= game.getActivePlayer();
        Player inactive= game.getInactivePlayer();
        assertEquals("Mario",active.getName());
        assertEquals("Anna",inactive.getName());
    }
    @Test
    void illegalTeamCompositionTest(){
        assertThrows(IllegalTeamsCompositionException.class,()->new Game("Anna","Mario",Team.White,Team.White));
    }

    @Test
    void moveOnePieceTest() throws NoPieceOnWhiteException, OutOfBoundsException, CantEatException, IllegalMovementException, NotOnDiagonalException {
        Player player=game.getActivePlayer();
        Board board=game.getBoard();
        Piece piece=board.getPiece(2,2);
        Move move=new Move(player,piece,NeighborPosition.TopRight);
        game.playTurn(move);
        assertEquals(piece, BlackTile.asBlackTile(board.getTile(3,3)).getPiece());
        assertNull(board.getPiece(2,2));
    }

    @Test
    void moveTwoPiecesTest() throws NoPieceOnWhiteException, CantEatException, IllegalMovementException, OutOfBoundsException, NotOnDiagonalException {
        Board board=game.getBoard();

        Player player1=game.getActivePlayer();
        Piece piece1=board.getPiece(2,2);
        game.playTurn(new Move(player1,piece1,NeighborPosition.TopRight));

        Player player2=game.getActivePlayer();
        Piece piece2=board.getPiece(5,1);
        game.playTurn(new Move(player2,piece2,NeighborPosition.BottomLeft));

        assertEquals(piece1, BlackTile.asBlackTile(board.getTile(3,3)).getPiece());
        assertNull(board.getPiece(2,2));
        assertEquals(piece2,BlackTile.asBlackTile(board.getTile(4,0)).getPiece());
        assertNull(board.getPiece(5,1));

    }

}
