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

        for (int i = 0; i < WIDTH; i++)
            for (int j =0; j < HEIGHT; j++)
                board[i][j] = new Token(PlayerType.EMPTY);

        System.out.println("Launched");
    }

    public int insertToken(int row, PlayerType playerType)
    {
        if (heightsOfRows[row] == HEIGHT) return -1;

        board[row][heightsOfRows[row]].player = playerType;

        checkHorizontal(row, heightsOfRows[row]);

        heightsOfRows[row]++;

        return 0;
    }

    public Token getToken(int x, int y)
    {
        return board[x][y];
    }

    public PlayerType getPlayerType(int x, int y)
    {
        //if (board[x][y] == null) return PlayerType.EMPTY;

        return board[x][y].player;
    }

    private void updateNeighbours(int x, int y)
    {
        int lowX = x - 1;
        int HighX = x + 1;
        int LowY = y - 1;
        int HighY = y + 1;
    }

    private void markToken(int x, int y, Direction dir)
    {
        if (x < 0 || x >= WIDTH || y <0 || y >= HEIGHT) return;

        //if (board[x][y] == null) board[x][y] = new Token(EMPTY);

        switch(dir)
        {
            case HORIZONTAL:
                board[x][y].horizontal++;
        }
    }

    private void checkHorizontal(int x, int y)
    {
        Token prev = (x-1) >= 0    ? board[x-1][y] : null;
        Token next = (x+1) < WIDTH ? board[x+1][y] : null;

        if (board[x][y].horizontal == 0)
        {
            board[x][y].horizontal++;
            prev.horizontal++;
            next.horizontal++;
            return;
        }

        if (board[x][y].horizontal >= 3)
        {
            gameOver = true;
            //winner
            return;
        }

        if (board[x][y].horizontal == 1)
        {
            board[x][y].horizontal++;
            if (prev.player != PlayerType.EMPTY)
            {
                if (x-2 >= 0) board[x-2][y].horizontal++;
                next.horizontal += 2;
                prev.horizontal++;
            }
            else
            {
                if (x+2 < WIDTH) board[x+2][y].horizontal++;
                prev.horizontal += 2;
                next.horizontal++;
            }
            return;
        }

        if (board[x][y].horizontal == 2)
        {
            board[x][y].horizontal++;
            if (prev.player != PlayerType.EMPTY && next.player != PlayerType.EMPTY)
            {
                if (x-2 >= 0) board[x-2][y].horizontal++;
                prev.horizontal++;
                next.horizontal++;
                if (x+2 < WIDTH) board[x+2][y].horizontal++;
            }
            else if (prev.player != PlayerType.EMPTY)
            {
                if (x-3 >= 0) board[x-3][y].horizontal++;
                if (x-2 >= 0) board[x-2][y].horizontal++;
                prev.horizontal++;
                next.horizontal += 3;
            }
            else
            {
                if (x+3 >= 0) board[x+3][y].horizontal++;
                if (x+2 >= 0) board[x+2][y].horizontal++;
                next.horizontal++;
                prev.horizontal += 3;
            }
        }
    }

    private Token getNeighbour(int x, int y, int dst, Direction dir)
    {
        switch (dir)
        {
            case HORIZONTAL:
                x += dst;
                break;
            case VERTICAL:
                y += dst;
                break;
            case DIAGONAL_UP:
                x += dst;
                y += dst;
                break;
            case DIAGONAL_DOWN:
                x += dst;
                y -= dst;
        }

        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
            return null;

        return board[x][y];
    }

    private void changeNeighbour(int x, int y, int dst, Direction dir, int val)
    {
        Token token = getNeighbour(x, y, dst, dir);

        switch (dir)
        {
            case HORIZONTAL:
                token.horizontal += val;
                break;
            case VERTICAL:
                token.vertical += val;
                break;
            case DIAGONAL_UP:
                token.diagonalUp += val;
                break;
            case DIAGONAL_DOWN:
                token.diagonalDown += val;
                break;
        }
    }
}
