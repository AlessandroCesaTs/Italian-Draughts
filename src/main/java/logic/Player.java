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

    public Player(String name, Team team,Game Game) throws NoPieceOnWhiteException {
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

    public void reloadPieces() throws NoPieceOnWhiteException {
        List<BlackTile> fullBlackTiles = Game.getBoard().getFullBlackTiles();
        pieces = fullBlackTiles.stream()
                .map(BlackTile::getPiece)
                .filter(piece -> piece.getTeam().equals(team))
                .collect(Collectors.toList());
    }
    public List<Piece> getPieces() throws NoPieceOnWhiteException {
        reloadPieces();
        return pieces;
    }

    public boolean hasPieces() throws NoPieceOnWhiteException {
        return numberOfPieces!=0;
    }

    public void loseOnePiece(){
        numberOfPieces--;
    }
    public void makeMove(TypeOfMove typeOfMove,Piece movingPiece,NeighborPosition targetPosition) throws IllegalMovementException, OutOfBoundsException, CantEatException {
        if (Objects.requireNonNull(typeOfMove) == TypeOfMove.Move) {
            movingPiece.movePiece(targetPosition);
        }else{
        movingPiece.eatPiece(targetPosition);
        }
    }
}
