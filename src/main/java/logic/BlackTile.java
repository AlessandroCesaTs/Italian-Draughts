package logic;

import java.util.HashMap;
import java.util.Map;

public class BlackTile extends Tile {
    private Piece piece=null;
    private final Map<NeighborPosition, BlackTile> neighbors = new HashMap<>();
    private BlackTile(int row, int col) {
        super(row,col);
    }
    public static BlackTile createBlackTile(int row,int col) {
        return new BlackTile(row,col);
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
    public void movePieceHere(Piece piece) {
        if (isFree()){
            this.piece=piece;
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
        checkAndAddNeighbor(NeighborPosition.BOTTOM_LEFT, row -1, col -1);
        checkAndAddNeighbor(NeighborPosition.BOTTOM_RIGHT, row -1, col +1);
        checkAndAddNeighbor(NeighborPosition.TOP_LEFT, row +1, col -1);
        checkAndAddNeighbor(NeighborPosition.TOP_RIGHT, row +1, col +1);
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
