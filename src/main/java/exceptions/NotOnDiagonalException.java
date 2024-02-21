package exceptions;

public class NotOnDiagonalException extends Exception{
    public NotOnDiagonalException(){
        super("logic.Tile is not on Diagonal");
    }
}
