public class SimplePieceCantGoBackException extends Exception{
    public SimplePieceCantGoBackException(){
        super("Simple Piece Cant Go Back");
    }
    public SimplePieceCantGoBackException(String message){
        super(message);
    }
}
