public class NotOnDiagonalException extends Exception{
    public NotOnDiagonalException(){
        super("Tile is not on Diagonal");
    }
    public NotOnDiagonalException(String message){
        super(message);
    }
}
