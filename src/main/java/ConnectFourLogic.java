import static java.lang.Math.max;

public class ConnectFourLogic
{
    public final int WIDTH;
    public final int HEIGHT;

    private boolean gameOver = false;
    private PlayerType winner = PlayerType.EMPTY;

    public enum PlayerType
    {
        EMPTY,
        PLAYER_A,
        PLAYER_B
    }

    private enum Direction
    {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL_UP,
        DIAGONAL_DOWN
    }

    public class Token
    {
        public Token(PlayerType playerType)
        {
            player = playerType;
        }

        PlayerType player;

        int horizontal = 0;
        int vertical = 0;
        int diagonalUp = 0;     //  /
        int diagonalDown = 0;   //  \
    }

    Token[][] board;
    int[] heightsOfRows;

    public ConnectFourLogic(int width, int height)
    {
        WIDTH = width;
        HEIGHT = height;

        board = new Token[WIDTH][HEIGHT];
        heightsOfRows = new int[WIDTH];

        System.out.println("Launched");
    }

    public int insertToken(int row, PlayerType playerType)
    {
        if (heightsOfRows[row] == HEIGHT) return -1;

        board[row][heightsOfRows[row]] = new Token(playerType);



        heightsOfRows[row]++;

        return 0;
    }

    public Token getToken(int x, int y)
    {
        return board[x][y];
    }

    public PlayerType getPlayerType(int x, int y)
    {
        if (board[x][y] == null) return PlayerType.EMPTY;

        return board[x][y].player;
    }

//    private void updateNeighbours(int x, int y)
//    {
//        int lowX = x - 1;
//        int HighX = x + 1;
//        int LowY = y - 1;
//        int HighY = y + 1;
//    }
//
//    private void markToken(int x, int y, Direction dir)
//    {
//        if (x < 0 || x >= WIDTH || y <0 || y >= HEIGHT) return;
//
//        if (board[x][y] == null) board[x][y] = new Token(EMPTY);
//
//        switch(dir)
//        {
//            case HORIZONTAL:
//                board[x][y].horizontal++;
//        }
//    }
//
//    private void checkHorizontal(int x, int y)
//    {
//
//    }
}
