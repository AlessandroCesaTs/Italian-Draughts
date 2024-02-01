package logic;

import Exceptions.IllegalTilePlacementException;
import Exceptions.NoPieceOnWhiteException;

public class Game {
    final Player player1;
    final Player player2;
    private Player activePlayer;
    private Player winnerPlayer;
    private final Board currentGameBoard;
    private int currentRound;

    public Game(String player1Name, String player2Name) throws IllegalTilePlacementException, NoPieceOnWhiteException {
        this.player1 =new Player(player1Name,Team.White,this);
        this.player2 =new Player(player2Name,Team.White,this);
        activePlayer= this.player1;

        this.currentGameBoard = new Board();

    }

    public void play() throws NoPieceOnWhiteException {
        while (player1.hasPieces() & player2.hasPieces()) {
            playTurn();
        }
        //alla fine metodo per chiamare il vincitore
    }

    public void playTurn() {
        //ricevere la mossa dall'interfaccia grafica (può essere un muovi,mangia o passa il turno)

        changeActivePlayer();
        currentRound++;
    }
    public void changeActivePlayer() {
        if (activePlayer == player1){
            activePlayer = player2;
        } else {
            activePlayer = player1;
        }
    }
    public Player getCurrentPlayer() {
        return activePlayer;
    }
    public int getCurrentRound(){
        return currentRound;
    }

    public Board getBoard() {
        return currentGameBoard;
    }
}
