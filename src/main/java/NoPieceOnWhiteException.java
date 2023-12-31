public class NoPieceOnWhiteException extends Exception {
    public NoPieceOnWhiteException(){
        super("No Piece on White");
    }
    public NoPieceOnWhiteException(String message){
        super(message);
    }

}