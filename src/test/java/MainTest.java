import example.TowerChessboard;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MainTest {

    // in tests figure (Tower) is represented by number 9

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testTowerPlacementSamePlace() {
        TowerChessboard board = new TowerChessboard(12);
        board.put(3, 4);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Tower already present at this position.");
        board.put(3, 4);
    }

    @Test
    public void testTowerPlacementSameRow() {
        TowerChessboard board = new TowerChessboard(8);
        board.put(3, 4);
        board.put(3, 6);
        board.update();

        int[][] grid = board.getBoard();
        assertThat(grid[3][4], is(9));
        assertThat(grid[3][6], is(9));
        assertThat(grid[3][5], is(2));

//            0 0 0 0 1 0 1 0
//            0 0 0 0 1 0 1 0
//            0 0 0 0 1 0 1 0
//            1 1 1 1 9 2 9 1
//            0 0 0 0 1 0 1 0
//            0 0 0 0 1 0 1 0
//            0 0 0 0 1 0 1 0
//            0 0 0 0 1 0 1 0
    }


    @Test
    public void testTowerPlacementInBetweenTowers() {
        TowerChessboard board = new TowerChessboard(8);
        board.put(3, 2);
        board.put(3, 6);
        board.put(0, 3);
        board.update();

        board.put(3, 4);
        board.update();

        int[][] grid = board.getBoard();
        assertThat(grid[3][4], is(9));
        assertThat(grid[3][3], is(3));
        assertThat(grid[3][5], is(2));

//        1 1 2 9 2 1 2 1
//        0 0 1 1 1 0 1 0
//        0 0 1 1 1 0 1 0
//        1 1 9 3 9 2 9 1
//        0 0 1 1 1 0 1 0
//        0 0 1 1 1 0 1 0
//        0 0 1 1 1 0 1 0
//        0 0 1 1 1 0 1 0
    }

    @Test
    public void testTowerPlacementIrregularOrder() {
        TowerChessboard board = new TowerChessboard(8);
        board.put(7, 3);
        board.put(0, 2);
        board.put(4, 5);

        int[][] grid = board.getBoard();
        assertThat(grid[7][3], is(9));
        assertThat(grid[0][2], is(9));
        assertThat(grid[4][5], is(9));

//        1 1 9 2 1 2 1 1
//        0 0 1 1 0 1 0 0
//        0 0 1 1 0 1 0 0
//        0 0 1 1 0 1 0 0
//        1 1 2 2 1 9 1 1
//        0 0 1 1 0 1 0 0
//        0 0 1 1 0 1 0 0
//        1 1 2 9 1 2 1 1
    }

    @Test
    public void testTowerPlacementAroundPoint() {
        TowerChessboard board = new TowerChessboard(8);

        board.put(2, 4);
        board.put(3, 5);
        board.put(4, 4);
        board.put(3, 3);
        board.update();

        int[][] grid = board.getBoard();
        assertThat(grid[2][4], is(9));
        assertThat(grid[3][5], is(9));
        assertThat(grid[4][4], is(9));
        assertThat(grid[3][3], is(9));
        assertThat(grid[3][4], is(4));


//        0 0 0 1 1 1 0 0
//        0 0 0 1 1 1 0 0
//        1 1 1 2 9 2 1 1
//        1 1 1 9 4 9 1 1
//        1 1 1 2 9 2 1 1
//        0 0 0 1 1 1 0 0
//        0 0 0 1 1 1 0 0
//        0 0 0 1 1 1 0 0
    }

    @Test
    public void testTowerPlacementBehindMultipleTowers() {
        TowerChessboard board = new TowerChessboard(8);
        board.put(2, 2);
        board.put(3, 2);
        board.put(4, 2);

        board.put(0, 2);
        board.update();

        int[][] grid = board.getBoard();
        assertThat(grid[1][2], is(2));
        assertThat(grid[0][2], is(9));
        assertThat(grid[5][2], is(1));
        assertThat(grid[1][1], is(0));

//        1 1 9 1 1 1 1 1
//        0 0 2 0 0 0 0 0
//        1 1 9 1 1 1 1 1
//        1 1 9 1 1 1 1 1
//        1 1 9 1 1 1 1 1
//        0 0 1 0 0 0 0 0
//        0 0 1 0 0 0 0 0
//        0 0 1 0 0 0 0 0
    }

    @Test
    public void testTowerMultipleUpdate() {
        TowerChessboard board = new TowerChessboard(8);
        board.put(2, 2);
        board.put(3, 3);
        board.put(4, 4);
        board.put(3, 1);

//        0 1 1 1 1 0 0 0
//        0 1 1 1 1 0 0 0
//        1 2 9 2 2 1 1 1
//        1 9 3 9 2 1 1 1
//        1 2 2 2 9 1 1 1
//        0 1 1 1 1 0 0 0
//        0 1 1 1 1 0 0 0
//        0 1 1 1 1 0 0 0


        for (int i = 0; i < 100; i++) {
            board.update();
        }

        int[][] grid = board.getBoard();
        assertThat(grid[3][1], is(9));
        assertThat(grid[3][2], greaterThan(0));

//        0 1 1 1 1 0 0 0
//        0 1 1 1 1 0 0 0
//        1 2 9 2 2 1 1 1
//        1 9 3 9 2 1 1 1
//        1 2 2 2 9 1 1 1
//        0 1 1 1 1 0 0 0
//        0 1 1 1 1 0 0 0
//        0 1 1 1 1 0 0 0

    }

    @Test
    public void testTowerPlacementBeyondBoardSize() {
        TowerChessboard board = new TowerChessboard(8);

        thrown.expect(ArrayIndexOutOfBoundsException.class);
        board.put(-1, 0);
        board.update();

        int[][] grid = board.getBoard();
        assertThat(grid[3][3], is(0));
        assertThat(grid[0][0], is(0));
        assertThat(grid[0][7], is(0));
        assertThat(grid[7][0], is(0));
        assertThat(grid[7][7], is(0));


//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
    }

    @Test
    public void testTowerPlacementBeyondBoardSize1() {
        TowerChessboard board = new TowerChessboard(8);

        thrown.expect(ArrayIndexOutOfBoundsException.class);
        board.put(-1, 9);
        board.update();

        int[][] grid = board.getBoard();
        assertThat(grid[3][3], is(0));
        assertThat(grid[0][0], is(0));
        assertThat(grid[0][7], is(0));
        assertThat(grid[7][0], is(0));
        assertThat(grid[7][7], is(0));


//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
    }

    @Test
    public void testTowerPlacementBeyondBoardSize2() {
        TowerChessboard board = new TowerChessboard(8);

        thrown.expect(ArrayIndexOutOfBoundsException.class);
        board.put(10, 10);
        board.update();

        int[][] grid = board.getBoard();
        assertThat(grid[3][3], is(0));
        assertThat(grid[0][0], is(0));
        assertThat(grid[0][7], is(0));
        assertThat(grid[7][0], is(0));
        assertThat(grid[7][7], is(0));


//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
    }

    @Test
    public void testTowerPlacementBeyondBoardSize3() {
        TowerChessboard board = new TowerChessboard(8);

        thrown.expect(ArrayIndexOutOfBoundsException.class);
        board.put(-1, -1);
        board.update();

        int[][] grid = board.getBoard();
        assertThat(grid[3][3], is(0));
        assertThat(grid[0][0], is(0));
        assertThat(grid[0][7], is(0));
        assertThat(grid[7][0], is(0));
        assertThat(grid[7][7], is(0));


//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
    }
    @Test
    public void testTowerPlacementBeyondBoardSize4() {
        TowerChessboard board = new TowerChessboard(8);

        thrown.expect(ArrayIndexOutOfBoundsException.class);
        board.put(0, 8);
        board.update();

        int[][] grid = board.getBoard();
        assertThat(grid[3][3], is(0));
        assertThat(grid[0][0], is(0));
        assertThat(grid[0][7], is(0));
        assertThat(grid[7][0], is(0));
        assertThat(grid[7][7], is(0));


//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
    }
    @Test
    public void testTowerPlacementBeyondBoardSize5() {
        TowerChessboard board = new TowerChessboard(8);

        thrown.expect(ArrayIndexOutOfBoundsException.class);
        board.put(8, 0);
        board.update();

        int[][] grid = board.getBoard();
        assertThat(grid[3][3], is(0));
        assertThat(grid[0][0], is(0));
        assertThat(grid[0][7], is(0));
        assertThat(grid[7][0], is(0));
        assertThat(grid[7][7], is(0));


//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
    }
    @Test
    public void testTowerPlacementBeyondBoardSize6() {
        TowerChessboard board = new TowerChessboard(8);

        thrown.expect(ArrayIndexOutOfBoundsException.class);
        board.put(-1, 8);
        board.update();

        int[][] grid = board.getBoard();
        assertThat(grid[3][3], is(0));
        assertThat(grid[0][0], is(0));
        assertThat(grid[0][7], is(0));
        assertThat(grid[7][0], is(0));
        assertThat(grid[7][7], is(0));


//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
    }
    @Test
    public void testTowerPlacementBeyondBoardSize7() {
        TowerChessboard board = new TowerChessboard(8);

        thrown.expect(ArrayIndexOutOfBoundsException.class);
        board.put(8, -1);
        board.update();

        int[][] grid = board.getBoard();
        assertThat(grid[3][3], is(0));
        assertThat(grid[0][0], is(0));
        assertThat(grid[0][7], is(0));
        assertThat(grid[7][0], is(0));
        assertThat(grid[7][7], is(0));


//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
    }
}