package logic;

import Exceptions.*;
import gui.GraphicBoard;
import multiplayer.Guest;
import multiplayer.Host;
import multiplayer.MultiplayerActions;
import observers.GameObserver;
import observers.MoveMadeObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Game implements MoveMadeObserver {
    final Player player1;
    final Player player2;
    private Player activePlayer;
    private Player inactivePlayer;
    private Player winnerPlayer=null;
    private boolean gameOver=false;
    private final Board board;
    private GraphicBoard gBoard;
    private int currentRound=1;
    private int roundsWithoutEating=0;
    private final List<GameObserver> observers = new ArrayList<>();
    private int consecutiveEatings;
    private final MultiplayerActions multiRole;

    public Game(String player1Name, String player2Name,Team team1,Team team2) throws IllegalTilePlacementException, NoPieceOnWhiteException, IllegalTeamsCompositionException, CantEatException, IllegalMovementException, OutOfBoundsException, NotOnDiagonalException {
        if (team1.equals(team2)){ //questa in teoria non può verificarsi perchè nella gui se uno sceglie un team l'altro cambia automaticamente
            throw new IllegalTeamsCompositionException();
        }
        board = new Board();
        player1 =new Player(player1Name,team1,this);
        player2 =new Player(player2Name,team2,this);
        activePlayer= player1;
        inactivePlayer=player2;
        multiRole = null;
    }

    public Game(String playerName, Team team, String hostIPField) throws Exception {
        switch (playerName){
            case "Host" -> {
                board = new Board();
                player1 = new Player(playerName, team, this);
                player2 = new Player("Guest", Team.Null, this);
                activePlayer = player1;
                inactivePlayer = player2;
                multiRole = new Host(this);
                multiRole.connect();
            }
            case "Guest" -> {
                board = new Board();
                player2 = new Player(playerName, team, this);
                player1 = new Player("Host", Team.Null, this);
                activePlayer = player1;
                inactivePlayer = player2;
                multiRole = new Guest(hostIPField,this);
                multiRole.connect();
            }
            default -> throw new Exception("Something has gone wrong!");
        }
    }

    @Override
    public void onMoveMade() throws NotOnDiagonalException, CantEatException, IllegalMovementException, OutOfBoundsException {
        playTurn();
    }

    public void playTurn() throws CantEatException, IllegalMovementException, OutOfBoundsException, NotOnDiagonalException {
        Move move= gBoard.getMoveFromGUI();
        TypeOfMove typeOfMove=move.getTypeOfMove();
        if (typeOfMove!=TypeOfMove.NoMove){
            Piece movingPiece=move.getPiece();
            NeighborPosition targetPosition=move.getDestination();
            if (typeOfMove.equals(TypeOfMove.Eat)){
                eat(movingPiece, targetPosition);
                if(checkMultipleEating(movingPiece) && consecutiveEatings <=3) {
                    if(multiRole != null && activePlayer != null)
                        multiRole.sendMove(gBoard.getStartTile(), gBoard.getEndTile(), 1);
                    return;
                }
            }else {
                Move(movingPiece, targetPosition);
            }
            if(multiRole != null && activePlayer.getTeam() != Team.Null)
                multiRole.sendMove(gBoard.getStartTile(), gBoard.getEndTile(), 0);
            checkGameOver();
            currentRound++;
            changeActivePlayer();
        }
    }

    private void Move(Piece movingPiece, NeighborPosition targetPosition) throws OutOfBoundsException {
        activePlayer.makeMove(TypeOfMove.Move, movingPiece, targetPosition);
        gBoard.movePiece(movingPiece, targetPosition);
        checkPromotion(movingPiece);
        roundsWithoutEating++;
    }

    private void eat(Piece movingPiece, NeighborPosition targetPosition) throws OutOfBoundsException {
        gBoard.eatPiece(movingPiece, targetPosition);
        Piece eatenPiece= movingPiece.getTile().getNeighbor(targetPosition).getPiece();
        activePlayer.makeMove(TypeOfMove.Eat, movingPiece, targetPosition);

        inactivePlayer.loseOnePiece(eatenPiece);
        checkPromotion(movingPiece);
        roundsWithoutEating=0;
        consecutiveEatings++;
    }

    private void checkGameOver() throws  OutOfBoundsException {
        if ((!inactivePlayer.hasPieces() || !inactivePlayer.canMove()) && multiRole == null){
            winnerPlayer=activePlayer;
            gameOver=true;
        }else if(roundsWithoutEating==40){
            gameOver=true;
        }
    }

    private boolean checkMultipleEating(Piece movingPiece) {
        if(movingPiece.canEatAnotherPiece()) {
            return true;
        } else {
            consecutiveEatings =0;
            return false;
        }
    }
    private void checkPromotion(Piece movingPiece){
        if (movingPiece.pieceHasToBePromoted()){
            gBoard.getGraphicPiece(movingPiece).promote();
        }
    }
    public void addObserver(GameObserver observer){
        observers.add(observer);
    }
    private void notifyObservers(){
        for (GameObserver observer:observers){
            observer.update(this);
        }
    }
    public void changeActivePlayer() {
        if (activePlayer == player1){
            activePlayer = player2;
            inactivePlayer = player1;
        } else {
            activePlayer = player1;
            inactivePlayer = player2;
        }
        notifyObservers();
    }

    public void setGBoard(GraphicBoard gBoard) {
        this.gBoard = gBoard;
        gBoard.addMoveMadeObserver(this);
    }
    public Player getActivePlayer() {
        return activePlayer;
    }
    public Player getInactivePlayer() {
        return inactivePlayer;
    }
    public int getCurrentRound(){
        return currentRound;
    }

    public Board getBoard() {
        return board;
    }
    public Player getWinnerPlayer() {
        return winnerPlayer;
    }
    public boolean isGameOver(){return gameOver; }

    public int getRoundWithoutEating() {
        return roundsWithoutEating;
    }

    public GraphicBoard getGBoard() {
        return gBoard;
    }
}
