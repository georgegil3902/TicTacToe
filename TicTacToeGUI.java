import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons = new JButton[3][3];  // 3x3 grid of buttons
    private char currentPlayer = 'X';  // 'X' starts
    private Queue<int[]> playerXMoves = new LinkedList<>();
    private Queue<int[]> playerOMoves = new LinkedList<>();
    private JLabel statusLabel;  // Label to display current player and winner
    private JButton resetButton;  // Button to reset the board

    public TicTacToeGUI() {
        setTitle("Tic-Tac-Toe");
        setSize(400, 500);  // Adjusted size to fit label and reset button
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize label to show player turn
        statusLabel = new JLabel("Player X's turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(statusLabel, BorderLayout.NORTH);

        // Create the game board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));  // 3x3 grid layout for buttons
        initializeBoard(boardPanel);
        add(boardPanel, BorderLayout.CENTER);

        // Create the reset button
        resetButton = new JButton("Reset Game");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 20));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBoard();  // Call resetBoard when the reset button is clicked
            }
        });
        add(resetButton, BorderLayout.SOUTH);  // Add the reset button to the bottom

        setVisible(true);
    }

    // Initialize the board with buttons
    private void initializeBoard(JPanel boardPanel) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton(" ");  // Initialize empty buttons
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                boardPanel.add(buttons[row][col]);  // Add button to the board panel
            }
        }
    }

    // Handle button click
    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().equals(" ")) {  // Check if the cell is empty
                buttons[row][col].setText(String.valueOf(currentPlayer));  // Set player token
                updatePlayerMoves(row, col);  // Update moves for current player

                if (isWinner()) {  // Check for a winner
                    statusLabel.setText("Player " + currentPlayer + " wins!");
                    disableBoard();  // Disable board after win
                } else {
                    // Switch players
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    statusLabel.setText("Player " + currentPlayer + "'s turn");
                }
            }
        }
    }

    // Update the player's moves queue
    private void updatePlayerMoves(int row, int col) {
        if (currentPlayer == 'X') {
            if (playerXMoves.size() == 3) {
                // Remove the oldest token from the board
                int[] oldMove = playerXMoves.poll();
                buttons[oldMove[0]][oldMove[1]].setText(" ");
            }
            playerXMoves.add(new int[]{row, col});
        } else {
            if (playerOMoves.size() == 3) {
                // Remove the oldest token from the board
                int[] oldMove = playerOMoves.poll();
                buttons[oldMove[0]][oldMove[1]].setText(" ");
            }
            playerOMoves.add(new int[]{row, col});
        }
    }

    // Check if the current player has won
    private boolean isWinner() {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][i].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }

        // Check diagonals
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }

        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][0].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }

        return false;
    }

    // Disable the board after the game is won
    private void disableBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setEnabled(false);  // Disable all buttons
            }
        }
    }

    // Reset the board for a new game
    private void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(" ");
                buttons[row][col].setEnabled(true);
            }
        }
        playerXMoves.clear();
        playerOMoves.clear();
        currentPlayer = 'X';
        statusLabel.setText("Player X's turn");
    }

    // Main method to launch the GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeGUI());
    }
}
