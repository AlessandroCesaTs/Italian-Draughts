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
    void neighborBottomLeftTest() throws OutOfBoundsException {
        assertTrue(tile.getNeighbor(NeighborPosition.BottomLeft).equals(bottomLeftTile));
    }
    @Test
    void neighborBottomRightTest() throws OutOfBoundsException {

        assertTrue(tile.getNeighbor(NeighborPosition.BottomRight).equals(bottomRightTile));
    }
    @Test
    void neighborTopLeftTest() throws OutOfBoundsException {

        assertTrue(tile.getNeighbor(NeighborPosition.TopLeft).equals(topLeftTile));
    }
    @Test
    void neighborTopRightTest() throws OutOfBoundsException {
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
    void directionException() {
        assertThrows(NotOnDiagonalException.class,()-> tile.otherTileDirection(otherTile));
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
        BlackTile otherTile2=BlackTile.asBlackTile(board.getTile(4,4));
        assertEquals(3,otherTile.calculateDistance(otherTile2));
    }

    @Test
    void tileInBetween() throws NotOnDiagonalException, DistanceDifferentThan2Exception, OutOfBoundsException {
        BlackTile otherTile2=BlackTile.asBlackTile(board.getTile(3,3));
        assertEquals(topRightTile,tile.getBlackTileInBetween(otherTile2));
    }

    @Test
    void tileInBetween2() throws NotOnDiagonalException, DistanceDifferentThan2Exception, OutOfBoundsException {
        BlackTile firstTile = BlackTile.asBlackTile(board.getTile(4, 2));
        BlackTile secondTile = BlackTile.asBlackTile(board.getTile(6, 0));
        BlackTile inBetweenTile = BlackTile.asBlackTile(board.getTile(5, 1));
        assertEquals(inBetweenTile, firstTile.getBlackTileInBetween(secondTile));
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

