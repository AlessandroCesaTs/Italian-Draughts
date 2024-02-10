package logic;

import Exceptions.IllegalMovementException;
import Exceptions.IllegalTilePlacementException;
import Exceptions.OutOfBoundsException;

import java.util.HashMap;
import java.util.Map;

public class BlackTile extends Tile {
    private Piece piece=null;
    private final Map<NeighborPosition, BlackTile> neighbors = new HashMap<>();
    private BlackTile(int row, int col) {
        super(row,col);
    }
    public static BlackTile createBlackTile(int row,int col) throws IllegalTilePlacementException {
        if((row+col)%2==0){
            return new BlackTile(row,col);
        }else{
            throw new IllegalTilePlacementException("Black tiles must be placed where (row+col)%2==0");
        }
    }
    public static BlackTile asBlackTile(Tile tile) {
        if (tile instanceof BlackTile) {
            return (BlackTile) tile;
        } else {
            throw new IllegalArgumentException("A white tile cannot be cast to logic.BlackTile");
        }
    }

    public void setPiece(Piece piece){
        this.piece=piece;
        piece.setTile(this);
    }
    public void movePieceHere(Piece piece) throws IllegalMovementException {
        if (isFree()){
            this.piece=piece;
        }else{
            throw new IllegalMovementException("Already occupied");
        }
    }

    public void removePiece(){
        getPiece().remove();
        piece=null;
    }
    public Piece getPiece(){
        return piece;
    }
    public boolean isFree(){
        return getPiece() == null;
    }

    public void setNeighbors(){
        checkAndAddNeighbor(NeighborPosition.BottomLeft, row -1, col -1);
        checkAndAddNeighbor(NeighborPosition.BottomRight, row -1, col +1);
        checkAndAddNeighbor(NeighborPosition.TopLeft, row +1, col -1);
        checkAndAddNeighbor(NeighborPosition.TopRight, row +1, col +1);
    }

    private void checkAndAddNeighbor(NeighborPosition position,int row, int col) {
        if (getBoard().validCoordinates(row,col)) {
            neighbors.put(position, BlackTile.asBlackTile(getBoard().getTile(row,col)));
        }
    }

    public BlackTile getNeighbor(NeighborPosition position) {
        return neighbors.getOrDefault(position, null);
    }

}
