public class Piece {
    final Team team;
    private BlackTile tile;

    public Piece(Team team) {
        this.team = team;
    }
    public void setTile(BlackTile tile){
        this.tile=tile;
    }
    public BlackTile getTile(){
        return this.tile;
    }

    public Team getTeam() {
        return team;
    }

    /*
    public void movePiece(NeighborPosition position){
        if (getTile().getNeighbor(position).getPiece()==null){

        }
    }

     */

}


