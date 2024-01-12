import Exceptions.IllegalTeamsCompositionException;
import Exceptions.IllegalTilePlacementException;
import Exceptions.NoPieceOnWhiteException;

public class Game {
    final Player player1;
    final Player player2;
    private Player activePlayer;
    private Player winnerPlayer;
    private static Board currentGameBoard;
    private int currentRound;

    public Game(Player player1, Player player2) throws IllegalTeamsCompositionException, IllegalTilePlacementException {
        if (player1.team == player2.team) {
            throw new IllegalTeamsCompositionException();
        }
        this.player1 = player1;
        this.player2 = player2;

        if (player1.team == Team.White){
            this.activePlayer = player1;
        } else {
            this.activePlayer = player2;
        }

        this.currentGameBoard = new Board();

    }

    public void play() throws NoPieceOnWhiteException {
        while (player1.hasPieces() & player2.hasPieces()) {
            playTurn();
        }
        //alla fine metodo per chiamare il vincitore
    }

    public void playTurn() {
        //metodo per ricevere mossa del giocatore
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

    public static Board getBoard() {
        return currentGameBoard;
    }
}
