package logic;

public abstract class Tile {
    final int row;
    final int col;
    private Board board;

    protected Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setBoard(Board boardToSet) {
        board = boardToSet;
    }

    public Board getBoard() {
        return board;
    }

    public abstract Piece getPiece();

    public boolean equals(Tile tile) {
        return tile.getRow() == row && tile.getCol() == col;
    }

}
