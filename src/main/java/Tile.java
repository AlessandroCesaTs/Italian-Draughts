public abstract class Tile {
    final int row;
    final int col;
    private Board board;

    protected Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow(){
        return row;
    }
     public int getCol(){
         return col;
     }

     public void setBoard(Board boardToSet){
        board=boardToSet;
     }
     public Board getBoard(){
        return board;
     }
     public abstract Piece getPiece() throws NoPieceOnWhiteException;

    public boolean equals(Tile tile) {
        return tile.getRow()== row && tile.getCol()== col;
    }

    //public int getDiagonalDistance(Tile tile){}

    public boolean isOnDiagonal(int rowDiff,int colDiff){
        return Math.abs(rowDiff)==Math.abs(colDiff) && rowDiff!=0;
    }
    public NeighborPosition otherTileDirection(Tile tile) throws NotOnDiagonalException {
        int rowDiff=tile.getRow()-row;
        int colDiff=tile.getCol()-col;
        if (this.isOnDiagonal(rowDiff,colDiff)) {
            if (rowDiff > 0) {
                if (colDiff > 0) {
                    return NeighborPosition.TopRight;
                } else {
                    return NeighborPosition.TopLeft;
                }
            } else {
                if (colDiff > 0) {
                    return NeighborPosition.BottomRight;
                } else {
                    return NeighborPosition.BottomLeft;
                }
            }
        }else{
            throw new NotOnDiagonalException();
        }
    }
}
