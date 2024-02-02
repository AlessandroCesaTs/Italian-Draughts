package logic;

import Exceptions.IllegalTilePlacementException;
import Exceptions.NoPieceOnWhiteException;

public class Game {
    final Player player1;
    final Player player2;
    private Player activePlayer;
    private Player inactivePlayer;
    private Player winnerPlayer;
    private final Board board;
    private int currentRound=1;

    public Game(String player1Name, String player2Name,Team team1,Team team2) throws IllegalTilePlacementException, NoPieceOnWhiteException {
        board = new Board();
        player1 =new Player(player1Name,team1,this);
        player2 =new Player(player2Name,team2,this);
        activePlayer= player1;
        inactivePlayer=player2;
    }

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
        /*
        Receive typeOfMove (can be pass, move or eat)
        if (move!=Pass){
            Receive movingPiece and targetPosition (where to move/eat)
            activePlayer.makeMove(typeOfMove,movingPiece,targetPosition)
            if (move==Eat){
            inactivePlayer.loseOnePiece();
            }
        }
         */

        changeActivePlayer();
        currentRound++;
    }
    public void changeActivePlayer() {
        if (activePlayer == player1){
            activePlayer = player2;
            inactivePlayer=player1;
        } else {
            activePlayer = player1;
            inactivePlayer=player2;
        }
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
}
