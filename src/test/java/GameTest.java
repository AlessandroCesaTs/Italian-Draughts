import gui.FakeGraphicBoard;
import gui.GraphicBoard;
import logic.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("MagicNumber")
public class GameTest {

    final Game game =new Game("Anna","Mario", Team.WHITE,Team.BLACK);
    final Board board=game.getBoard();
    final Player player1=game.getActivePlayer();
    final Player player2=game.getInactivePlayer();

    public GameTest() {
    }

    @BeforeEach
    void setUp() {
        game.setGBoard(new FakeGraphicBoard());
    }

    @Test
    void changeActivePlayerTest() {
        Piece piece=board.getPiece(2,2);
        game.playTurn(new Move(player1,piece,NeighborPosition.TOP_RIGHT));
        assertEquals(player2,game.getActivePlayer());
        assertEquals(player1,game.getInactivePlayer());
    }


    @Test
    void moveOnePieceTest() {
        Piece piece=board.getPiece(2,2);
        Move move=new Move(player1,piece,NeighborPosition.TOP_RIGHT);
        game.playTurn(move);
        assertEquals(piece, board.getPiece(3,3));
        assertNull(board.getPiece(2,2));
        assertEquals(game.getRoundsWithoutEating(),1);
    }

    @Test
    void CantMoveOtherPlayersPiece() {
        Piece piece=board.getPiece(5,1);
        game.playTurn(new Move(player1,piece,NeighborPosition.BOTTOM_LEFT));
        assertEquals(piece, board.getPiece(5,1));
        assertNull(board.getPiece(4,0));
    }

    @Test
    void moveTwoPiecesTest() {
        Piece piece1=board.getPiece(2,2);
        game.playTurn(new Move(player1,piece1,NeighborPosition.TOP_RIGHT));

        Piece piece2=board.getPiece(5,1);
        game.playTurn(new Move(player2,piece2,NeighborPosition.BOTTOM_LEFT));

        assertEquals(piece1, board.getPiece(3,3));
        assertNull(board.getPiece(2,2));
        assertEquals(piece2,board.getPiece(4,0));
        assertNull(board.getPiece(5,1));
        assertEquals(game.getRoundsWithoutEating(),2);
    }

    @Test
    void illegalMoveTest() {
        Piece piece=board.getPiece(7,5);
        game.playTurn(new Move(player1,piece,NeighborPosition.BOTTOM_LEFT));
        assertEquals(piece,board.getPiece(7,5));
    }
    @Test
    void whiteOutOfBoundsTest() {
        Piece piece=board.getPiece(1,7);
        game.playTurn(new Move(player1,piece,NeighborPosition.TOP_RIGHT));
        assertEquals(piece,board.getPiece(1,7));
    }

    @Test
    void whiteEatsBlackTest() {
        Piece whitePiece=board.getPiece(2,2);
        Piece blackPiece=board.getPiece(5,5);

        game.playTurn(new Move(player1,whitePiece,NeighborPosition.TOP_RIGHT));
        game.playTurn(new Move(player2,blackPiece,NeighborPosition.BOTTOM_LEFT));
        game.playTurn(new Move(player1,whitePiece,NeighborPosition.TOP_RIGHT));

        assertEquals(board.getPiece(5,5),whitePiece);
        assertNull(board.getPiece(4,4));
        assertEquals(player2.getNumberOfPieces(),11);
        assertEquals(game.getRoundsWithoutEating(),0);
    }

    @Test
    void cantEatSameTeamTest() {
        Piece whitePiece=board.getPiece(1,1);
        Piece whitePiece2=board.getPiece(2,2);
        game.playTurn(new Move(player1,whitePiece,NeighborPosition.TOP_RIGHT));

        assertEquals(board.getPiece(1,1),whitePiece);
        assertEquals(board.getPiece(2,2),whitePiece2);
        assertEquals(game.getActivePlayer(),player1);
    }

    @Test
    void cantEatTileAfterOccupiedTest() {
        Piece whitePiece=board.getPiece(2,6);
        Piece blackPiece=board.getPiece(5,7);

        game.playTurn(new Move(player1,whitePiece,NeighborPosition.TOP_RIGHT));
        game.playTurn(new Move(player2,blackPiece,NeighborPosition.BOTTOM_LEFT));
        game.playTurn(new Move(player1,whitePiece,NeighborPosition.TOP_LEFT));

        assertEquals(board.getPiece(3,7),whitePiece);
        assertEquals(board.getPiece(4,6),blackPiece);
        assertEquals(game.getActivePlayer(),player1);
    }

    @Test
    void youHaveToEatTest(){
        Piece whitePiece1=board.getPiece(2,4);
        Piece whitePiece2=board.getPiece(2,2);
        Piece blackPiece=board.getPiece(5,7);

        game.playTurn(new Move(player1,whitePiece1,NeighborPosition.TOP_RIGHT));
        game.playTurn(new Move(player2,blackPiece,NeighborPosition.BOTTOM_LEFT));
        game.playTurn(new Move(player1,whitePiece2,NeighborPosition.TOP_RIGHT));

        assertEquals(board.getPiece(3,5),whitePiece1);
        assertEquals(board.getPiece(4,6),blackPiece);
        assertEquals(board.getPiece(2,2),whitePiece2);
        assertEquals(game.getActivePlayer(),player1);
    }

    @Test
    void doubleEatTest() {
        Piece whitePiece1=board.getPiece(2,4);
        Piece blackPiece1=board.getPiece(5,1);
        Piece whitePiece2=board.getPiece(2,6);
        Piece blackPiece2=board.getPiece(6,0);
        Piece whitePiece3=board.getPiece(1,5);
        Piece whitePiece4=board.getPiece(2,0);


        game.playTurn(new Move(player1,whitePiece1,NeighborPosition.TOP_RIGHT));
        game.playTurn(new Move(player2,blackPiece1,NeighborPosition.BOTTOM_RIGHT));
        game.playTurn(new Move(player1,whitePiece2,NeighborPosition.TOP_RIGHT));
        game.playTurn(new Move(player2,blackPiece2,NeighborPosition.BOTTOM_RIGHT));
        game.playTurn(new Move(player1,whitePiece3,NeighborPosition.TOP_RIGHT));
        game.playTurn(new Move(player2,blackPiece1,NeighborPosition.BOTTOM_LEFT));
        game.playTurn(new Move(player1,whitePiece4,NeighborPosition.TOP_RIGHT));

        assertEquals(game.getActivePlayer(),player1);

        game.playTurn(new Move(player1,whitePiece4,NeighborPosition.TOP_LEFT));

        assertEquals(whitePiece4,board.getPiece(6,0));
        assertNull(board.getPiece(5,1));
        assertNull(board.getPiece(3,1));
        assertEquals(player2.getNumberOfPieces(),10);
    }
}
