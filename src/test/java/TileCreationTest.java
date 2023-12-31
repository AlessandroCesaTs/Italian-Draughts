import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TileCreationTest {

    @Test
    void validBlackTile() throws IllegalTilePlacementException {
        Tile blackTile= BlackTile.createBlackTile(0,0);
        assertEquals(0,blackTile.getX());
        assertEquals(0,blackTile.getY());
    }
    @Test
    void validBlackTile2() throws IllegalTilePlacementException {
        Tile blackTile= BlackTile.createBlackTile(5,3);
        assertEquals(5,blackTile.getX());
        assertEquals(3,blackTile.getY());
    }

    @Test
    void blackTileException()  {
       assertThrows(IllegalTilePlacementException.class,()->BlackTile.createBlackTile(0,1));
    }
    @Test
    void blackTileException2()  {
        assertThrows(IllegalTilePlacementException.class,()->BlackTile.createBlackTile(2,3));
    }

    @Test
    void createWhiteTile() throws IllegalTilePlacementException {
        Tile whiteTile= WhiteTile.createWhiteTile(1,0);
        assertEquals(1,whiteTile.getX());
        assertEquals(0,whiteTile.getY());
    }
    @Test
    void createWhiteTile2() throws IllegalTilePlacementException {
        Tile whiteTile= WhiteTile.createWhiteTile(7,2);
        assertEquals(7,whiteTile.getX());
        assertEquals(2,whiteTile.getY());
    }
    @Test
    void whiteTileException() {
        assertThrows(IllegalTilePlacementException.class,()->WhiteTile.createWhiteTile(0,0));
    }
    @Test
    void whiteTileException2() {
        assertThrows(IllegalTilePlacementException.class,()->WhiteTile.createWhiteTile(4,2));
    }
}
