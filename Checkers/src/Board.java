
public class Board {
    private static int SIZE = 8;
    private char[][] board;

    public Board() {
        board = new char[SIZE][SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if ((i + j) % 2 != 0 && i < 3) {
                    board[i][j] = 'X';
                } else if ((i + j) % 2 != 0 && i > 4) {
                    board[i][j] = 'O';
                } else board[i][j] = '.';

            }
        }
    }

    public void printBoard() {
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isValidMove(int row, int col, int newRow, int newCol, char player) {
        if(!isWithinBounds(row, col, newRow, newCol))
            return false;

        char piece = board[row][col];
        boolean isKing = false;
        int rowDir = -1;

        if(piece == 'W' || piece == 'Q') isKing = true;
        if(player == 'X') rowDir = 1;

        if ((!isKing && player == piece && (newRow - row == rowDir) && Math.abs(newCol - col) == 1) || (isKing && Math.abs(newRow - row) == 1 && Math.abs(newCol - col) == 1)) {
            return true;
        }
        return false;
    }

    public boolean isValidAttack(int row, int col, int newRow, int newCol, char player){
        if(!isWithinBounds(row, col, newRow, newCol))
            return false;
        char piece = board[row][col];
        int rowDir = -2;
        if(player == 'X') rowDir = 2;
        if ((piece != 'X' && piece != 'O' && Math.abs(newRow - row) == 2 && Math.abs(newCol - col) == 2) || ((piece == 'O' || player == 'X') && (newRow - row == rowDir) && Math.abs(newCol - col) == 2)) {
            int midRow = (row + newRow) / 2;
            int midCol = (col + newCol) / 2;
            char opponent;
            char opponentKing;

            if(player == 'X' || board[row][col] == 'W'){
                opponent = 'O';
                opponentKing = 'Q';
            } else{
                opponent = 'X';
                opponentKing = 'W';
            }

            if (board[midRow][midCol] == opponent || board[midRow][midCol] == opponentKing)
                return true;
        }
        return false;
    }

    public void movePiece(int row, int col, int newRow, int newCol) {
        board[newRow][newCol] = board[row][col];
        clearPiece(row, col);

        if (newRow == 0 && board[newRow][newCol] == 'O') {
            board[newRow][newCol] = 'Q';
        } else if (newRow == SIZE - 1 && board[newRow][newCol] == 'X') {
            board[newRow][newCol] = 'W';
        }
    }

    public void clearPiece(int row, int col){
        board[row][col] = '.';
    }

    public boolean isGameOver() {
        boolean playerOneExists = false;
        boolean playerTwoExists = false;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 'X' || board[i][j] == 'W') playerOneExists = true;
                if (board[i][j] == 'O' || board[i][j] == 'Q') playerTwoExists = true;
            }
        }

        return ((!playerOneExists && playerTwoExists) || (playerOneExists && !playerTwoExists));
    }

    public boolean isWithinBounds(int row, int col, int newRow, int newCol){
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE || board[newRow][newCol] != '.')
            return false;
        else
            return true;
    }

    public int[] canAttack(char player) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {

                if ((player == 'X' && (board[row][col] == 'X' || board[row][col] == 'W')) || (player == 'O' && (board[row][col] == 'O' || board[row][col] == 'Q'))) {
                    int[] rowPossibilities = {-2, -2, 2, 2};
                    int[] colPossibilities = {-2, 2, -2, 2};

                    for (int i = 0; i < 4; i++) {
                        int newRow = row + rowPossibilities[i];
                        int newCol = col + colPossibilities[i];
                        if (isValidAttack(row, col, newRow, newCol, player)) {
                            return new int[]{row, col};
                        }
                    }
                }
            }
        }
        return null;
    }
}
