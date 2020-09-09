import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Omar.A.K
 */
public class Board {
    private int[][] board;
    private Board[] neighbors;

    public Board(int[][] tiles) {
        this.board = copy(tiles);
    }

    private int[][] copy(int[][] arrayToCopy) {
        int[][] copy = new int[arrayToCopy.length][];
        for (int r = 0; r < arrayToCopy.length; r++) {
            copy[r] = arrayToCopy[r].clone();
        }
        return copy;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(board.length);
        for (int i = 0; i < board.length; i++) {
            str.append("\n");
            for (int j = 0; j < board[0].length; j++) {
                str.append(" ");
                str.append(board[i][j]);
            }

        }
        return str.toString();
    }

    public int dimension() {
        return board.length;
    }

    public int hamming() {
        int count = 0, hamdis = 0;

        for (int i = 0; i < board.length - 1; i++) {
            for (int j = 0; j < board[0].length; j++) {
                count++;
                if (board[i][j] != count) {
                    hamdis++;
                }

            }


        }
        return hamdis;
    }

    private int mandis;

    public int manhattan() {
        int res = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int exp = i * board.length + j + 1;
                if (board[i][j] != exp && board[i][j] != 0) {

                    int actual = board[i][j];
                    actual--;
                    int goali = actual / dimension();
                    int goalj = actual % dimension();
                    res += Math.abs(goali - i) + Math.abs(goalj - j);
                }
            }
        }
        mandis = res;
        return res;
    }

    public boolean equals(Object y) {
        String x = y.toString();
        String z = board.toString();
        int leny = x.charAt(0);
        int lenz = z.charAt(0);
        if (leny != lenz) {
            return false;
        }
        for (int i = 0; i < leny; i++) {
            if (x.charAt(i) != z.charAt(i)) {
                return false;
            }
        }
        return true;

    }

    public boolean isGoal() {
        return (mandis == 0);
    }

    private void exchangeBlocks(int[][] blocks, int iFirstBlock, int jFirstBlock, int iSecondsBlock,
                                int jSecondBlock) {
        int firstValue = blocks[iFirstBlock][jFirstBlock];
        blocks[iFirstBlock][jFirstBlock] = blocks[iSecondsBlock][jSecondBlock];
        blocks[iSecondsBlock][jSecondBlock] = firstValue;
    }

    public Board twin() {                   // a board that is obtained by exchanging any pair of tiles
        int[][] twinBlocks = copy(board);

        int i = 0;
        int j = 0;
        while (twinBlocks[i][j] == 0 || twinBlocks[i][j + 1] == 0) {
            j++;
            if (j >= twinBlocks.length - 1) {
                i++;
                j = 0;
            }
        }

        exchangeBlocks(twinBlocks, i, j, i, j + 1);
        return new Board(twinBlocks);
    }

    private void findNeighbors() {
        List<Board> foundNeighbors = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (board[i][j] != 0) {
            j++;
            if (j >= dimension()) {
                i++;
                j = 0;
            }
        }

        if (i > 0) {
            int[][] neighborTiles = copy(board);
            exchangeBlocks(neighborTiles, i - 1, j, i, j);
            foundNeighbors.add(new Board(neighborTiles));
        }
        if (i < dimension() - 1) {
            int[][] neighborTiles = copy(board);
            exchangeBlocks(neighborTiles, i, j, i + 1, j);
            foundNeighbors.add(new Board(neighborTiles));
        }
        if (j > 0) {
            int[][] neighborTiles = copy(board);
            exchangeBlocks(neighborTiles, i, j - 1, i, j);
            foundNeighbors.add(new Board(neighborTiles));
        }
        if (j < dimension() - 1) {
            int[][] neighborTiles = copy(board);
            exchangeBlocks(neighborTiles, i, j, i, j + 1);
            foundNeighbors.add(new Board(neighborTiles));
        }

        neighbors = foundNeighbors.toArray(new Board[foundNeighbors.size()]);
    }

    public Iterable<Board> neighbors() {    // all neighboring boards
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                if (neighbors == null) {
                    findNeighbors();
                }
                return new NeighborIterator();
            }
        };
    }

    private class NeighborIterator implements Iterator<Board> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < neighbors.length;
        }

        @Override
        public Board next() {
            if (hasNext()) {
                return neighbors[index++];
            }
            else {
                throw new NoSuchElementException("There is no next neighbor.");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removal of neighbors not supported.");
        }
    }

}
