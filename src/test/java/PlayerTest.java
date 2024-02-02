import Exceptions.IllegalTilePlacementException;
import Exceptions.NoPieceOnWhiteException;
import logic.Game;
import logic.Player;
import logic.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PlayerTest {

    Game game=new Game("Anna","Mario", Team.White,Team.Black);

    public PlayerTest() throws IllegalTilePlacementException, NoPieceOnWhiteException {
    }

    Player player1=game.getActivePlayer();
    Player player2=game.getInactivePlayer();

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
        Player testPlayer=game.getActivePlayer();
        assertTrue(player1.equals(testPlayer));
    }
    @Test
    void playerEqualsTest2(){
        Player testPlayer=game.getInactivePlayer();
        assertTrue(player2.equals(testPlayer));
    }
}
