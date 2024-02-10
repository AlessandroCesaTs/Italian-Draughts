package logic;

import Exceptions.IllegalTilePlacementException;
import Exceptions.NoPieceOnWhiteException;

public class WhiteTile extends Tile {
    public WhiteTile(int row, int col) {
        super(row,col);
    }

    public static WhiteTile createWhiteTile(int row,int col) throws IllegalTilePlacementException {
        if((row+col)%2!=0){
            return new WhiteTile(row,col);
        }else{
            throw new IllegalTilePlacementException("White tiles must be placed where (row+col)%2!=0");
        }
    }
    public Piece getPiece() throws NoPieceOnWhiteException {
        throw new NoPieceOnWhiteException();
    }

}
