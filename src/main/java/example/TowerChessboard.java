package example;

public class TowerChessboard {
    private int[][] board;
    private int size;

    public int[][] getBoard() {
        int[][] copy = new int[board.length][];
        for (int i = 0; i < board.length; i++) {
            copy[i] = board[i].clone();
        }
        return copy;
    }



    public TowerChessboard(int size) {
        this.size = Math.max(size, 8);
        this.board = new int[this.size][this.size];
    }

    public void print() {
        for (int[] row : board) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    public boolean isValidPosition(int row, int column) {
        return row >= 0 && row < size && column >= 0 && column < size;
    }

    public void put(int row, int column) {
        if (board[row][column] == 9) {
            throw new IllegalArgumentException("Tower already present at this position.");
        }
        board[row][column] = 9;
    }

    public void update() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 9) continue;
                board[i][j] = countTowers(i, j);
            }
        }
    }

    private int countTowers(int row, int column) {
        int count = 0;

        // Left
        for (int i = column - 1; i >= 0; i--) {
            if (board[row][i] == 9) {
                count++;
                break;
            }
        }

        // Right
        for (int i = column + 1; i < size; i++) {
            if (board[row][i] == 9) {
                count++;
                break;
            }
        }

        // Up
        for (int i = row - 1; i >= 0; i--) {
            if (board[i][column] == 9) {
                count++;
                break;
            }
        }

        // Down
        for (int i = row + 1; i < size; i++) {
            if (board[i][column] == 9) {
                count++;
                break;
            }
        }

        return count;
    }
}
