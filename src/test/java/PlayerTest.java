import org.junit.jupiter.api.Test;

public class PlayerTest {
    Player player;
    {
        try {
            player = new Player("Dracula", Team.White);
        } catch (NoPieceOnWhiteException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void nameTest(){
        player.getName();
    }
}
