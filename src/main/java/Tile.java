public abstract class Tile {
    final int x;
    final int y;
    private Board board;

    protected Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }
     public int getY(){
         return y;
     }

     public void setBoard(Board boardToSet){
        board=boardToSet;
     }
     public Board getBoard(){
        return board;
     }
     public abstract Piece getPiece() throws NoPieceOnWhiteException;

    public Tile getNeighbor(Direction direction, int n) throws NoPieceOnWhiteException {
        if (direction.equals(Direction.Left)){
            return board.getTile(x+n,y-n);
        }else{
            return board.getTile(x+n,y+n);
        }
    }


}
