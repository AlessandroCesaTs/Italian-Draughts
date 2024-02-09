import Exceptions.*;
import logic.Game;
import logic.Player;
import logic.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest {

    Game game=new Game("Anna","Mario", Team.White,Team.Black);

    public GameTest() throws IllegalTilePlacementException, NoPieceOnWhiteException, IllegalTeamsCompositionException, CantEatException, IllegalMovementException, OutOfBoundsException, NotOnDiagonalException {
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
    void illegalTeamCompositionTest(){
        assertThrows(IllegalTeamsCompositionException.class,()->new Game("Anna","Mario",Team.White,Team.White));
    }
}
