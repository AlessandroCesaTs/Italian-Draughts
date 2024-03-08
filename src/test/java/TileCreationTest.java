import logic.BlackTile;
import logic.Tile;
import logic.WhiteTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileCreationTest {

    @Test
    void validBlackTile(){
        Tile blackTile= BlackTile.createBlackTile(0,0);
        assertEquals(0,blackTile.getRow());
        assertEquals(0,blackTile.getCol());
    }
    @Test
    void validBlackTile2(){
        Tile blackTile= BlackTile.createBlackTile(5,3);
        assertEquals(5,blackTile.getRow());
        assertEquals(3,blackTile.getCol());
    }

    @Test
    void createWhiteTile(){
        Tile whiteTile= WhiteTile.createWhiteTile(1,0);
        assertEquals(1,whiteTile.getRow());
        assertEquals(0,whiteTile.getCol());
    }
    @Test
    void createWhiteTile2(){
        Tile whiteTile= WhiteTile.createWhiteTile(7,2);
        assertEquals(7,whiteTile.getRow());
        assertEquals(2,whiteTile.getCol());
    }

}
