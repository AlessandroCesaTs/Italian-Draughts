import IlegalPieceMovementException.IllegalPieceMovement;

public class Move {
    private final Player player;
    private final Piece piece;
    private final BlackTile destination;
    private final NeighborPosition neighborDestination;

    public Move(Player player, Piece piece, BlackTile finish, NeighborPosition neighborDestination) throws IllegalPieceMovement, AlreadyOccupiedException, CantEatException, CantEatPieceOfSameTeamException, OutOfBoundsException {
        this.player = player;
        this.piece = piece;
        this.destination = finish;
        this.neighborDestination = neighborDestination;
        makeMove();
    }

    private void makeMove() throws IllegalPieceMovement, AlreadyOccupiedException, CantEatPieceOfSameTeamException, CantEatException, OutOfBoundsException {
        if (player.team != piece.team){
            throw new IllegalPieceMovement();
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
