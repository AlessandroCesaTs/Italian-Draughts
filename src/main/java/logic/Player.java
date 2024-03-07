package logic;


import multiplayer.Role;
import exceptions.NoPieceOnWhiteException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Player {
    private final String name;
    private final Team team;
    public final GameInterface gameInterface;
    private final Role role;
    private int numberOfPieces=12;
    private List<Piece> pieces;

    public Player(String name, Team team, GameInterface gameInterface, Role role) {
        this.name = name;
        this.team = team;
        this.gameInterface = gameInterface;
        this.role = role;
        setPieces();
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

    private void setPieces(){
        List<BlackTile> fullBlackTiles;
        try {
            fullBlackTiles = gameInterface.getFullBlackTiles();
        } catch (NoPieceOnWhiteException e) {
            throw new RuntimeException(e);
        }
        pieces = fullBlackTiles.stream()
                .map(BlackTile::getPiece)
                .filter(piece -> piece.getTeam().equals(team))
                .collect(Collectors.toList());
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
