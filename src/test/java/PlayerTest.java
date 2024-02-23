import exceptions.*;
import logic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SuppressWarnings("MagicNumber")
public class PlayerTest {

    GameInterface gameInterface =new Game("Anna","Mario", Team.White,Team.Black);

    public PlayerTest() throws IllegalTilePlacementException, IllegalTeamsCompositionException {
    }

    Player player1= gameInterface.getActivePlayer();
    Player player2= gameInterface.getInactivePlayer();

    @Test
    void player1Test(){
        assertEquals("Anna",player1.getName());
        assertEquals(Team.White,player1.getTeam());
        assertEquals(12,player1.getNumberOfPieces());
    }
    @Test
    void player2Test(){
        assertEquals("Mario",player2.getName());
        assertEquals(Team.Black,player2.getTeam());
        assertEquals(12,player2.getNumberOfPieces());
    }

    @Test
    void playerEqualsTest1(){
        Player testPlayer= gameInterface.getActivePlayer();
        assertTrue(player1.equals(testPlayer));
    }
    @Test
    void playerEqualsTest2(){
        Player testPlayer= gameInterface.getInactivePlayer();
        assertTrue(player2.equals(testPlayer));
    }
    @Test
    void losePiece1Test() throws NoPieceOnWhiteException {
        Piece pieceToLose= gameInterface.getPiece(0,0);
        player1.loseOnePiece(pieceToLose);
        assertEquals(11,player1.getNumberOfPieces());
    }
    @Test
    void losePiece2Test() throws NoPieceOnWhiteException {
        Piece pieceToLose1= gameInterface.getPiece(5,1);
        Piece pieceToLose2= gameInterface.getPiece(5,3);
        player2.loseOnePiece(pieceToLose1);
        player2.loseOnePiece(pieceToLose2);
        assertEquals(10,player2.getNumberOfPieces());
    }
}
