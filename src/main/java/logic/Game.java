package logic;

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
    private boolean gameOver = false;
    private final Board board;
    private GraphicBoard gBoard;
    private int currentRound = 1;
    private int roundsWithoutEating = 0;
    private final List<GameObserver> observers = new ArrayList<>();
    private int consecutiveEating;
    private final MultiplayerActions multiRole;


    public Game(String player1Name, String player2Name, Team team1, Team team2) {
        board = new Board();
        player1 = new Player(player1Name, team1, this, Role.NULL);
        player2 = new Player(player2Name, team2, this, Role.NULL);
        activePlayer = player1;
        inactivePlayer = player2;
        multiRole = null;
    }

    public Game(String playerName, Team team, String hostIPField) throws Exception {
        switch (playerName) {
            case "Host" -> {
                board = new Board();
                player1 = new Player(playerName, team, this, Role.HOST);
                player2 = new Player("Guest", Team.BLACK, this, Role.GUEST);
                activePlayer = player1;
                inactivePlayer = player2;
                multiRole = new Host(this);
                multiRole.connect();
            }
            case "Guest" -> {
                board = new Board();
                player1 = new Player(playerName, team, this, Role.GUEST);
                player2 = new Player("Host", Team.WHITE, this, Role.HOST);
                activePlayer = player2;
                inactivePlayer = player1;
                multiRole = new Guest(hostIPField, this);
                multiRole.connect();
            }
            default -> throw new Exception("Something has gone wrong!");
        }
    }

    @Override
    public void onMoveMade() {
        Move move = gBoard.getMoveFromGUI();
        playTurn(move);
    }

    @Override
    public void playTurn(Move move) {
        TypeOfMove typeOfMove = move.getTypeOfMove();
        if (typeOfMove != TypeOfMove.NO_MOVE) {
            Piece movingPiece = move.getPiece();
            NeighborPosition targetPosition = move.getDestination();
            if (typeOfMove.equals(TypeOfMove.EAT)) {
                eat(movingPiece, targetPosition);
                if (checkMultipleEating(movingPiece) && consecutiveEating <= 3) {
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
        if (multiRole != null && activePlayer.getRole() == player1.getRole()) {
            Point startTile = gBoard.getStartTile();
            Point endTile = gBoard.getEndTile();
            multiRole.sendMove(startTile, endTile, messageType);
        }
    }

    @Override
    public void Move(Piece movingPiece, NeighborPosition targetPosition) {
        activePlayer.makeMove(TypeOfMove.MOVE, movingPiece, targetPosition);
        gBoard.movePiece(movingPiece, targetPosition);
        checkPromotion(movingPiece);
        roundsWithoutEating++;
    }

    @Override
    public void eat(Piece movingPiece, NeighborPosition targetPosition) {
        gBoard.eatPiece(movingPiece, targetPosition);
        Piece eatenPiece = movingPiece.getTile().getNeighbor(targetPosition).getPiece();
        activePlayer.makeMove(TypeOfMove.EAT, movingPiece, targetPosition);

        inactivePlayer.loseOnePiece(eatenPiece);
        checkPromotion(movingPiece);
        roundsWithoutEating = 0;
        consecutiveEating++;
    }


    @Override
    public void checkGameOver() {
        if (inactivePlayer.doesntHavePieces() || !inactivePlayer.canMove()) {
            gameOver = true;
            sendMove(2);
        } else if (roundsWithoutEating == 40) {
            gameOver = true;
            sendMove(2);
        }
    }

    @Override
    public boolean checkMultipleEating(Piece movingPiece) {
        if (movingPiece.canEatAnotherPiece()) {
            return true;
        } else {
            consecutiveEating = 0;
            return false;
        }
    }

    public void checkPromotion(Piece movingPiece) {
        if (movingPiece.pieceHasToBePromoted()) {
            gBoard.getGraphicPiece(movingPiece).promote();
        }
    }


    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public void changeActivePlayer() {
        if (activePlayer == player1) {
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
    public int getCurrentRound() {
        return currentRound;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

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
    public Team getActiveTeam() {
        return activePlayer.getTeam();
    }

    @Override
    public Piece getPiece(int row, int col) {
        return board.getPiece(row, col);
    }

    @Override
    public List<BlackTile> getFullBlackTiles() {
        return board.getFullBlackTiles();
    }

}
