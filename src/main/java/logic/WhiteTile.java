package logic;

import exceptions.NoPieceOnWhiteException;

public class WhiteTile extends Tile {
    public WhiteTile(int row, int col) {
        super(row,col);
    }

    public static WhiteTile createWhiteTile(int row,int col){
        return new WhiteTile(row,col);
    }
    public Piece getPiece() throws NoPieceOnWhiteException {
        throw new NoPieceOnWhiteException();
    }

}
