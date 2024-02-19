import Exceptions.*;
import logic.Game;
import logic.GameInterface;
import logic.Player;
import logic.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest {

    GameInterface gameInterface =new Game("Anna","Mario", Team.White,Team.Black);

    public GameTest() throws IllegalTilePlacementException, NoPieceOnWhiteException, IllegalTeamsCompositionException, CantEatException, IllegalMovementException, OutOfBoundsException, NotOnDiagonalException {
    }
    Player player1= gameInterface.getActivePlayer();
    Player player2= gameInterface.getActivePlayer();

    @Test
    void changeActivePlayerTest(){
        gameInterface.changeActivePlayer();
        Player active= gameInterface.getActivePlayer();
        Player inactive= gameInterface.getInactivePlayer();
        assertEquals("Mario",active.getName());
        assertEquals("Anna",inactive.getName());
    }
    @Test
    void illegalTeamCompositionTest(){
        assertThrows(IllegalTeamsCompositionException.class,()->new Game("Anna","Mario",Team.White,Team.White));
    }
}
