import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;


public class BoardTest {
    @Test
    void boardBlackTileTest() throws IllegalTilePlacementException {
        Board board=new Board();
        for (int row=0;row<board.getSize();row+=2){
            for (int col=0;col<board.getSize();col+=2){
                assertInstanceOf(BlackTile.class, board.getTile(row, col));
            }
        }
    }
    @Test
    void boardWhiteTileTest() throws IllegalTilePlacementException {
        Board board=new Board();
        for (int row=1;row<board.getSize();row+=2){
            for (int col=0;col<board.getSize();col+=2){
                assertInstanceOf(WhiteTile.class, board.getTile(row, col));
            }
        }
    }
}
