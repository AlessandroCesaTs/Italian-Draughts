import Exceptions.IllegalTilePlacementException;
import Exceptions.NoPieceOnWhiteException;
import logic.BlackTile;
import logic.Board;
import logic.Team;
import logic.WhiteTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BoardTest {


    public BoardTest() throws IllegalTilePlacementException {
    }
    Board board=new Board();
    @Test
    void boardBlackTileTest(){
        for (int row=0;row<board.getSize();row+=2){
            for (int col=0;col<board.getSize();col+=2){
                assertInstanceOf(BlackTile.class, board.getTile(row, col));
            }
        }
        for (int row=1;row<board.getSize();row+=2){
            for (int col=1;col<board.getSize();col+=2){
                assertInstanceOf(BlackTile.class, board.getTile(row, col));
            }
        }
    }
    @Test
    void boardWhiteTileTest(){
        for (int row=1;row<board.getSize();row+=2){
            for (int col=0;col<board.getSize();col+=2){
                assertInstanceOf(WhiteTile.class, board.getTile(row, col));
            }
        }
        for (int row=0;row<board.getSize();row+=2){
            for (int col=1;col<board.getSize();col+=2){
                assertInstanceOf(WhiteTile.class, board.getTile(row, col));
            }
        }
    }
    @Test
    void blackPieceTest() throws NoPieceOnWhiteException {
        for (int row=0;row<4;row+=2){
            for (int col=0;col<board.getSize();col+=2){
                assertEquals(Team.White,board.getTile(row,col).getPiece().getTeam());
            }
        }
        for (int col=1;col<board.getSize();col+=2){
            assertEquals(Team.White,board.getTile(1,col).getPiece().getTeam());
        }
    }
    @Test
    void whitePieceTest() throws NoPieceOnWhiteException {
        for (int row=5;row<board.getSize();row+=2){
            for (int col=1;col<board.getSize();col+=2){
                assertEquals(Team.Black,board.getTile(row,col).getPiece().getTeam());
            }
        }
        for (int col=0;col<board.getSize();col+=2){
            assertEquals(Team.Black,board.getTile(6,col).getPiece().getTeam());
        }
    }

    @Test
    void validPositionTest(){
        for (int row=0;row<8;row++){
            for (int col=0;col<8;col++){
                assertTrue(board.validCoordinates(row,col));
            }
        }
    }
    @Test
    void nonValidPositionTest(){
        assertFalse(board.validCoordinates(-2,3));
        assertFalse(board.validCoordinates(1,9));
        assertFalse(board.validCoordinates(3,8));
        assertFalse(board.validCoordinates(8,8));
        assertFalse(board.validCoordinates(15,92));

    }
}
