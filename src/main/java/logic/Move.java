package logic;

import Exceptions.CantEatException;
import Exceptions.IllegalMovementException;
import Exceptions.OutOfBoundsException;

public class Move {
    private final Player player;
    private final Piece piece;
    private final BlackTile destination;
    private final NeighborPosition neighborDestination;

    public Move(Player player, Piece piece, BlackTile finish, NeighborPosition neighborDestination) throws IllegalMovementException, CantEatException, OutOfBoundsException {
        this.player = player;
        this.piece = piece;
        this.destination = finish;
        this.neighborDestination = neighborDestination;
        makeMove();
    }

    private void makeMove() throws IllegalMovementException, CantEatException, OutOfBoundsException{
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
    }



}
