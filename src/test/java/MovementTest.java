import Exceptions.IllegalMovementException;
import Exceptions.IllegalTilePlacementException;
import Exceptions.NoPieceOnWhiteException;
import Exceptions.OutOfBoundsException;
import logic.BlackTile;
import logic.Board;
import logic.NeighborPosition;
import logic.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MovementTest {
    Board board=new Board();

    public MovementTest() throws IllegalTilePlacementException {
    }
    @Test
    void movementTest() throws NoPieceOnWhiteException, OutOfBoundsException, IllegalMovementException {
        Piece piece=board.getPiece(2,2);
        piece.movePieceByOne(NeighborPosition.TopRight);
        assertEquals(piece, BlackTile.asBlackTile(board.getTile(3,3)).getPiece());
        assertNull(board.getPiece(2,2));
    }
    @Test
    void movementTest2() throws NoPieceOnWhiteException, OutOfBoundsException, IllegalMovementException {
        Piece piece=board.getPiece(5,1);
        piece.movePieceByOne(NeighborPosition.BottomLeft);
        assertEquals(piece,BlackTile.asBlackTile(board.getTile(4,0)).getPiece());
        assertNull(board.getPiece(5,1));
    }

    @Test
    void alreadyOccupiedTest() throws NoPieceOnWhiteException, IllegalMovementException, OutOfBoundsException {
        Piece piece=board.getPiece(7,5);
        piece.movePieceByOne(NeighborPosition.BottomLeft);
        assertEquals(piece,BlackTile.asBlackTile(board.getTile(7,5)).getPiece());
    }
    @Test
    void whiteOutOfBoundsTest() throws NoPieceOnWhiteException {
        Piece piece=board.getPiece(1,7);
        assertThrows(OutOfBoundsException.class,()->piece.movePieceByOne(NeighborPosition.TopRight));
    }
    @Test
    void blackOutOfBoundsTest() throws NoPieceOnWhiteException {
        Piece piece=board.getPiece(7,7);
        assertThrows(OutOfBoundsException.class,()->piece.movePieceByOne(NeighborPosition.BottomRight));
    }
}
