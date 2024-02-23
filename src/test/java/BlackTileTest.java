import exceptions.IllegalTilePlacementException;
import logic.BlackTile;
import logic.Board;
import logic.NeighborPosition;
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
    void distanceNeighbors(){
        assertEquals(1,tile.calculateDistance(bottomLeftTile));
        assertEquals(1,tile.calculateDistance(bottomRightTile));
        assertEquals(1,tile.calculateDistance(topLeftTile));
        assertEquals(1,tile.calculateDistance(topRightTile));
    }
    @Test
    void distance2(){
        BlackTile otherTile2= BlackTile.asBlackTile(board.getTile(4,4));
        assertEquals(3,otherTile.calculateDistance(otherTile2));
    }

    @Test
    void isNotFree() {
        assertFalse(BlackTile.asBlackTile(board.getTile(1,1)).isFree());
        assertFalse(BlackTile.asBlackTile(board.getTile(6,4)).isFree());
    }
    @Test
    void isFree(){
        assertTrue(BlackTile.asBlackTile(board.getTile(3,1)).isFree());
        assertTrue(BlackTile.asBlackTile(board.getTile(4,4)).isFree());
    }

    @Test
    void removePiece(){
        assertNotNull(tile.getPiece());
        assertFalse(tile.isFree());
        tile.removePiece();

        assertNull(tile.getPiece());
        assertTrue(tile.isFree());
    }

}

