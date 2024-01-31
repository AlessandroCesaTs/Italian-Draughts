package logic;

import Exceptions.NoPieceOnWhiteException;
import Exceptions.NotOnDiagonalException;

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

    //public int getDiagonalDistance(logic.Tile tile){}

    public boolean isOnDiagonal(Tile otherTile){
        return Math.abs(rowDiff(otherTile))==Math.abs(colDiff(otherTile)) && rowDiff(otherTile)!=0;
    }
    public NeighborPosition otherTileDirection(Tile otherTile) throws NotOnDiagonalException {
        int rowDiff=rowDiff(otherTile);
        int colDiff=colDiff(otherTile);
        if (this.isOnDiagonal(otherTile)) {
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

    public int calculateDistance(Tile otherTile){
        int rowDiff=rowDiff(otherTile);
        int colDiff=colDiff(otherTile);
        return Math.max(Math.abs(rowDiff),Math.abs(colDiff));
    }

    public int rowDiff(Tile otherTile){
        return otherTile.getRow()-row;
    }
    public int colDiff(Tile otherTile){
        return otherTile.getCol()-col;
    }

}
