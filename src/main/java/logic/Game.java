package logic;

import exceptions.*;
import gui.GraphicBoard;
import multiplayer.Guest;
import multiplayer.Host;
import multiplayer.MultiplayerActions;
import multiplayer.Role;
import observers.GameObserver;
import observers.MoveMadeObserver;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class Game implements MoveMadeObserver, GameInterface {
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


    public Game(String player1Name, String player2Name,Team team1,Team team2) throws IllegalTilePlacementException, IllegalTeamsCompositionException {
        if (team1.equals(team2)){ //questa in teoria non può verificarsi perchè nella gui se uno sceglie un team l'altro cambia automaticamente
            throw new IllegalTeamsCompositionException();
        }
        board = new Board();
        player1 =new Player(player1Name,team1,this, Role.Null);
        player2 =new Player(player2Name,team2,this,Role.Null);
        activePlayer= player1;
        inactivePlayer=player2;
        multiRole = null;
    }

    public Game(String playerName, Team team, String hostIPField) throws Exception {
        switch (playerName){
            case "Host" -> {
                board = new Board();
                player1 = new Player(playerName, team, this, Role.Host);
                player2 = new Player("Guest", Team.Black, this, Role.Guest);
                activePlayer = player1;
                inactivePlayer = player2;
                multiRole = new Host(this);
                multiRole.connect();
            }
            case "Guest" -> {
                board = new Board();
                player1 = new Player(playerName, team, this, Role.Guest);
                player2 = new Player("Host", Team.White, this, Role.Host);
                activePlayer = player2;
                inactivePlayer = player1;
                multiRole = new Guest(hostIPField,this);
                multiRole.connect();
            }
            default -> throw new Exception("Something has gone wrong!");
        }
    }

    @Override
    public void onMoveMade(){
        Move move= gBoard.getMoveFromGUI();
        playTurn(move);
    }

    @Override
    public void playTurn(Move move){
        TypeOfMove typeOfMove=move.getTypeOfMove();
        if (typeOfMove!=TypeOfMove.NoMove){
            Piece movingPiece=move.getPiece();
            NeighborPosition targetPosition=move.getDestination();
            if (typeOfMove.equals(TypeOfMove.Eat)){
                eat(movingPiece, targetPosition);
                if (checkMultipleEating(movingPiece) && consecutiveEatings <= 3) {
                    sendMove(1);
                    return;
                }
            } else {
                Move(movingPiece, targetPosition);
            }
            sendMove(0);
            checkGameOver();
            currentRound++;
            changeActivePlayer();
        }
    }

    private void sendMove(int messageType) {
        if (multiRole != null && activePlayer.getRole() == player1.getRole()){
            Point startTile=gBoard.getStartTile();
            Point endTile=gBoard.getStartTile();
            multiRole.sendMove(startTile, endTile, messageType);}
    }

    @Override
    public void Move(Piece movingPiece, NeighborPosition targetPosition){
        activePlayer.makeMove(TypeOfMove.Move, movingPiece, targetPosition);
        gBoard.movePiece(movingPiece, targetPosition);
        checkPromotion(movingPiece);
        roundsWithoutEating++;
    }

    @Override
    public void eat(Piece movingPiece, NeighborPosition targetPosition) {
        gBoard.eatPiece(movingPiece, targetPosition);
        Piece eatenPiece= movingPiece.getTile().getNeighbor(targetPosition).getPiece();
        activePlayer.makeMove(TypeOfMove.Eat, movingPiece, targetPosition);

        inactivePlayer.loseOnePiece(eatenPiece);
        checkPromotion(movingPiece);
        roundsWithoutEating=0;
        consecutiveEatings++;
    }


    @Override
    public void checkGameOver(){
        if (!inactivePlayer.hasPieces() || !inactivePlayer.canMove()){
            winnerPlayer=activePlayer;
            gameOver=true;
            if (multiRole != null){
                multiRole.close();
            }
        }else if(roundsWithoutEating==40){
            gameOver=true;
            if (multiRole != null){
                multiRole.close();
            }
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
    public void checkPromotion(Piece movingPiece){
        if (movingPiece.pieceHasToBePromoted()){
            gBoard.getGraphicPiece(movingPiece).promote();
        }
    }


    public void addObserver(GameObserver observer){
        observers.add(observer);
    }
    public void notifyObservers(){
        for (GameObserver observer:observers){
            observer.update(this);
        }
    }
    @Override
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
    @Override
    public Player getActivePlayer() {
        return activePlayer;
    }
    @Override
    public Player getInactivePlayer() {
        return inactivePlayer;
    }
    @Override
    public int getCurrentRound(){
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
    public boolean isGameOver(){return gameOver; }

    @Override
    public int getRoundsWithoutEating() {
        return roundsWithoutEating;
    }

    public GraphicBoard getGBoard() {
        return gBoard;
    }

    @Override
    public Player getPlayer1() {
        return player1;
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
