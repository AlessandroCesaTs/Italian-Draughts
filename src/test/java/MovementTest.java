import logic.BlackTile;
import logic.Board;
import logic.NeighborPosition;
import logic.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MovementTest {
    final Board board=new Board();

    public MovementTest() {
    }
    @Test
    void movementTest() {
        Piece piece=board.getPiece(2,2);
        piece.movePieceByOne(NeighborPosition.TOP_RIGHT);
        assertEquals(piece, BlackTile.asBlackTile(board.getTile(3,3)).getPiece());
        assertNull(board.getPiece(2,2));
    }
    @Test
    void movementTest2() {
        Piece piece=board.getPiece(5,1);
        piece.movePieceByOne(NeighborPosition.BOTTOM_LEFT);
        assertEquals(piece,BlackTile.asBlackTile(board.getTile(4,0)).getPiece());
        assertNull(board.getPiece(5,1));
    }

    @Test
    void IllegalMoveTest() {
        Piece piece=board.getPiece(7,5);
        piece.movePieceByOne(NeighborPosition.BOTTOM_LEFT);
        assertEquals(piece,BlackTile.asBlackTile(board.getTile(7,5)).getPiece());
    }
    @Test
    void whiteOutOfBoundsTest() {
        Piece piece=board.getPiece(1,7);
        piece.movePieceByOne(NeighborPosition.TOP_RIGHT);
        assertEquals(piece,BlackTile.asBlackTile(board.getTile(1,7)).getPiece());
    }
    @Test
    void blackOutOfBoundsTest() {
        Piece piece=board.getPiece(7,7);
        piece.movePieceByOne(NeighborPosition.BOTTOM_RIGHT);
        assertEquals(piece,BlackTile.asBlackTile(board.getTile(7,7)).getPiece());
    }
}
