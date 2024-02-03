package logic;

import Exceptions.CantEatException;
import Exceptions.IllegalMovementException;
import Exceptions.NoPieceOnWhiteException;
import Exceptions.OutOfBoundsException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Player {
    private final String name;
    private final Team team;
    public final Game Game;

    private int numberOfPieces=12;
    private List<Piece> pieces;

    public Player(String name, Team team,Game Game) {
        this.name = name;
        this.team = team;
        this.Game=Game;
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
        List<BlackTile> fullBlackTiles = null;
        try {
            fullBlackTiles = Game.getBoard().getFullBlackTiles();
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

    public void loseOnePiece(){
        numberOfPieces--;
    }
    public void makeMove(TypeOfMove typeOfMove,Piece movingPiece,NeighborPosition targetPosition) throws IllegalMovementException, OutOfBoundsException, CantEatException {
        if (Objects.requireNonNull(typeOfMove) == TypeOfMove.Move) {
            movingPiece.movePieceByOne(targetPosition);
        }else{
        movingPiece.eatPiece(targetPosition);
        }
    }
    public boolean isWhite(){
        return team==Team.White;
    }
}
