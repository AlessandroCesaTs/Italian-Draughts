package Exceptions;

public class IllegalTilePlacementException extends Exception {
    public IllegalTilePlacementException(){
        super("Illegal Tile placement");
    }
    public IllegalTilePlacementException(String message){
        super(message);
    }

}
