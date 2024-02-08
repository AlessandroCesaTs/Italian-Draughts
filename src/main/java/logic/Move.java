package logic;

import Exceptions.CantEatException;
import Exceptions.IllegalMovementException;
import Exceptions.OutOfBoundsException;

public class Move {
    private final Player player;
    private final Piece piece;
    private final NeighborPosition neighborDestination;
    private TypeOfMove typeOfMove;

    public Move(Player player, Piece piece, NeighborPosition neighborDestination) throws IllegalMovementException, CantEatException, OutOfBoundsException {
        this.player = player;
        this.piece = piece;
        this.neighborDestination = neighborDestination;
        typeOfMove=computeTypeOfMove();
    }

    public TypeOfMove computeTypeOfMove() throws IllegalMovementException, CantEatException, OutOfBoundsException{
        /*
        if (player.getTeam() != piece.team){
            throw new IllegalMovementException();
        }
        if (destination.isFree()){
            piece.moveToTile(destination);
        }
        if (piece.canEat(neighborDestination)){
            piece.eatPiece(neighborDestination);
        }
        //if(!piece.getIfKing() & piece.promotion()){}

        if (player.getTeam() != piece.team){
            throw new IllegalMovementException();
        }else {
            try {
                piece.eatPiece(neighborDestination);
            } catch (CantEatException | IllegalMovementException | OutOfBoundsException e) {
                System.out.println("Illegal move: " + e.getMessage());
                try {
                    piece.movePieceByOne(neighborDestination);
                } catch (IllegalMovementException | OutOfBoundsException e2) {
                    System.out.println("Illegal move: " + e2.getMessage());
                }
            }
        }
         */
        if (player.getTeam() == piece.team){
            if (piece.canEat(neighborDestination)){

                return TypeOfMove.Eat;
            }else if (piece.canMovePieceByOne(neighborDestination)) {

                return TypeOfMove.Move;
            }
        }
        return TypeOfMove.NoMove;
    }


    public Piece getPiece() {
        return piece;
    }
    public Player getPlayer(){return player;}
    public NeighborPosition getDestination(){return neighborDestination;}
    public TypeOfMove getTypeOfMove(){
        return typeOfMove;
    }
}
