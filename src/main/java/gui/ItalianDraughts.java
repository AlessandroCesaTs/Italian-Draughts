package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItalianDraughts extends JFrame {
    private JPanel board;
    private JButton[][] tiles;

    public ItalianDraughts() {
        setTitle("Italian Draughts");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        board = new JPanel(new GridLayout(8, 8));
        tiles = new JButton[8][8];

        // Create chess board with buttons representing squares
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tiles[i][j] = new JButton();
                board.add(tiles[i][j]);

                // Set button colors based on chess board pattern
                if ((i + j) % 2 == 0) {
                    tiles[i][j].setBackground(Color.WHITE);
                } else {
                    tiles[i][j].setBackground(new Color(255, 100, 60, 180));
                }

                // Add action listener to handle button clicks
                final int row = i;
                final int col = j;
                tiles[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        squareClicked(row, col);
                    }
                });
            }
        }

        add(board);
        setVisible(true);
    }

    private void squareClicked(int row, int col) {
        JOptionPane.showMessageDialog(this, "Square clicked: " + (char)('A' + col) + (8 - row));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ItalianDraughts();
            }
        });
    }
}
