public class BlackTile extends Tile {
    private Piece piece;
    private BlackTile(int x, int y) {
        super(x,y);
    }
    private BlackTile[] neighbors;
    public static BlackTile createBlackTile(int x,int y) throws IllegalTilePlacementException {
        if((x+y)%2==0){
            return new BlackTile(x,y);
        }else{
            throw new IllegalTilePlacementException("Black tiles must be placed where (x+y)%2==0");
        }
    }

    public void setPiece(Piece piece){
        this.piece=piece;
        piece.setTile(this);
    }
    public Piece getPiece(){
        return piece;
    }


}
