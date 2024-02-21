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
            BlackTile targetTile = getTile().getNeighbor(position);
            if (targetTile.isFree()) {
                moveToTile(targetTile);
            }
        }
    }

    public boolean canMovePieceByOne(NeighborPosition position) {
        if (isMoveValid(position) & !isMoveOutOfBounds(position)) {
            BlackTile targetTile = getTile().getNeighbor(position);
            return targetTile.isFree();
        }else{
            return false;
        }
    }

    public boolean pieceHasToBePromoted(){
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

    public void movePieceByTwo(NeighborPosition position) {
        BlackTile targetTile=getTile().getNeighbor(position).getNeighbor(position);
        if (isMoveValid(position)) {
            if (targetTile.isFree()) {
                moveToTile(targetTile);
            }
        }
    }

    public void eatPiece(NeighborPosition position){
        if (canEat(position)){
            tile.getNeighbor(position).removePiece();
            movePieceByTwo(position);
        }
    }

    private boolean isPositionAfterEatingFree(NeighborPosition position){
        return tile.getNeighbor(position).getNeighbor(position).isFree();
    }

    private boolean pieceOfOpposingTeam(NeighborPosition position) {
        if (tile.getNeighbor(position).isFree()){
            return true;
        }else {
            return tile.getNeighbor(position).getPiece().getTeam() != team;
        }
    }
    private boolean neighboringPieceIsKing(NeighborPosition position)  {
        if (tile.getNeighbor(position).isFree()){
            return false;
        }else {
            return tile.getNeighbor(position).getPiece().isKing;
        }
    }

    public boolean canEat(NeighborPosition position) {
        return  isMoveValid(position) && isPositionGoodForEating(position) && pieceOfOpposingTeam(position) && canOtherPieceBeEaten(position);
    }

    private boolean canOtherPieceBeEaten(NeighborPosition position) {
        return !neighboringPieceIsKing(position) || isKing;
    }

    private boolean isPositionGoodForEating(NeighborPosition position){
        return  !isMoveAfterOutOfBounds(position) && !tile.getNeighbor(position).isFree() && isPositionAfterEatingFree(position);
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
        return this.tile;
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

}