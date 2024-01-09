import IlegalPieceMovementException.IllegalPieceMovement;

public class Move {
    private final Player player;
    private final Piece piece;
    private final BlackTile destination;
    private boolean hasPromoted;

    public Move(Player player, Piece piece, BlackTile finish) throws IllegalPieceMovement, AlreadyOccupiedException {
        this.player = player;
        this.piece = piece;
        this.destination = finish;
        makeMove();
    }

    private void makeMove() throws IllegalPieceMovement, AlreadyOccupiedException {
        if (player.team != piece.team){
            throw new IllegalPieceMovement();
        }
        if (destination.isFree()){
            piece.moveToTile(destination);
        }
        if (destination.getPiece().team != piece.team){
            //piece.eatPiece(destination); perchè serve enum NeighborPosition invece di BlackTile
        }
        /*if(!piece.hasPromoted() && destination.promotionRow()){
                 piece.promote();
          }
         */
    }



}
