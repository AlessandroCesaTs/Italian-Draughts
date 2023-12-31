import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;


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
                assertEquals(Team.Black,board.getTile(row,col).getPiece().getTeam());
            }
        }
        for (int col=1;col<board.getSize();col+=2){
            assertEquals(Team.Black,board.getTile(1,col).getPiece().getTeam());
        }
    }
    @Test
    void whitePieceTest() throws NoPieceOnWhiteException {
        for (int row=5;row<board.getSize();row+=2){
            for (int col=1;col<board.getSize();col+=2){
                assertEquals(Team.White,board.getTile(row,col).getPiece().getTeam());
            }
        }
        for (int col=0;col<board.getSize();col+=2){
            assertEquals(Team.White,board.getTile(6,col).getPiece().getTeam());
        }
    }


}
