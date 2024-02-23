package logic;

import exceptions.NoPieceOnWhiteException;

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
