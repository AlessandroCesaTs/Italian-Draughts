package Main;

import Exceptions.IllegalTeamsCompositionException;
import Exceptions.IllegalTilePlacementException;
import Exceptions.NoPieceOnWhiteException;
import gui.GraphicBoard;
import logic.Game;
import logic.Player;
import logic.Team;
import observers.GameObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class Main implements GameObserver {
    private static GraphicBoard gBoard;
    private static JLabel gameLabel;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(gameInitiation());
    }
    public static Runnable gameInitiation() {
        return () -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setBackground(new Color(240, 236, 236));
            frame.setLayout(new GridBagLayout());
            frame.setMinimumSize(new Dimension(800, 850));
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            Game placeholderGame = null;
            try {
                placeholderGame = new Game("Player1", "Player2", Team.White, Team.Black);
            } catch (IllegalTilePlacementException e) {
                throw new RuntimeException(e);
            } catch (NoPieceOnWhiteException e) {
                throw new RuntimeException(e);
            } catch (IllegalTeamsCompositionException e) {
                throw new RuntimeException(e);
            }
            gBoard = new GraphicBoard(placeholderGame);

            JLabel playersLabel = new JLabel();
            gameLabel = new JLabel();
            Dimension labelSize = new Dimension(frame.getWidth(), 25);
            playersLabel.setPreferredSize(labelSize);
            gameLabel.setPreferredSize(labelSize);
            playersLabel.setForeground(Color.BLACK);
            gameLabel.setForeground(Color.BLACK);

            JButton newGameButton = new JButton("New Game");
            newGameButton.addActionListener(e -> {
                JTextField player1NameField = new JTextField();
                JTextField player2NameField = new JTextField();
                JComboBox<Team> player1TeamField = new JComboBox<>(Team.values());
                JComboBox<Team> player2TeamField = new JComboBox<>(Team.values());

                player1TeamField.setSelectedItem(Team.White);
                player2TeamField.setSelectedItem(Team.Black);

                player1TeamField.addItemListener(event -> {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        player2TeamField.setSelectedItem(player1TeamField.getSelectedItem() == Team.White ? Team.Black : Team.White);
                    }
                });

                player2TeamField.addItemListener(event -> {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        player1TeamField.setSelectedItem(player2TeamField.getSelectedItem() == Team.White ? Team.Black : Team.White);
                    }
                });

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Enter Player 1 Name:"));
                panel.add(player1NameField);
                panel.add(new JLabel("Enter Player 1 Team:"));
                panel.add(player1TeamField);
                panel.add(new JLabel("Enter Player 2 Name:"));
                panel.add(player2NameField);
                panel.add(new JLabel("Enter Player 2 Team:"));
                panel.add(player2TeamField);

                int result = JOptionPane.showConfirmDialog(frame, panel, "Start New Game", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String player1Name = player1NameField.getText();
                    Team player1Team = (Team) player1TeamField.getSelectedItem();
                    String player2Name = player2NameField.getText();
                    Team player2Team = (Team) player2TeamField.getSelectedItem();

                    playersLabel.setText(player1Name + " with " + player1Team + "s" +
                                         "; " + player2Name + " with " + player2Team + "s" );

                    Game game = null;
                    try {
                        game = new Game(player1Name, player2Name, player1Team, player2Team);
                        frame.remove(gBoard);
                        gBoard = new GraphicBoard(game);
                        game.addObserver(new Main());
                        frame.add(gBoard);
                    } catch (IllegalTilePlacementException ex) {
                        throw new RuntimeException(ex);
                    } catch (NoPieceOnWhiteException ex) {
                        throw new RuntimeException(ex);
                    } catch (IllegalTeamsCompositionException ex) {
                        throw new RuntimeException(ex);
                    }
                    gameLabel.setText("Turn " + game.getCurrentRound() + ", active player: " + game.getActivePlayer().getName());
                }
            });

            JButton multiplayerButton = new JButton("Multiplayer");
            multiplayerButton.addActionListener(e -> {
                JTextField player1NameField = new JTextField();
                JTextField player2NameField = new JTextField();

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Enter Player 1 Name:"));
                panel.add(player1NameField);
                panel.add(new JLabel("Enter Player 2 Name:"));
                panel.add(player2NameField);

                int result = JOptionPane.showConfirmDialog(frame, panel, "Start New Game", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String player1Name = player1NameField.getText();
                    String player2Name = player2NameField.getText();

                    // Code for multiplayer
                    gBoard.resetBoard();
                }
            });

            JPanel buttonPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            buttonPanel.add(newGameButton, gbc);
            buttonPanel.add(multiplayerButton, gbc);

            gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            frame.add(buttonPanel, gbc);
            frame.add(gameLabel, gbc);

            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1;
            gbc.weighty = 1;
            frame.add(playersLabel, gbc);
            frame.add(gBoard, gbc);


            frame.pack();
            frame.setVisible(true);
        };
    }
    @Override
    public void update(Game game) {
        SwingUtilities.invokeLater(() -> {
            gameLabel.setText("Turn " + game.getCurrentRound() + ", active player: " + game.getActivePlayer().getName());
        });
    }
}
