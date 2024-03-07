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

    private TypeOfMove computeTypeOfMove(){
        if (player.getTeam() == piece.team && neighborDestination!=null){
            if (player.shouldEat()){
                if (piece.canEat(neighborDestination)){
                    return TypeOfMove.Eat;
                }
            }else if (piece.canMovePieceByOne(neighborDestination)){
                return TypeOfMove.Move;
            }
        }
        return TypeOfMove.NoMove;
    }

    public Piece getPiece() {
        return piece;
    }
    public NeighborPosition getDestination(){return neighborDestination;}
    public TypeOfMove getTypeOfMove(){
        return typeOfMove;
    }
}
