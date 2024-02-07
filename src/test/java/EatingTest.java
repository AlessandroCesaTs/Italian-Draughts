import Exceptions.*;
import logic.Board;
import logic.NeighborPosition;
import logic.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class EatingTest {

    @Test
    void whiteEatsBlackTest() throws IllegalTilePlacementException, NoPieceOnWhiteException, CantEatException, OutOfBoundsException, IllegalMovementException {
        Board board=new Board();
        Piece whitePiece=board.getPiece(2,2);
        Piece blackPiece=board.getPiece(5,5);
        whitePiece.movePieceByOne(NeighborPosition.TopRight);
        blackPiece.movePieceByOne(NeighborPosition.BottomLeft);
        whitePiece.eatPiece(NeighborPosition.TopRight);
        assertEquals(board.getPiece(5,5),whitePiece);
        assertNull(board.getPiece(4,4));
    }
    @Test
    void cantEatSameTeamTest() throws IllegalTilePlacementException, NoPieceOnWhiteException, CantEatException, IllegalMovementException, OutOfBoundsException {
        Board board=new Board();
        Piece whitePiece=board.getPiece(1,1);
        Piece whitePiece2=board.getPiece(2,2);
        whitePiece.eatPiece(NeighborPosition.TopRight);
        assertEquals(board.getPiece(1,1),whitePiece);
        assertEquals(board.getPiece(2,2),whitePiece2);
    }
    @Test
    void cantEatTileAfterOccupiedTest() throws IllegalTilePlacementException, NoPieceOnWhiteException, OutOfBoundsException, IllegalMovementException, CantEatException {
        Board board=new Board();
        Piece whitePiece=board.getPiece(2,2);
        Piece blackPiece=board.getPiece(5,5);
        whitePiece.movePieceByOne(NeighborPosition.TopRight);
        whitePiece.movePieceByOne(NeighborPosition.TopRight);
        whitePiece.eatPiece(NeighborPosition.TopRight);
        assertEquals(board.getPiece(4,4),whitePiece);
        assertEquals(board.getPiece(5,5),blackPiece);
    }
}
