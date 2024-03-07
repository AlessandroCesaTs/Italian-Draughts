package logic;

import java.util.List;

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

    public void moveToTile(BlackTile targetTile) {
        tile.removePiece();
        setTile(targetTile);
        targetTile.movePieceHere(this);
        if (pieceHasToBePromoted()) {
            isKing = true;
        }
    }

    public void movePieceByOne(NeighborPosition position) {
        if (isMoveValid(position) & !isMoveOutOfBounds(position)) {
            BlackTile targetTile = getNeighbor(position);
            if (targetTile.isFree()) {
                moveToTile(targetTile);
            }
        }
    }

    public boolean canMovePieceByOne(NeighborPosition position) {
        if (isMoveValid(position) & !isMoveOutOfBounds(position)) {
            BlackTile targetTile = getNeighbor(position);
            return targetTile.isFree();
        }else{
            return false;
        }
    }

    public boolean pieceHasToBePromoted(){
        return (team==Team.WHITE && tile.getRow()==7)||(team==Team.BLACK && tile.getRow()==0);
    }
    private boolean isMoveValid(NeighborPosition position){
        if (isKing){
            return true;
        }else {
            if (team == Team.WHITE) {
                return position==NeighborPosition.TOP_LEFT || position==NeighborPosition.TOP_RIGHT;
            }else{
                return position==NeighborPosition.BOTTOM_LEFT || position==NeighborPosition.BOTTOM_RIGHT;
            }
        }
    }
    private boolean isMoveOutOfBounds(NeighborPosition position) {
        return getNeighbor(position) == null;
    }

    private boolean isMoveAfterOutOfBounds(NeighborPosition position){
        if (isMoveOutOfBounds(position)){
            return true;
        }else {
            return getNeighbor(position).getNeighbor(position) == null;
        }
    }

    private void movePieceByTwo(NeighborPosition position) {
        BlackTile targetTile=getNeighbor(position).getNeighbor(position);
        if (isMoveValid(position)) {
            if (targetTile.isFree()) {
                moveToTile(targetTile);
            }
        }
    }

    public void eatPiece(NeighborPosition position){
        if (canEat(position)){
            getNeighbor(position).removePiece();
            movePieceByTwo(position);
        }
    }

    private boolean isPositionAfterEatingFree(NeighborPosition position){
        return getNeighbor(position).getNeighbor(position).isFree();
    }

    private boolean pieceOfOpposingTeam(NeighborPosition position) {
        if (isNeighborFree(position)){
            return true;
        }else {
            return getNeighborPiece(position).getTeam() != team;
        }
    }
    private boolean neighboringPieceIsKing(NeighborPosition position)  {
        if (isNeighborFree(position)){
            return false;
        }else {
            return getNeighborPiece(position).isKing;
        }
    }

    public boolean canEat(NeighborPosition position) {
        return  isMoveValid(position) && isPositionGoodForEating(position) && pieceOfOpposingTeam(position) && canOtherPieceBeEaten(position);
    }

    private boolean canOtherPieceBeEaten(NeighborPosition position) {
        return !neighboringPieceIsKing(position) || isKing;
    }

    private boolean isPositionGoodForEating(NeighborPosition position){
        return  !isMoveAfterOutOfBounds(position) && !isNeighborFree(position) && isPositionAfterEatingFree(position);
    }

    public boolean canEatAnotherPiece() {

        List<NeighborPosition> neighborPositions = NeighborPosition.getNeighborPosition();

        for (NeighborPosition position : neighborPositions) {
            if(canEat(position)){
                System.out.println(position);
                return true;
            }
        }
        return false;
    }

    public BlackTile getTile(){
        return tile;
    }

    public Team getTeam() {
        return team;
    }

    public boolean getIfKing(){
        return isKing;
    }

    public void remove(){
        tile=null;
    }

    private BlackTile getNeighbor(NeighborPosition position){
        return tile.getNeighbor(position);
    }
    private Piece getNeighborPiece(NeighborPosition position){
        return getNeighbor(position).getPiece();
    }
    private boolean isNeighborFree(NeighborPosition position){
        return getNeighbor(position).isFree();
    }

}