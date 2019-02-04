import java.util.Random;

class Board {

    private int[][] board;

    Board(int size) {
        board = new int[size][size];
    }

    int getField(int x, int y) {
        return board[x][y];
    }

    int getSize() {
        return board.length;
    }

    void initBoard(double density) {
        Random random = new Random();

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++)
                if (random.nextDouble() > density)
                    board[x][y] = 1;
        }
    }

    void nextPopulation() {
        int[][] newBoard = new int[board.length][board.length];
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                newBoard[x][y] = getField(x, y);
                checkBoard(x, y, newBoard);
            }
        }

        board = newBoard;
    }

    private void checkBoard(int x, int y, int[][] newBoard) {
        int[] xIndex = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] yIndex = {1, 1, 1, 0, 0, -1, -1, -1};

        int fieldVal = board[x][y];
        int neighbours = 0;

        for (int i = 0; i < 8; i++)
            if(x+xIndex[i]>=0 && y+yIndex[i]>=0 && x+xIndex[i]<board.length && y+yIndex[i]<board.length)
                neighbours = neighbours + getField(x+ xIndex[i], y+yIndex[i]);

        if (fieldVal == 0 && neighbours == 3) {
            newBoard[x][y] =1;
            return;
        }

        if (fieldVal == 1 && neighbours < 2) {
            newBoard[x][y] = 0;
            return;
        }

        if (fieldVal == 1 && neighbours == 2 || neighbours == 3) return;

        if (fieldVal == 1) {
            newBoard[x][y] = 0;
        }
    }
}
