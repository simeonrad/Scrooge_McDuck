import java.util.Scanner;

public class ScroogeMcDuck {

    static int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    static boolean isValidMove(int x, int y, int n, int m) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }

    static int[] findStartingPosition(int[][] labyrinth) {
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[0].length; j++) {
                if (labyrinth[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    static int[] findMaxNeighbor(int x, int y, int[][] labyrinth) {
        int maxCoins = 0;
        int[] maxNeighbor = null;

        for (int[] direction : directions) {
            int nx = x + direction[0];
            int ny = y + direction[1];

            if (isValidMove(nx, ny, labyrinth.length, labyrinth[0].length) && labyrinth[nx][ny] > maxCoins) {
                maxCoins = labyrinth[nx][ny];
                maxNeighbor = new int[]{nx, ny};
            }
        }

        return maxNeighbor;
    }

    static int collectCoinsRecursively(int x, int y, int[][] labyrinth) {
        int[] nextCell = findMaxNeighbor(x, y, labyrinth);

        if (nextCell == null) {
            return 0;
        }

        int nextTempCell = 0;
        int temp = labyrinth[nextCell[0]][nextCell[1]] - 1;
        int[] tempCell = findMaxNeighbor(nextCell[0], nextCell[1], labyrinth);

        try {
            nextTempCell = labyrinth[tempCell[0]][tempCell[1]];
        } catch (NullPointerException npe) {
            nextTempCell = 0;
        }

        if (nextTempCell == 0) {
            labyrinth[x][y] -= 1;
            return 1;
        }

        labyrinth[x][y] -= 1;
        return 1 + collectCoinsRecursively(nextCell[0], nextCell[1], labyrinth);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] labyrinth = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                labyrinth[i][j] = scanner.nextInt();
            }
        }

        int[] startingPosition = findStartingPosition(labyrinth);
        int result = 0;

        if (startingPosition != null) {
            int x = startingPosition[0];
            int y = startingPosition[1];
            result = collectCoinsRecursively(x, y, labyrinth);
        }

        System.out.println(result);
    }
}
