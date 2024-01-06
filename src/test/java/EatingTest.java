import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class EatingTest {

    @Test
    void whiteEatsBlackTest() throws IllegalTilePlacementException, NoPieceOnWhiteException, AlreadyOccupiedException, CantEatPieceOfSameTeamException {
        Board board=new Board();
        Piece whitePiece=board.getPiece(2,2);
        Piece blackPiece=board.getPiece(5,5);
        whitePiece.movePiece(NeighborPosition.TopRight);
        blackPiece.movePiece(NeighborPosition.BottomLeft);
        whitePiece.eatPiece(NeighborPosition.TopRight);
        assertEquals(board.getPiece(5,5),whitePiece);
        assertNull(board.getPiece(4,4));
    }

}
