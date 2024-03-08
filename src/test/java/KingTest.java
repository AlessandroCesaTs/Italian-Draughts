import logic.BlackTile;
import logic.Board;
import logic.NeighborPosition;
import logic.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class KingTest {

    @Test
    void whiteBecomesKing(){
        Board board=new Board();
        Piece whitePiece=board.getPiece(2,2);
        Piece blackPiece1=board.getPiece(5,5);
        Piece blackPiece2=board.getPiece(5,7);
        Piece blackPiece3=board.getPiece(6,6);
        Piece blackPiece4=board.getPiece(7,7);

        assertFalse(whitePiece.getIfKing());

        whitePiece.movePieceByOne(NeighborPosition.TOP_RIGHT);
        blackPiece1.movePieceByOne(NeighborPosition.BOTTOM_LEFT);
        whitePiece.eatPiece(NeighborPosition.TOP_RIGHT);
        blackPiece2.movePieceByOne(NeighborPosition.BOTTOM_LEFT);
        blackPiece3.movePieceByOne(NeighborPosition.BOTTOM_RIGHT);
        blackPiece4.movePieceByOne(NeighborPosition.BOTTOM_LEFT);
        whitePiece.eatPiece(NeighborPosition.TOP_RIGHT);

        whitePiece.movePieceByOne(NeighborPosition.BOTTOM_LEFT);

        assertEquals(board.getPiece(6,6),whitePiece);
        assertTrue(whitePiece.getIfKing());
    }
    @Test
    void cantEatKing() {
        Board board=new Board();
        Piece whitePiece=board.getPiece(2,2);
        Piece blackPiece1=board.getPiece(5,5);
        Piece blackPiece2=board.getPiece(5,7);
        Piece blackPiece3=board.getPiece(6,6);
        Piece blackPiece4=board.getPiece(7,7);
        Piece blackPiece5=board.getPiece(7,5);

        assertFalse(whitePiece.getIfKing());

        whitePiece.movePieceByOne(NeighborPosition.TOP_RIGHT);
        blackPiece1.movePieceByOne(NeighborPosition.BOTTOM_LEFT);
        whitePiece.eatPiece(NeighborPosition.TOP_RIGHT);
        blackPiece2.movePieceByOne(NeighborPosition.BOTTOM_LEFT);
        blackPiece3.movePieceByOne(NeighborPosition.BOTTOM_RIGHT);
        blackPiece4.movePieceByOne(NeighborPosition.BOTTOM_LEFT);
        whitePiece.eatPiece(NeighborPosition.TOP_RIGHT);

        whitePiece.movePieceByOne(NeighborPosition.BOTTOM_LEFT);
        whitePiece.movePieceByOne(NeighborPosition.BOTTOM_LEFT);
        blackPiece5.movePieceByOne(NeighborPosition.BOTTOM_RIGHT);

        blackPiece5.eatPiece(NeighborPosition.BOTTOM_RIGHT);
        assertEquals(whitePiece, BlackTile.asBlackTile(board.getTile(5,5)).getPiece());
        assertEquals(blackPiece5, BlackTile.asBlackTile(board.getTile(6,6)).getPiece());
    }
}
