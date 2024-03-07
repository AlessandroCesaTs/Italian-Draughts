package logic;

public class Move {
    private final Player player;
    private final Piece piece;
    private final NeighborPosition neighborDestination;
    private final TypeOfMove typeOfMove;

    public Move(Player player, Piece piece, NeighborPosition neighborDestination){
        this.player = player;
        this.piece = piece;
        this.neighborDestination = neighborDestination;
        typeOfMove=computeTypeOfMove();
    }

    public TypeOfMove computeTypeOfMove(){
        if (player.getTeam() == piece.team && neighborDestination!=null){
            if (player.shouldEat()){
                if (piece.canEat(neighborDestination)){
                    return TypeOfMove.EAT;
                }
            }else if (piece.canMovePieceByOne(neighborDestination)){
                return TypeOfMove.MOVE;
            }
        }
        return TypeOfMove.NO_MOVE;
    }

    public Piece getPiece() {
        return piece;
    }
    public NeighborPosition getDestination(){return neighborDestination;}
    public TypeOfMove getTypeOfMove(){
        return typeOfMove;
    }
}
