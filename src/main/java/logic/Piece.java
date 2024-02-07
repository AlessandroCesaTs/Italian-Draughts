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

    public void movePieceByOne(NeighborPosition position) throws OutOfBoundsException, IllegalMovementException {
        if (isMoveValid(position)) {
            BlackTile targetTile = getTile().getNeighbor(position);
            if (targetTile.isFree()) {
                moveToTile(targetTile);
            }
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
        if (isMoveValid(position)) {
            if (targetTile.isFree()) {
                moveToTile(targetTile);
            }
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
        if (tile.getNeighbor(position).isFree()){
            return true;
        }else {
            return tile.getNeighbor(position).getPiece().getTeam() != team;
        }
    }
    private boolean pieceIsKing(NeighborPosition position) throws OutOfBoundsException {
        if (tile.getNeighbor(position).isFree()){
            return false;
        }else {
            return tile.getNeighbor(position).getPiece().isKing;
        }
    }

    public boolean canEat(NeighborPosition position) throws OutOfBoundsException {
        return  !tile.getNeighbor(position).isFree() && pieceOfOpposingTeam(position) && isPositionAfterEatingFree(position) && (!pieceIsKing(position) || isKing);
    }
    public boolean isWhite() {
        return team == Team.White;
    }
    public void remove(){
        tile=null;
    }
}