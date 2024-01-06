public class CantEatPieceOfSameTeamException extends Exception{
    public CantEatPieceOfSameTeamException(){
        super("CantEatPieceOfSameTeamException");
    }
    public CantEatPieceOfSameTeamException(String message){
        super(message);
    }
}
