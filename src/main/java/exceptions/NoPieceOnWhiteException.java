package exceptions;

public class NoPieceOnWhiteException extends Exception {
    public NoPieceOnWhiteException(){
        super("No logic.Piece on White");
    }
}