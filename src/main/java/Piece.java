public class Piece {
    final Team team;
    private BlackTile tile;

    public Piece(Team team) {
        this.team = team;
    }
    public void setTile(BlackTile tile){
        this.tile=tile;
    }
    public void moveToTile(BlackTile tile) throws AlreadyOccupiedException {
        setTile(tile);
        tile.movePieceHere(this);
    }
    public BlackTile getTile(){
        return this.tile;
    }

    public Team getTeam() {
        return team;
    }


    public void movePiece(NeighborPosition position) throws AlreadyOccupiedException {
        BlackTile targetTile=getTile().getNeighbor(position);
        if (targetTile.isFree()){
            moveToTile(targetTile);
        }else{
            throw new AlreadyOccupiedException();
        }
    }
    public void movePieceByTwo(NeighborPosition position) throws AlreadyOccupiedException {
        BlackTile targetTile=getTile().getNeighbor(position).getNeighbor(position);
        if (targetTile.isFree()){
            moveToTile(targetTile);
        }else{
            throw new AlreadyOccupiedException();
        }
    }

    public void eatPiece(NeighborPosition position) throws AlreadyOccupiedException {
        if (tile.getNeighbor(position).getPiece().getTeam()!=team && tile.getNeighbor(position).getNeighbor(position).isFree()){
            movePieceByTwo(position);
            tile.getNeighbor(position).removePiece();
        }
    }
}


