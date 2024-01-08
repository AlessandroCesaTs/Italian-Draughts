import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class EatingTest {

    @Test
    void whiteEatsBlackTest() throws IllegalTilePlacementException, NoPieceOnWhiteException, AlreadyOccupiedException, SimplePieceCantGoBackException, CantEatException, OutOfBoundsException {
        Board board=new Board();
        Piece whitePiece=board.getPiece(2,2);
        Piece blackPiece=board.getPiece(5,5);
        whitePiece.movePiece(NeighborPosition.TopRight);
        blackPiece.movePiece(NeighborPosition.BottomLeft);
        whitePiece.eatPiece(NeighborPosition.TopRight);
        assertEquals(board.getPiece(5,5),whitePiece);
        assertNull(board.getPiece(4,4));
    }
    @Test
    void cantEatSameTeamTest() throws IllegalTilePlacementException, NoPieceOnWhiteException {
        Board board=new Board();
        Piece whitePiece=board.getPiece(1,1);
        assertThrows(CantEatException.class,()-> whitePiece.eatPiece(NeighborPosition.TopRight));
    }
    @Test
    void cantEatTileAfterOccupiedTest() throws IllegalTilePlacementException, NoPieceOnWhiteException, AlreadyOccupiedException, SimplePieceCantGoBackException, OutOfBoundsException {
        Board board=new Board();
        Piece whitePiece=board.getPiece(2,2);
        whitePiece.movePiece(NeighborPosition.TopRight);
        whitePiece.movePiece(NeighborPosition.TopRight);
        assertThrows(CantEatException.class,()-> whitePiece.eatPiece(NeighborPosition.TopRight));
    }
}
