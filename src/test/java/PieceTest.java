import Exceptions.OutOfBoundsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PieceTest {

    @Test
    void getBlackNeighborTest() throws Exceptions.IllegalTilePlacementException, OutOfBoundsException {
        Board board = new Board();
        Tile tile = board.getTile(0, 2);
        assertEquals(Team.White, BlackTile.asBlackTile(tile).getNeighbor(NeighborPosition.TopLeft).getPiece().getTeam());
        assertEquals(Team.White, BlackTile.asBlackTile(tile).getNeighbor(NeighborPosition.TopRight).getPiece().getTeam());
    }

    @Test
    void getNullNeighborTest() throws Exceptions.IllegalTilePlacementException, OutOfBoundsException {
        Board board = new Board();
        Tile tile = board.getTile(2, 2);
        assertNull(BlackTile.asBlackTile(tile).getNeighbor(NeighborPosition.TopLeft).getPiece());
        assertNull(BlackTile.asBlackTile(tile).getNeighbor(NeighborPosition.TopRight).getPiece());
    }

    @Test
    void getWhiteNeighborTest() throws Exceptions.IllegalTilePlacementException, OutOfBoundsException {
        Board board = new Board();
        Tile tile = board.getTile(5, 3);
        assertEquals(Team.Black, BlackTile.asBlackTile(tile).getNeighbor(NeighborPosition.TopLeft).getPiece().getTeam());
        assertEquals(Team.Black, BlackTile.asBlackTile(tile).getNeighbor(NeighborPosition.TopRight).getPiece().getTeam());
    }

}

