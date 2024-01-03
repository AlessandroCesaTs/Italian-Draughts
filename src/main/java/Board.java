public class Board {
    private final int size=8;
    private final Tile[][] tiles = new Tile[size][size];

    public Board() throws IllegalTilePlacementException {
        for (int row=0;row<size;row++){
            for (int col=0;col<size;col++){
                createTile(row, col);
            }
        }
        populateNeighbors();

    }

    private void populateNeighbors() {
        for (int row=0;row<size;row++){
            for (int col=0;col<size;col++){
                if(tiles[row][col] instanceof BlackTile){
                    ((BlackTile) tiles[row][col]).setNeighbors();
                }
            }
        }
    }

    private void createTile(int row, int col) throws IllegalTilePlacementException {
        if((col + row)%2==0){
            tiles[row][col]=BlackTile.createBlackTile(row, col);
            placePiece(row, col);
        }else{
            tiles[row][col]=WhiteTile.createWhiteTile(row, col);
        }
        tiles[row][col].setBoard(this);
    }

    private void placePiece(int row, int col) {
        if (row <3){
            ((BlackTile) tiles[row][col]).setPiece(new Piece(Team.Black));
        }else if(row <8 && row >4){
            ((BlackTile) tiles[row][col]).setPiece(new Piece(Team.White));
        }
    }

    public Tile getTile(int row,int col){
        return tiles[row][col];
    }
    public Piece getPiece(int row,int col) throws NoPieceOnWhiteException {
        return getTile(row,col).getPiece();
    }
    public int getSize(){
        return size;
    }

    public boolean validCoordinates(int x,int y){
        return x>=0 && x<size && y>=0 && y<size;
    }

}
