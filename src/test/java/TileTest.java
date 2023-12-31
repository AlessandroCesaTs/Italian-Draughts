import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileTest {

    @Test
    void validBlackTile() throws IllegalTilePlacementException {
        Tile blackTile= BlackTile.createBlackTile(0,0);
        assertEquals(0,blackTile.getX());
        assertEquals(0,blackTile.getY());
    }

    @Test
    void createWhiteTile() throws IllegalTilePlacementException {
        Tile whiteTile= WhiteTile.createWhiteTile(1,0);
        assertEquals(1,whiteTile.getX());
        assertEquals(0,whiteTile.getY());
    }
}
