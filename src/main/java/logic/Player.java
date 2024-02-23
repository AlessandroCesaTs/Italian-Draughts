package logic;


import multiplayer.Role;
import exceptions.NoPieceOnWhiteException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Player {
    private final String name;
    private final Team team;
    public final GameInterface GameInterface;
    private final Role role;
    private int numberOfPieces=12;
    private List<Piece> pieces;

    public Player(String name, Team team, Game Game, Role role) {
        this.name = name;
        this.team = team;
        this.GameInterface = GameInterface;
        this.role = role;
        this.pieces = getPieces();
    }

    public String getName(){
        return name;
    }
    public Team getTeam(){
        return team;
    }
    public int getNumberOfPieces() {return numberOfPieces;}
    public boolean equals(Player player){
        return Objects.equals(name, player.getName());
    }

    public void reloadPieces(){
        List<BlackTile> fullBlackTiles;
        try {
            fullBlackTiles = GameInterface.getFullBlackTiles();
        } catch (NoPieceOnWhiteException e) {
            throw new RuntimeException(e);
        }
        pieces = fullBlackTiles.stream()
                .map(BlackTile::getPiece)
                .filter(piece -> piece.getTeam().equals(team))
                .collect(Collectors.toList());
    }
    public List<Piece> getPieces(){
        reloadPieces();
        return pieces;
    }

    public boolean hasPieces(){
        return numberOfPieces!=0;
    }

    public void loseOnePiece(Piece piece){
        pieces.remove(piece);
        numberOfPieces--;
    }
    public void makeMove(TypeOfMove typeOfMove,Piece movingPiece,NeighborPosition targetPosition) {
        if (Objects.requireNonNull(typeOfMove) == TypeOfMove.Move) {
            movingPiece.movePieceByOne(targetPosition);
        }else{
        movingPiece.eatPiece(targetPosition);
        }
    }

    public boolean shouldEat(){
        for (Piece piece:pieces){
            for (NeighborPosition destination : new NeighborPosition[]{NeighborPosition.TopLeft, NeighborPosition.TopRight,NeighborPosition.BottomLeft,NeighborPosition.BottomRight})
            {
                if (piece.canEat(destination)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean canMove() {
        for (Piece piece:pieces){
            for (NeighborPosition destination : new NeighborPosition[]{NeighborPosition.TopLeft, NeighborPosition.TopRight,NeighborPosition.BottomLeft,NeighborPosition.BottomRight})
            {
                if (piece.canEat(destination) || piece.canMovePieceByOne(destination)){
                    return true;
                }
            }

        }
        return false;
    }

    public Role getRole() {
        return role;
    }
}
