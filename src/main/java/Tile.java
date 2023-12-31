public abstract class Tile {
    final int x;
    final int y;

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
}
