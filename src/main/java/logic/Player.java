package logic;

import Exceptions.NoPieceOnWhiteException;

import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private final String name;
    public final Team team;
    private List<Piece> pieces;

    public Player(String name, Team team) {
        this.name = name;
        this.team = team;
        try {
            this.pieces = getPieces();
        } catch (NoPieceOnWhiteException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName(){
        return name;
    }
    public Team getTeam(){
        return team;
    }


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
        return getPieces().isEmpty();
    }
}
