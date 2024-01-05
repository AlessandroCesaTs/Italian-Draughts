import java.util.HashMap;
import java.util.Map;

public class BlackTile extends Tile {
    private Piece piece;
    private final Map<NeighborPosition, BlackTile> neighbors = new HashMap<>();
    private BlackTile(int x, int y) {
        super(x,y);
    }
    public static BlackTile createBlackTile(int x,int y) throws IllegalTilePlacementException {
        if((x+y)%2==0){
            return new BlackTile(x,y);
        }else{
            throw new IllegalTilePlacementException("Black tiles must be placed where (x+y)%2==0");
        }
    }
    public static BlackTile asBlackTile(Tile tile) {
        if (tile instanceof BlackTile) {
            return (BlackTile) tile;
        } else {
            throw new IllegalArgumentException("A white tile cannot be cast to BlackTile");
        }
    }

    public void setPiece(Piece piece){
        this.piece=piece;
        piece.setTile(this);
    }
    public Piece getPiece(){
        return piece;
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

    public BlackTile getNeighbor(NeighborPosition position){
        return neighbors.get(position);
    }

    /*
    public BlackTile getBlackTileInBetween(BlackTile otherBlackTile){

    }

     */

}
