package Main;

import Exceptions.IllegalTeamsCompositionException;
import Exceptions.IllegalTilePlacementException;
import Exceptions.NoPieceOnWhiteException;
import gui.GraphicBoard;
import logic.Game;
import logic.Player;
import logic.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(680, 680));
        frame.setLocationRelativeTo(null);

        GraphicBoard gBoard = new GraphicBoard();
        frame.add(gBoard);

        JButton newGame = new JButton("New Game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player player1 = null;
                try {
                    player1 = new Player("Giorgio", Team.White);
                } catch (NoPieceOnWhiteException ex) {
                    throw new RuntimeException(ex);
                }
                Player player2 = null;
                try {
                    player2 = new Player("Franco", Team.Black);
                } catch (NoPieceOnWhiteException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    Game game = new Game(player1, player2);
                } catch (IllegalTeamsCompositionException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalTilePlacementException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        frame.add(newGame, BorderLayout.PAGE_START);

        frame.setVisible(true);
    }
}