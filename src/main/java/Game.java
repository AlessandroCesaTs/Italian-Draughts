import java.util.LinkedList;
import java.util.List;

public class Game {
    final Player player1;
    final Player player2;
    private Player activePlayer;
    private Player winnerPlayer;
    Board currentGameBoard;

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

    public void newTurn(Piece piece, BlackTile source, BlackTile destination) {
        Player inactivePlayer = activePlayer.equals(player1) ? player2 : player1;

        changeActivePlayer();
    }
    public void changeActivePlayer() {
        if (activePlayer == player1){
            activePlayer = player2;
        } else {
            activePlayer = player1;
        }
    }





}
