package Exceptions;

public class IllegalTeamsCompositionException extends Exception {
    public IllegalTeamsCompositionException() { super("Can't have both players in the same team");}
}
