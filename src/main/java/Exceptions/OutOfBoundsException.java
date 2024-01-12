package Exceptions;

public class OutOfBoundsException extends Exception{
    public OutOfBoundsException(){
        super("Out of Bounds");
    }
    public OutOfBoundsException(String message){
        super(message);
    }
}
