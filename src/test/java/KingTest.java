import Exceptions.*;
import logic.BlackTile;
import logic.Board;
import logic.NeighborPosition;
import logic.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class KingTest {

    @Test
    void whiteBecomesKing() throws IllegalTilePlacementException, NoPieceOnWhiteException{
        Board board=new Board();
        Piece whitePiece=board.getPiece(2,2);
        Piece blackPiece1=board.getPiece(5,5);
        Piece blackPiece2=board.getPiece(5,7);
        Piece blackPiece3=board.getPiece(6,6);
        Piece blackPiece4=board.getPiece(7,7);

        assertFalse(whitePiece.getIfKing());

        whitePiece.movePieceByOne(NeighborPosition.TopRight);
        blackPiece1.movePieceByOne(NeighborPosition.BottomLeft);
        whitePiece.eatPiece(NeighborPosition.TopRight);
        blackPiece2.movePieceByOne(NeighborPosition.BottomLeft);
        blackPiece3.movePieceByOne(NeighborPosition.BottomRight);
        blackPiece4.movePieceByOne(NeighborPosition.BottomLeft);
        whitePiece.eatPiece(NeighborPosition.TopRight);

        whitePiece.movePieceByOne(NeighborPosition.BottomLeft);

        assertEquals(board.getPiece(6,6),whitePiece);
        assertTrue(whitePiece.getIfKing());
    }
    @Test
    void cantEatKing() throws IllegalTilePlacementException, NoPieceOnWhiteException {
        Board board=new Board();
        Piece whitePiece=board.getPiece(2,2);
        Piece blackPiece1=board.getPiece(5,5);
        Piece blackPiece2=board.getPiece(5,7);
        Piece blackPiece3=board.getPiece(6,6);
        Piece blackPiece4=board.getPiece(7,7);
        Piece blackPiece5=board.getPiece(7,5);

        assertFalse(whitePiece.getIfKing());

        whitePiece.movePieceByOne(NeighborPosition.TopRight);
        blackPiece1.movePieceByOne(NeighborPosition.BottomLeft);
        whitePiece.eatPiece(NeighborPosition.TopRight);
        blackPiece2.movePieceByOne(NeighborPosition.BottomLeft);
        blackPiece3.movePieceByOne(NeighborPosition.BottomRight);
        blackPiece4.movePieceByOne(NeighborPosition.BottomLeft);
        whitePiece.eatPiece(NeighborPosition.TopRight);

        whitePiece.movePieceByOne(NeighborPosition.BottomLeft);
        whitePiece.movePieceByOne(NeighborPosition.BottomLeft);
        blackPiece5.movePieceByOne(NeighborPosition.BottomRight);

        blackPiece5.eatPiece(NeighborPosition.BottomRight);
        assertEquals(whitePiece, BlackTile.asBlackTile(board.getTile(5,5)).getPiece());
        assertEquals(blackPiece5, BlackTile.asBlackTile(board.getTile(6,6)).getPiece());
    }
}
