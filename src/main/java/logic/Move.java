package logic;

import Exceptions.CantEatException;
import Exceptions.IllegalMovementException;
import Exceptions.OutOfBoundsException;
import gui.GraphicPiece;

import static main.Main.gBoard;

public class Move {
    private final Player player;
    private final Piece piece;
    private final BlackTile destination;
    private final NeighborPosition neighborDestination;
    private boolean isMoveValid;

    public Move(Player player, Piece piece, BlackTile destination, NeighborPosition neighborDestination) throws IllegalMovementException, CantEatException, OutOfBoundsException {
        this.player = player;
        this.piece = piece;
        this.destination = destination;
        this.neighborDestination = neighborDestination;
        isMoveValid=makeMove();
    }

    public boolean makeMove() throws IllegalMovementException, CantEatException, OutOfBoundsException{
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
                Piece eatenPiece = destination.getNeighbor(neighborDestination).getPiece();
                GraphicPiece eatenGraphicPiece = gBoard.getGraphicPiece(eatenPiece);
                gBoard.removePiece(eatenGraphicPiece);
                gBoard.moveCurrentPieceByTwo();
                return piece.eatPiece(neighborDestination);
            }else{
                gBoard.moveCurrentPieceByOne();
                return piece.movePieceByOne(neighborDestination);
            }
        }
        return false;
    }
    public Piece getPiece() {
        return piece;
    }
    public boolean getIfMoveIsValid(){
        return isMoveValid;
    }
}
