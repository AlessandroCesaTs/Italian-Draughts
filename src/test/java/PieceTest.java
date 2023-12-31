import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PieceTest {
    @Test
    void getBlackNeighborTest() throws IllegalTilePlacementException, NoPieceOnWhiteException {
        Board board=new Board();
        Tile tile=board.getTile(0,2);
        assertEquals(Team.Black,tile.getNeighbor(Direction.Left,1).getPiece().getTeam());
        assertEquals(Team.Black,tile.getNeighbor(Direction.Right,1).getPiece().getTeam());
    }
    @Test
    void getNullNeighborTest() throws IllegalTilePlacementException, NoPieceOnWhiteException {
        Board board=new Board();
        Tile tile=board.getTile(2,2);
        assertNull(tile.getNeighbor(Direction.Left, 1).getPiece());
        assertNull(tile.getNeighbor(Direction.Right, 1).getPiece());
    }
    @Test
    void getWhiteNeighborTest() throws IllegalTilePlacementException, NoPieceOnWhiteException {
        Board board=new Board();
        Tile tile=board.getTile(5,3);
        assertEquals(Team.White,tile.getNeighbor(Direction.Left,1).getPiece().getTeam());
        assertEquals(Team.White,tile.getNeighbor(Direction.Right,1).getPiece().getTeam());
    }


}
