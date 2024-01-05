public class AlreadyOccupiedException extends Exception{
    public AlreadyOccupiedException(){
        super("AlreadyOccupiedException");
    }
    public AlreadyOccupiedException(String message){
        super(message);
    }
}
