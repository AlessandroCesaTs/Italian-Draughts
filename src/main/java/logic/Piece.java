package logic;

import Exceptions.CantEatException;
import Exceptions.IllegalMovementException;
import Exceptions.OutOfBoundsException;

public class Piece {
    final Team team;
    private BlackTile tile;
    private boolean isKing=false;

    public Piece(Team team) {
        this.team = team;
    }
    public void setTile(BlackTile tile){
        this.tile=tile;
    }
    public void moveToTile(BlackTile targetTile) throws IllegalMovementException {
        tile.removePiece();
        setTile(targetTile);
        targetTile.movePieceHere(this);
        if (promotion()) {
            isKing = true;
        }
    }
    public BlackTile getTile(){
        return this.tile;
    }

    public Team getTeam() {
        return team;
    }

    public boolean getIfKing(){
        return isKing;
    }

    public void movePiece(NeighborPosition position) throws OutOfBoundsException, IllegalMovementException {
        if (isMoveValid(position)) {
            BlackTile targetTile = getTile().getNeighbor(position);
            if (targetTile.isFree()) {
                moveToTile(targetTile);
            } else {
                throw new IllegalMovementException("Already occupied");
            }
        }else{
            throw new IllegalMovementException("You can't go back");
        }
    }

    public boolean promotion(){
        return (team==Team.White && tile.getRow()==7)||(team==Team.Black && tile.getRow()==0);
    }
    public boolean isMoveValid(NeighborPosition position){
        if (isKing){
            return true;
        }else {
            if (team == Team.White) {
                return position==NeighborPosition.TopLeft || position==NeighborPosition.TopRight;
            }else{
                return position==NeighborPosition.BottomLeft || position==NeighborPosition.BottomRight;
            }
        }
    }

    public void movePieceByTwo(NeighborPosition position) throws OutOfBoundsException, IllegalMovementException {
        BlackTile targetTile=getTile().getNeighbor(position).getNeighbor(position);
        if (targetTile.isFree()){
            moveToTile(targetTile);
        }else{
            throw new IllegalMovementException("Already occupied");
        }
    }

    public void eatPiece(NeighborPosition position) throws CantEatException, OutOfBoundsException, IllegalMovementException {
        if (canEat(position)){
            tile.getNeighbor(position).removePiece();
            movePieceByTwo(position);
        }
    }

    private boolean isPositionAfterEatingFree(NeighborPosition position) throws OutOfBoundsException {
        return tile.getNeighbor(position).getNeighbor(position).isFree();
    }

    private boolean pieceOfOpposingTeam(NeighborPosition position) throws OutOfBoundsException {
        return tile.getNeighbor(position).getPiece().getTeam() != team;
    }
    private boolean pieceIsKing(NeighborPosition position) throws OutOfBoundsException {
        return tile.getNeighbor(position).getPiece().isKing;
    }

    public boolean canEat(NeighborPosition position) throws CantEatException, OutOfBoundsException {
        if (pieceOfOpposingTeam(position)){
            if (isPositionAfterEatingFree(position)){
                if (!pieceIsKing(position) || isKing ){
                    return true;
                }else{
                    throw new CantEatException("Simple piece can't eat king");
                }
            }else{
                throw new CantEatException("Can't eat because tile after is occupied");
            }
        }else {
            throw new CantEatException("Can't eat piece of same team");
        }
    }
    public void remove(){
        tile=null;
    }
}