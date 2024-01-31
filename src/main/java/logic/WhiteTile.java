package logic;

import Exceptions.IllegalTilePlacementException;
import Exceptions.NoPieceOnWhiteException;

public class WhiteTile extends Tile {
    public WhiteTile(int x, int y) {
        super(x,y);
    }

    public static WhiteTile createWhiteTile(int x,int y) throws IllegalTilePlacementException {
        if((x+y)%2!=0){
            return new WhiteTile(x,y);
        }else{
            throw new IllegalTilePlacementException("White tiles must be placed where (x+y)%2!=0");
        }
    }
    public Piece getPiece() throws NoPieceOnWhiteException {
        throw new NoPieceOnWhiteException();
    }

}
