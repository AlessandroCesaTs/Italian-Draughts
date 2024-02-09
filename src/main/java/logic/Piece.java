package logic;

import Exceptions.CantEatException;
import Exceptions.IllegalMovementException;
import Exceptions.OutOfBoundsException;

import java.awt.*;
import java.util.List;

public class Piece {
    final Team team;
    private BlackTile tile;
    private boolean isKing=false;
    private Point newCoordinates = new Point();

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
        if (isMoveValid(position) & !isMoveOutOfBounds(position)) {
            BlackTile targetTile = getTile().getNeighbor(position);
            if (targetTile.isFree()) {
                moveToTile(targetTile);
                setNewCoordinates(targetTile.getCol(), targetTile.getRow());
            }
        }
    }

    public boolean canMovePieceByOne(NeighborPosition position) throws OutOfBoundsException, IllegalMovementException {
        if (isMoveValid(position) & !isMoveOutOfBounds(position)) {
            BlackTile targetTile = getTile().getNeighbor(position);
            if (targetTile.isFree()) {
                return true;
            }else{
                return false;
            }
        }else{
            return false;
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
    public boolean isMoveOutOfBounds(NeighborPosition position) {
        return getTile().getNeighbor(position) == null;
    }

    public boolean isMoveAfterOutOfBounds(NeighborPosition position){
        if (isMoveOutOfBounds(position)){
            return true;
        }else {
            return tile.getNeighbor(position).getNeighbor(position) == null;
        }
    }

    public void movePieceByTwo(NeighborPosition position) throws OutOfBoundsException, IllegalMovementException {
        BlackTile targetTile=getTile().getNeighbor(position).getNeighbor(position);
        if (isMoveValid(position)) {
            if (targetTile.isFree()) {
                moveToTile(targetTile);
                setNewCoordinates(targetTile.getCol(), targetTile.getRow());
            }
        }
    }

    public void eatPiece(NeighborPosition position) throws OutOfBoundsException, IllegalMovementException {
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
        return  !isMoveOutOfBounds(position) && !isMoveAfterOutOfBounds(position) && !tile.getNeighbor(position).isFree() && pieceOfOpposingTeam(position) && isPositionAfterEatingFree(position) && (!pieceIsKing(position) || isKing) && (isMoveValid(position));
    }

    public boolean canEatAnotherPiece() {

        List<NeighborPosition> neighborPositions = NeighborPosition.getNeighborPosition();

        for (NeighborPosition position : neighborPositions) {
            try {
                if(canEat(position)){
                    System.out.println(position);
                    return true;
                }
            } catch (OutOfBoundsException e) {
                return false;
            }
        }
        return false;
    }
    public boolean isWhite() {
        return team == Team.White;
    }
    public void remove(){
        tile=null;
    }
    private void setNewCoordinates(int x, int y){
        newCoordinates.x=x;
        newCoordinates.y=y;
    }

    public Point getNewCoordinates() {
        return newCoordinates;
    }
}