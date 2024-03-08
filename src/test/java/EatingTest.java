import logic.Board;
import logic.NeighborPosition;
import logic.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class EatingTest {

    @Test
    void whiteEatsBlackTest(){
        Board board=new Board();
        Piece whitePiece=board.getPiece(2,2);
        Piece blackPiece=board.getPiece(5,5);
        whitePiece.movePieceByOne(NeighborPosition.TOP_RIGHT);
        blackPiece.movePieceByOne(NeighborPosition.BOTTOM_LEFT);
        whitePiece.eatPiece(NeighborPosition.TOP_RIGHT);
        assertEquals(board.getPiece(5,5),whitePiece);
        assertNull(board.getPiece(4,4));
    }
    @Test
    void cantEatSameTeamTest() {
        Board board=new Board();
        Piece whitePiece=board.getPiece(1,1);
        Piece whitePiece2=board.getPiece(2,2);
        whitePiece.eatPiece(NeighborPosition.TOP_RIGHT);
        assertEquals(board.getPiece(1,1),whitePiece);
        assertEquals(board.getPiece(2,2),whitePiece2);
    }
    @Test
    void cantEatTileAfterOccupiedTest() {
        Board board=new Board();
        Piece whitePiece=board.getPiece(2,2);
        Piece blackPiece=board.getPiece(5,5);
        whitePiece.movePieceByOne(NeighborPosition.TOP_RIGHT);
        whitePiece.movePieceByOne(NeighborPosition.TOP_RIGHT);
        whitePiece.eatPiece(NeighborPosition.TOP_RIGHT);
        assertEquals(board.getPiece(4,4),whitePiece);
        assertEquals(board.getPiece(5,5),blackPiece);
    }
}
