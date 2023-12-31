public class BlackTile extends Tile {
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

}
