package logic;

import Exceptions.*;
import gui.GraphicBoard;
import observers.GameObserver;
import observers.MoveMadeObserver;

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
    private List<GameObserver> observers = new ArrayList<>();
    private int consecutiveEatings;

    public Game(String player1Name, String player2Name,Team team1,Team team2) throws IllegalTilePlacementException, NoPieceOnWhiteException, IllegalTeamsCompositionException, CantEatException, IllegalMovementException, OutOfBoundsException, NotOnDiagonalException {
        if (team1.equals(team2)){ //questa in teoria non può verificarsi perchè nella gui se uno sceglie un team l'altro cambia automaticamente
            throw new IllegalTeamsCompositionException();
        }
        board = new Board();
        player1 =new Player(player1Name,team1,this);
        player2 =new Player(player2Name,team2,this);
        activePlayer= player1;
        inactivePlayer=player2;
        //gBoard=new GraphicBoard(this);
        //startGame(); //sostituirei con play() il metodo startGame() che fa quello che vedi sotto
        //rimane da aggiungere caso game over
    }
    /*
    public void startGame(){
       Thread gameThread = new Thread(() -> { //nuovo thread per non bloccare il programma
           while (gBoard == null){ //aspetta che la gBoard sia stata inizializzata in GraphicBoard
                try {
                     Thread.sleep(100);
                } catch (InterruptedException e) {
                     Thread.currentThread().interrupt();
                }
           }
           while (!isGameOver()){
               waitForMove();
           }
       });
       gameThread.start();
    }

     */
    @Override
    public void onMoveMade() throws NotOnDiagonalException, CantEatException, IllegalMovementException, OutOfBoundsException {
        playTurn();
        if(winnerPlayer!=null){
            System.out.println("Winner player is" + winnerPlayer);
        }
    }

    public void play() throws NoPieceOnWhiteException, CantEatException, IllegalMovementException, OutOfBoundsException, NotOnDiagonalException {
        while (player1.hasPieces() & player2.hasPieces()) {
            playTurn();
        }

    }

    public void playTurn() throws CantEatException, IllegalMovementException, OutOfBoundsException, NotOnDiagonalException {
        //ricevere la mossa dall'interfaccia grafica (può essere un muovi,mangia o passa il turno)
        Move move= gBoard.getMoveFromGUI();
        TypeOfMove typeOfMove=move.getTypeOfMove();
        //System.out.println(typeOfMove);
        if (move.getTypeOfMove()!=TypeOfMove.NoMove){
            Piece movingPiece=move.getPiece();
            NeighborPosition targetPosition=move.getDestination();
            if (move.getTypeOfMove().equals(TypeOfMove.Eat)){
                gBoard.eatPiece(movingPiece,targetPosition);
                Piece eatenPiece=movingPiece.getTile().getNeighbor(targetPosition).getPiece();
                activePlayer.makeMove(typeOfMove,movingPiece,targetPosition);

                inactivePlayer.loseOnePiece(eatenPiece);
                checkPromotion(movingPiece);
                roundsWithoutEating=0;
                consecutiveEatings++;
                if(checkMultipleEating(movingPiece) && consecutiveEatings <=3) {
                    return; // se può mangiare ancora e non ha mangiato più di 3 pezzi esce dal metodo senza cambiare giocatore
                }
            }else {
                activePlayer.makeMove(typeOfMove,movingPiece,targetPosition);
                gBoard.movePiece(movingPiece,targetPosition);
                checkPromotion(movingPiece);
                roundsWithoutEating++;
            }
            checkGameOver();
            currentRound++;
            changeActivePlayer();
        }


        //System.out.println(player1.getNumberOfPieces());
        //System.out.println(player2.getNumberOfPieces());
        //gBoard.debugPieces();

    }

    private void checkGameOver() throws IllegalMovementException, OutOfBoundsException {
        if (!inactivePlayer.hasPieces() || !inactivePlayer.canMove()){
            winnerPlayer=activePlayer;
            gameOver=true;
        }else if(roundsWithoutEating==40){
            gameOver=true;
        }
    }

    private boolean checkMultipleEating(Piece movingPiece) throws OutOfBoundsException {
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
            inactivePlayer=player1;
        } else {
            activePlayer = player1;
            inactivePlayer=player2;
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
    public GraphicBoard getGBoard() {
        return gBoard;
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
}
