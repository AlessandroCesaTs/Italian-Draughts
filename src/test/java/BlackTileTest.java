import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BlackTileTest {
    Board board=new Board();
    BlackTile tile=BlackTile.asBlackTile(board.getTile(1,1));

    public BlackTileTest() throws IllegalTilePlacementException {
    }

    @Test
    void equalsTest() throws IllegalTilePlacementException {
        BlackTile tile1=BlackTile.createBlackTile(2,2);
        BlackTile tile2=BlackTile.createBlackTile(2,2);
        BlackTile tile3=BlackTile.createBlackTile(5,3);
        assertTrue(tile1.equals(tile2));
        assertTrue(tile2.equals(tile1));
        assertFalse(tile1.equals(tile3));
    }

    @Test
    void neighborBottomLeftTest(){
        BlackTile bottomLeftTile=BlackTile.asBlackTile(board.getTile(0,0));
        assertTrue(tile.getNeighbor(NeighborPosition.BottomLeft).equals(bottomLeftTile));
    }
    @Test
    void neighborBottomRightTest(){
        BlackTile bottomRightTile=BlackTile.asBlackTile(board.getTile(0,2));
        assertTrue(tile.getNeighbor(NeighborPosition.BottomRight).equals(bottomRightTile));
    }
    @Test
    void neighborTopLeftTest(){
        BlackTile topLeftTile=BlackTile.asBlackTile(board.getTile(2,0));
        assertTrue(tile.getNeighbor(NeighborPosition.TopLeft).equals(topLeftTile));
    }
    @Test
    void neighborTopRightTest(){
        BlackTile topRightTile=BlackTile.asBlackTile(board.getTile(2,2));
        assertTrue(tile.getNeighbor(NeighborPosition.TopRight).equals(topRightTile));
    }
}
