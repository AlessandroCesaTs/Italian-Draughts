package logic;

import Exceptions.*;
import gui.GraphicBoard;
import observers.GameObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    final Player player1;
    final Player player2;
    private Player activePlayer;
    private Player inactivePlayer;
    private Player winnerPlayer;
    private final Board board;
    private GraphicBoard gBoard;
    private int currentRound=1;
    private List<GameObserver> observers = new ArrayList<>();

    public Game(String player1Name, String player2Name,Team team1,Team team2) throws IllegalTilePlacementException, NoPieceOnWhiteException, IllegalTeamsCompositionException {
        if (team1.equals(team2)){ //questa in teoria non può verificarsi perchè nella gui se uno sceglie un team l'altro cambia automaticamente
            throw new IllegalTeamsCompositionException();
        }
        board = new Board();
        gBoard = new GraphicBoard(this);
        player1 =new Player(player1Name,team1,this);
        player2 =new Player(player2Name,team2,this);
        activePlayer= player1;
        inactivePlayer=player2;
        //startGame(); //sostituirei con play() il metodo startGame() che fa quello che vedi sotto
    }
    public void startGame(){
       Thread gameThread = new Thread(() -> { //nuovo thread per non bloccare il programma
           while (!isGameOver()){
               waitForMove();
           }
       });
       gameThread.start();
    }
    public void waitForMove(){
        while (!gBoard.hasMoveBeenMade()){
            try {
                Thread.sleep(100); //questo serve per non far andare il while in loop troppo velocemente
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Move lastMove = gBoard.getMoveFromGUI();
        if (lastMove!=null){
            try {
                lastMove.makeMove();
            } catch (IllegalMovementException | CantEatException | OutOfBoundsException e) {
                e.printStackTrace();
            }
            currentRound++;
            changeActivePlayer();
        }
    }
    public boolean isGameOver(){
        //Ho tolto l'exception NoPieceOnWhiteException dal metodo hasPieces perchè penso venga gestito dal game over
        //così evitiamo di mettere troppi try catch
        return !player1.hasPieces() | !player2.hasPieces();
    }
    /*
    public void play() throws NoPieceOnWhiteException {
        while (player1.hasPieces() & player2.hasPieces()) {
            playTurn();
        }
        if (player1.hasPieces()){
            winnerPlayer=player1;
        }else{
            winnerPlayer=player2;
        }
    }

    public void playTurn() {
        //ricevere la mossa dall'interfaccia grafica (può essere un muovi,mangia o passa il turno)
        Receive typeOfMove (can be pass, move or eat)
        if (move!=Pass){
            Receive movingPiece and targetPosition (where to move/eat)
            activePlayer.makeMove(typeOfMove,movingPiece,targetPosition)
            if (move==Eat){
            inactivePlayer.loseOnePiece();
            }
        }
        changeActivePlayer();
        currentRound++;
    }
    */
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
            inactivePlayer=player1;
        } else {
            activePlayer = player1;
            inactivePlayer=player2;
        }
        //notifyObservers();
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
    public GraphicBoard getGBoard() {
        return gBoard;
    }
    public Board getBoard() {
        return board;
    }
    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }
}
