import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileTest {

    @Test
    void createBlackTile(){
        Tile whiteTile=new BlackTile(0,0);
        assertEquals(0,whiteTile.getX());
        assertEquals(0,whiteTile.getY());
    }

    @Test
    void createWhiteTile(){
        Tile whiteTile=new WhiteTile(1,0);
        assertEquals(1,whiteTile.getX());
        assertEquals(0,whiteTile.getY());
    }
}
