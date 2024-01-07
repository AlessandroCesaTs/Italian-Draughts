import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class KingTest {

    @Test
    void whiteBecomesKing() throws IllegalTilePlacementException, NoPieceOnWhiteException, AlreadyOccupiedException, SimplePieceCantGoBackException, CantEatException {
        Board board=new Board();
        Piece whitePiece=board.getPiece(2,2);
        Piece blackPiece1=board.getPiece(5,5);
        Piece blackPiece2=board.getPiece(5,7);
        Piece blackPiece3=board.getPiece(6,6);
        Piece blackPiece4=board.getPiece(7,7);

        assertFalse(whitePiece.getIfKing());

        whitePiece.movePiece(NeighborPosition.TopRight);
        blackPiece1.movePiece(NeighborPosition.BottomLeft);
        whitePiece.eatPiece(NeighborPosition.TopRight);
        blackPiece2.movePiece(NeighborPosition.BottomLeft);
        blackPiece3.movePiece(NeighborPosition.BottomRight);
        blackPiece4.movePiece(NeighborPosition.BottomLeft);
        whitePiece.eatPiece(NeighborPosition.TopRight);

        whitePiece.movePiece(NeighborPosition.BottomLeft);

        assertEquals(board.getPiece(6,6),whitePiece);
        assertTrue(whitePiece.getIfKing());
    }
    @Test
    void cantEatKing() throws IllegalTilePlacementException, NoPieceOnWhiteException, AlreadyOccupiedException, SimplePieceCantGoBackException, CantEatException {
        Board board=new Board();
        Piece whitePiece=board.getPiece(2,2);
        Piece blackPiece1=board.getPiece(5,5);
        Piece blackPiece2=board.getPiece(5,7);
        Piece blackPiece3=board.getPiece(6,6);
        Piece blackPiece4=board.getPiece(7,7);
        Piece blackPiece5=board.getPiece(7,5);

        assertFalse(whitePiece.getIfKing());

        whitePiece.movePiece(NeighborPosition.TopRight);
        blackPiece1.movePiece(NeighborPosition.BottomLeft);
        whitePiece.eatPiece(NeighborPosition.TopRight);
        blackPiece2.movePiece(NeighborPosition.BottomLeft);
        blackPiece3.movePiece(NeighborPosition.BottomRight);
        blackPiece4.movePiece(NeighborPosition.BottomLeft);
        whitePiece.eatPiece(NeighborPosition.TopRight);

        whitePiece.movePiece(NeighborPosition.BottomLeft);
        whitePiece.movePiece(NeighborPosition.BottomLeft);
        blackPiece5.movePiece(NeighborPosition.BottomRight);

        assertThrows(CantEatException.class,()->blackPiece5.eatPiece(NeighborPosition.BottomRight));
    }
}
