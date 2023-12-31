public class Piece {
    final Team team;
    private BlackTile tile;

    public Piece(Team team) {
        this.team = team;
    }
    public void setTile(BlackTile tile){
        this.tile=tile;
    }

    public Team getTeam() {
        return team;
    }
    public Piece getNeighbor(Direction direction, int n) throws NoPieceOnWhiteException {
        return tile.getNeighbor(direction,n).getPiece();
    }
}


