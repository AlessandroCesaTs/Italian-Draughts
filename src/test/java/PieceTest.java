import logic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PieceTest {

    @Test
    void getBlackNeighborTest() {
        Board board = new Board();
        Tile tile = board.getTile(0, 2);
        assertEquals(Team.WHITE, BlackTile.asBlackTile(tile).getNeighbor(NeighborPosition.TOP_LEFT).getPiece().getTeam());
        assertEquals(Team.WHITE, BlackTile.asBlackTile(tile).getNeighbor(NeighborPosition.TOP_RIGHT).getPiece().getTeam());
    }

    @Test
    void getNullNeighborTest() {
        Board board = new Board();
        Tile tile = board.getTile(2, 2);
        assertNull(BlackTile.asBlackTile(tile).getNeighbor(NeighborPosition.TOP_LEFT).getPiece());
        assertNull(BlackTile.asBlackTile(tile).getNeighbor(NeighborPosition.TOP_RIGHT).getPiece());
    }

    @Test
    void getWhiteNeighborTest() {
        Board board = new Board();
        Tile tile = board.getTile(5, 3);
        assertEquals(Team.BLACK, BlackTile.asBlackTile(tile).getNeighbor(NeighborPosition.TOP_LEFT).getPiece().getTeam());
        assertEquals(Team.BLACK, BlackTile.asBlackTile(tile).getNeighbor(NeighborPosition.TOP_RIGHT).getPiece().getTeam());
    }

}

