import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class TicTacToe {
    private static char[][] board = new char[3][3];
    private static char currentPlayer = 'X';
    private static Scanner scanner = new Scanner(System.in);

    // Queues to track the positions of each player's tokens
    private static Queue<int[]> playerXMoves = new LinkedList<>();
    private static Queue<int[]> playerOMoves = new LinkedList<>();

    public static void main(String[] args) {
        initializeBoard();
        printBoard();

        while (true) {
            playerMove();
            printBoard();

            if (isWinner()) {
                System.out.println("Player " + currentPlayer + " wins!");
                break;
            } else if (isBoardFull()) {
                System.out.println("It's a draw!");
                break;
            }

            // Switch player
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }

        scanner.close();
    }

    // Initialize the board
    public static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // Print the board
    public static void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    // Player makes a move
    public static void playerMove() {
        int row, col;

        while (true) {
            System.out.println("Player " + currentPlayer + "'s turn. Enter row (1-3) and column (1-3): ");
            row = scanner.nextInt() - 1;
            col = scanner.nextInt() - 1;

            if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
                // Place the token
                board[row][col] = currentPlayer;
                updatePlayerMoves(row, col);
                break;
            } else {
                System.out.println("This move is not valid. Try again.");
            }
        }
    }

    // Update the player's moves queue
    public static void updatePlayerMoves(int row, int col) {
        if (currentPlayer == 'X') {
            if (playerXMoves.size() == 3) {
                // Remove the oldest token from the board
                int[] oldMove = playerXMoves.poll();
                board[oldMove[0]][oldMove[1]] = ' ';
            }
            playerXMoves.add(new int[]{row, col});
        } else {
            if (playerOMoves.size() == 3) {
                // Remove the oldest token from the board
                int[] oldMove = playerOMoves.poll();
                board[oldMove[0]][oldMove[1]] = ' ';
            }
            playerOMoves.add(new int[]{row, col});
        }
    }

    // Check if current player has won
    public static boolean isWinner() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true;
            }
        }

        // Check diagonals
        if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
            (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)) {
            return true;
        }

        return false;
    }

    // Check if the board is full
    public static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
