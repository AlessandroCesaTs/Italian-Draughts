package Exceptions;

public class NoPieceOnWhiteException extends Exception {
    public NoPieceOnWhiteException(){
        super("No logic.Piece on White");
    }
    public NoPieceOnWhiteException(String message){
        super(message);
    }

}