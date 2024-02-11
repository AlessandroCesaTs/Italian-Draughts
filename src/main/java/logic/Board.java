package logic;

import Exceptions.IllegalTilePlacementException;
import Exceptions.NoPieceOnWhiteException;

import java.util.List;
import java.util.ArrayList;



public class Board {
    private final int size=8;
    private final Tile[][] tiles = new Tile[size][size];

    public Board() throws IllegalTilePlacementException{
        for (int row=0;row<size;row++){
            for (int col=0;col<size;col++){
                createTile(row, col);
            }
        }
        populateNeighbors();
    }

    private void populateNeighbors() {
        for (Tile[] tileRow: tiles){
            for (Tile tile:tileRow){
                if(tile instanceof BlackTile){
                    ((BlackTile) tile).setNeighbors();
                }
            }
        }
    }

    private void createTile(int row, int col) throws IllegalTilePlacementException {
        if(isPositionForBlackTile(row, col)){
            tiles[row][col]=BlackTile.createBlackTile(row, col);
            placePiece(row, col);
        }else{
            tiles[row][col]=WhiteTile.createWhiteTile(row, col);
        }
        tiles[row][col].setBoard(this);
    }

    private static boolean isPositionForBlackTile(int row, int col) {
        return (col + row) % 2 == 0;
    }

    private void placePiece(int row, int col){
        if (row <3){
            ((BlackTile) tiles[row][col]).setPiece(new Piece(Team.White));
        }else if(row <8 && row >4){
            ((BlackTile) tiles[row][col]).setPiece(new Piece(Team.Black));
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

    public List<BlackTile> getFullBlackTiles() throws NoPieceOnWhiteException {
        List<BlackTile> fullBlackTiles = new ArrayList<>();
        for (int col=0; col<size; col++){
            for (int row=0; row<size; row++){
                if(isPositionForBlackTile(row, col) && getPiece(row,col) != null){
                    fullBlackTiles.add((BlackTile) tiles[row][col]);
                }
            }
        }
        return fullBlackTiles;
    }

}
