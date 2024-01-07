public class CantEatException extends Exception{
    public CantEatException(){
        super("Can't Eat");
    }
    public CantEatException(String message){
        super(message);
    }
}
