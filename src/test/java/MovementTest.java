import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MovementTest {
    Board board=new Board();

    public MovementTest() throws IllegalTilePlacementException{
    }
    @Test
    void movementTest() throws NoPieceOnWhiteException, AlreadyOccupiedException {
        Piece piece=board.getPiece(2,2);
        piece.movePiece(NeighborPosition.TopRight);
        assertEquals(piece,BlackTile.asBlackTile(board.getTile(3,3)).getPiece());
        assertNull(board.getPiece(2,2));
    }
    @Test
    void movementTest2() throws NoPieceOnWhiteException, AlreadyOccupiedException {
        Piece piece=board.getPiece(5,1);
        piece.movePiece(NeighborPosition.BottomLeft);
        assertEquals(piece,BlackTile.asBlackTile(board.getTile(4,0)).getPiece());
        assertNull(board.getPiece(5,1));
    }

    @Test
    void alreadyOccupiedTest() throws NoPieceOnWhiteException {
        Piece piece=board.getPiece(7,5);
        assertThrows(AlreadyOccupiedException.class,()->piece.movePiece(NeighborPosition.BottomLeft));
    }
}
