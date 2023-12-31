public class Board {
    private final int size=8;
    private final Tile[][] tiles = new Tile[size][size];

    public Board() throws IllegalTilePlacementException {
        for (int row=0;row<size;row++){
            for (int col=0;col<size;col++){
                createTile(col, row);
            }
        }
    }


    private void createTile(int col, int row) throws IllegalTilePlacementException {
        if((col + row)%2==0){
            tiles[col][row]=BlackTile.createBlackTile(col, row);
        }else{
            tiles[col][row]=WhiteTile.createWhiteTile(col, row);
        }
    }

    public Tile getTile(int col,int row){
        return tiles[col][row];
    }
    public int getSize(){
        return size;
    }
}
