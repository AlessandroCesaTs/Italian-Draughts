public class Piece {
    final Team team;
    private BlackTile tile;

    public Piece(Team team) {
        this.team = team;
    }
    public void setTile(BlackTile tile){
        this.tile=tile;
    }
    public void moveToTile(BlackTile targetTile) throws AlreadyOccupiedException {
        tile.removePiece();
        setTile(targetTile);
        targetTile.movePieceHere(this);
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

    public void eatPiece(NeighborPosition position) throws AlreadyOccupiedException, CantEatPieceOfSameTeamException {
        if (canEat(position)){
            tile.getNeighbor(position).removePiece();
            movePieceByTwo(position);
        }
    }

    private boolean isPositionAfterEatingFree(NeighborPosition position) {
        return tile.getNeighbor(position).getNeighbor(position).isFree();
    }

    private boolean pieceOfOpposingTeam(NeighborPosition position) {
        return tile.getNeighbor(position).getPiece().getTeam() != team;
    }


    public boolean canEat(NeighborPosition position) throws CantEatPieceOfSameTeamException, AlreadyOccupiedException {
        if (pieceOfOpposingTeam(position)){
            if (isPositionAfterEatingFree(position)){
                return true;
            }else{
                throw new AlreadyOccupiedException("Can't eat because tile after is occupied");
            }
        }else {
            throw new CantEatPieceOfSameTeamException();
        }
    }
    public void remove(){
        tile=null;
    }
}