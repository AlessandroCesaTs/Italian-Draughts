package Exceptions;

public class NotOnDiagonalException extends Exception{
    public NotOnDiagonalException(){
        super("logic.Tile is not on Diagonal");
    }
    public NotOnDiagonalException(String message){
        super(message);
    }
}
