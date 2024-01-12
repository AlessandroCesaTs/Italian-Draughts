package Exceptions;

public class IllegalMovementException extends Exception {
    public IllegalMovementException() { super("IllegalMovementException");}
    public IllegalMovementException(String message){
        super(message);
    }
}
