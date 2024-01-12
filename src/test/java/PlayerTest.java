import Exceptions.NoPieceOnWhiteException;

public class PlayerTest {
    Player player;
    {
        try {
            player = new Player("Dracula", Team.White);
        } catch (NoPieceOnWhiteException e) {
            throw new RuntimeException(e);
        }
    }

}
