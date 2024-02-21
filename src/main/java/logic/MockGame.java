package logic;

import Exceptions.*;

import java.util.List;

public class MockGame implements GameInterface{

    final Player player1;
    final Player player2;
    private Player activePlayer;
    private Player inactivePlayer;
    private Player winnerPlayer=null;
    private boolean gameOver=false;
    private final Board board;
    private int currentRound=1;
    private int roundsWithoutEating=0;
    private int consecutiveEatings;

    public MockGame(String player1Name, String player2Name,Team team1,Team team2) throws IllegalTilePlacementException, IllegalTeamsCompositionException {
        if (team1.equals(team2)){ //questa in teoria non può verificarsi perchè nella gui se uno sceglie un team l'altro cambia automaticamente
            throw new IllegalTeamsCompositionException();
        }
        board = new Board();
        player1 =new Player(player1Name,team1,this);
        player2 =new Player(player2Name,team2,this);
        activePlayer= player1;
        inactivePlayer=player2;
    }

    @Override
    public void playTurn(Move move) throws OutOfBoundsException{
        TypeOfMove typeOfMove=move.getTypeOfMove();
        if (typeOfMove!=TypeOfMove.NoMove){
            Piece movingPiece=move.getPiece();
            NeighborPosition targetPosition=move.getDestination();
            if (typeOfMove.equals(TypeOfMove.Eat)){
                eat(movingPiece, targetPosition);
                if(checkMultipleEating(movingPiece) && consecutiveEatings <=3) {
                    return;
                }
            }else {
                Move(movingPiece, targetPosition);
            }
            checkGameOver();
            currentRound++;
            changeActivePlayer();
        }
    }

    @Override
    public void Move(Piece movingPiece, NeighborPosition targetPosition) throws OutOfBoundsException {
        activePlayer.makeMove(TypeOfMove.Move, movingPiece, targetPosition);
        roundsWithoutEating++;

    }

    @Override
    public void eat(Piece movingPiece, NeighborPosition targetPosition) throws OutOfBoundsException {
        Piece eatenPiece= movingPiece.getTile().getNeighbor(targetPosition).getPiece();
        activePlayer.makeMove(TypeOfMove.Eat, movingPiece, targetPosition);

        inactivePlayer.loseOnePiece(eatenPiece);
        roundsWithoutEating=0;
        consecutiveEatings++;

    }

    @Override
    public void checkGameOver(){
        if (!inactivePlayer.hasPieces() || !inactivePlayer.canMove()){
            winnerPlayer=activePlayer;
            gameOver=true;
        }else if(roundsWithoutEating==40){
            gameOver=true;
        }

    }

    @Override
    public boolean checkMultipleEating(Piece movingPiece) {
        if(movingPiece.canEatAnotherPiece()) {
            return true;
        } else {
            consecutiveEatings =0;
            return false;
        }
    }

    @Override
    public void changeActivePlayer() {
        if (activePlayer == player1){
            activePlayer = player2;
            inactivePlayer=player1;
        } else {
            activePlayer = player1;
            inactivePlayer=player2;
        }
    }

    @Override
    public Player getActivePlayer() {
        return activePlayer;
    }

    @Override
    public Player getInactivePlayer() {
        return inactivePlayer;
    }

    @Override
    public int getCurrentRound() {
        return currentRound;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Player getWinnerPlayer() {
        return winnerPlayer;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public int getRoundsWithoutEating() {
        return roundsWithoutEating;
    }

    @Override
    public Team getActiveTeam(){
        return activePlayer.getTeam();
    }
    @Override
    public Piece getPiece(int row,int col) throws NoPieceOnWhiteException {
        return getBoard().getPiece(row,col);
    }
    @Override
    public List<BlackTile> getFullBlackTiles() throws NoPieceOnWhiteException {
        return getBoard().getFullBlackTiles();
    }
}
