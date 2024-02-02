import Exceptions.IllegalTilePlacementException;
import Exceptions.NoPieceOnWhiteException;
import logic.Game;
import logic.Player;
import logic.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    Game game=new Game("Anna","Mario", Team.White,Team.Black);

    public GameTest() throws IllegalTilePlacementException, NoPieceOnWhiteException {
    }
    Player player1=game.getActivePlayer();
    Player player2=game.getActivePlayer();


    @Test
    void changeActivePlayerTest(){
        game.changeActivePlayer();
        Player active=game.getActivePlayer();
        Player inactive=game.getInactivePlayer();
        assertEquals("Mario",active.getName());
        assertEquals("Anna",inactive.getName());
    }
    @Test
    void losePieceTest(){
        game.changeActivePlayer();
        Player active=game.getActivePlayer();
        Player inactive=game.getInactivePlayer();
        assertEquals("Mario",active.getName());
        assertEquals("Anna",inactive.getName());
    }


}
