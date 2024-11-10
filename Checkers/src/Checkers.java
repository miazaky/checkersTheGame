import java.io.IOException;
import java.util.Scanner;

public class Checkers {
    private Board board;
    private Player firstPlayer;
    private Player secondPlayer;
    private Player currentPlayer;

    public Checkers() {
        board = new Board();
        firstPlayer = new Player('O', "Player O");
        secondPlayer = new Player('X', "Player X");
        currentPlayer = firstPlayer;
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        int playerMoves = 0;
        while (true) {
            for(int i = 0; i < 50; i ++) System.out.println("\n");

            board.printBoard();
            System.out.println(currentPlayer.getPlayerName() + " turn");

            System.out.print("Enter row and column of the piece you want to move: ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            System.out.print("Enter row and column to move piece to: ");
            int newRow = scanner.nextInt();
            int newCol = scanner.nextInt();

            if (board.isValidAttack(row, col, newRow, newCol, currentPlayer.getPiece())) {
                playerMoves = 0;
                int midRow = (row + newRow) / 2;
                int midCol = (col + newCol) / 2;
                board.clearPiece(midRow, midCol);
                board.movePiece(row, col, newRow, newCol);
                if(board.canAttack(currentPlayer.getPiece()) == null) {
                    switchPlayer();
                }
            }
            else if(board.isValidMove(row, col, newRow, newCol, currentPlayer.getPiece()) && board.canAttack(currentPlayer.getPiece()) == null) {
                board.movePiece(row, col, newRow, newCol);
                playerMoves = 0;
                switchPlayer();
            }
            else if(board.isValidMove(row, col, newRow, newCol, currentPlayer.getPiece()) && board.canAttack(currentPlayer.getPiece()) != null){
                int []temp = board.canAttack(currentPlayer.getPiece());
                System.out.println("You had a chance to attack but missed it.");
                board.movePiece(row, col, newRow, newCol);
                board.clearPiece(temp[0], temp[1]);
                playerMoves = 0;
                switchPlayer();
            }
            else if(playerMoves <= 1){
                System.out.println("Invalid move");
            }
            else{
                System.out.println("Invalid move");
                switchPlayer();
            }

            if (board.isGameOver()) {
                if(currentPlayer == firstPlayer){
                    currentPlayer = secondPlayer;
                } else currentPlayer = firstPlayer;
                System.out.println(currentPlayer.getPlayerName() + " wins");
                break;
            }
            playerMoves++;
        }
        scanner.close();
    }

    private void switchPlayer() {
        if(currentPlayer == firstPlayer) {
            currentPlayer = secondPlayer;
        } else currentPlayer = firstPlayer;

    }
}
