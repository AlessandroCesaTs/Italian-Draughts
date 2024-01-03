import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BlackTileTest {
    Board board=new Board();
    BlackTile tile=BlackTile.asBlackTile(board.getTile(1,1));
    BlackTile bottomLeftTile=BlackTile.asBlackTile(board.getTile(0,0));
    BlackTile bottomRightTile=BlackTile.asBlackTile(board.getTile(0,2));
    BlackTile topLeftTile=BlackTile.asBlackTile(board.getTile(2,0));
    BlackTile topRightTile=BlackTile.asBlackTile(board.getTile(2,2));
    BlackTile otherTile=BlackTile.asBlackTile(board.getTile(1,3));

    public BlackTileTest() throws IllegalTilePlacementException {
    }

    @Test
    void equalsTest() throws IllegalTilePlacementException {
        BlackTile tile2=BlackTile.createBlackTile(1,1);
        assertTrue(tile.equals(tile2));
        assertTrue(tile2.equals(tile));
        assertFalse(tile.equals(otherTile));
    }

    @Test
    void equalsTest2() throws IllegalTilePlacementException {
        BlackTile tile1=BlackTile.createBlackTile(2,2);
        BlackTile tile2=BlackTile.createBlackTile(2,2);
        BlackTile tile3=BlackTile.createBlackTile(5,3);
        assertTrue(tile1.equals(tile2));
        assertTrue(tile2.equals(tile1));
        assertFalse(tile1.equals(tile3));
    }

    @Test
    void neighborBottomLeftTest(){
        assertTrue(tile.getNeighbor(NeighborPosition.BottomLeft).equals(bottomLeftTile));
    }
    @Test
    void neighborBottomRightTest(){

        assertTrue(tile.getNeighbor(NeighborPosition.BottomRight).equals(bottomRightTile));
    }
    @Test
    void neighborTopLeftTest(){

        assertTrue(tile.getNeighbor(NeighborPosition.TopLeft).equals(topLeftTile));
    }
    @Test
    void neighborTopRightTest(){
        assertTrue(tile.getNeighbor(NeighborPosition.TopRight).equals(topRightTile));
    }

    @Test
    void directionBottomLeft() throws Exception {
        assertEquals(NeighborPosition.BottomLeft,tile.otherTileDirection(bottomLeftTile));
    }
    @Test
    void directionBottomRight() throws Exception {
        assertEquals(NeighborPosition.BottomRight,tile.otherTileDirection(bottomRightTile));
    }
    @Test
    void directionTopLeft() throws Exception {
        assertEquals(NeighborPosition.TopLeft,tile.otherTileDirection(topLeftTile));
    }
    @Test
    void directionTopRight() throws Exception {
        assertEquals(NeighborPosition.TopRight,tile.otherTileDirection(topRightTile));
    }
    @Test
    void directionException() throws Exception {
        assertThrows(NotOnDiagonalException.class,()->{tile.otherTileDirection(otherTile);});
    }

}
