import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TicTacToeGame extends JFrame {
    private JButton[][] buttons;
    private boolean playerX;
    private boolean gameOver;
    private JPanel buttonPanel;
    private JButton replayButton;

    public TicTacToeGame() {
        super("Tic-Tac-Toe");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBounds(0, 0, 300, 300);
        add(buttonPanel);

        replayButton = new JButton("Replay");
        replayButton.setBounds(100, 310, 100, 30);
        replayButton.setEnabled(false);
        replayButton.addActionListener(new ReplayButtonListener());
        add(replayButton);

        buttons = new JButton[3][3];
        playerX = true;
        gameOver = false;

        initializeButtons();
    }

    private void initializeButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
                buttons[row][col].addActionListener(new ButtonListener(row, col));
                buttonPanel.add(buttons[row][col]);
            }
        }
    }

    private class ButtonListener implements ActionListener {
        private int row;
        private int col;

        public ButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            if (gameOver || buttons[row][col].getText().length() > 0) {
                return;
            }

            if (playerX) {
                buttons[row][col].setText("X");
            } else {
                buttons[row][col].setText("O");
            }

            playerX = !playerX;
            checkGameStatus();
        }
    }

    private void checkGameStatus() {
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (checkRow(row)) {
                showWinner(row, 0, row, 2);
                return;
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (checkColumn(col)) {
                showWinner(0, col, 2, col);
                return;
            }
        }

        // Check diagonals
        if (checkDiagonal1()) {
            showWinner(0, 0, 2, 2);
            return;
        }

        if (checkDiagonal2()) {
            showWinner(0, 2, 2, 0);
            return;
        }

        // Check for a tie
        if (isBoardFull()) {
            showTie();
            return;
        }
    }

    private boolean checkRow(int row) {
        String symbol = buttons[row][0].getText();
        return !symbol.isEmpty() && buttons[row][1].getText().equals(symbol) && buttons[row][2].getText().equals(symbol);
    }

    private boolean checkColumn(int col) {
        String symbol = buttons[0][col].getText();
        return !symbol.isEmpty() && buttons[1][col].getText().equals(symbol) && buttons[2][col].getText().equals(symbol);
    }

    private boolean checkDiagonal1() {
        String symbol = buttons[0][0].getText();
        return !symbol.isEmpty() && buttons[1][1].getText().equals(symbol) && buttons[2][2].getText().equals(symbol);
    }

    private boolean checkDiagonal2() {
        String symbol = buttons[0][2].getText();
        return !symbol.isEmpty() && buttons[1][1].getText().equals(symbol) && buttons[2][0].getText().equals(symbol);
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showWinner(int startRow, int startCol, int endRow, int endCol) {
        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                buttons[row][col].setBackground(Color.GREEN);
            }
        }
        gameOver = true;
        String winner = playerX ? "O" : "X";
        JOptionPane.showMessageDialog(this, "Player " + winner + " wins!");
        replayButton.setEnabled(true);
    }

    private void showTie() {
        gameOver = true;
        JOptionPane.showMessageDialog(this, "It's a tie!");
        replayButton.setEnabled(true);
    }

    private class ReplayButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            resetGame();
        }
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setBackground(null);
            }
        }
        playerX = true;
        gameOver = false;
        replayButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TicTacToeGame game = new TicTacToeGame();
                game.setVisible(true);
            }
        });
    }
}
