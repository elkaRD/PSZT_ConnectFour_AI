public class GameBoard
{
    public final int WIDTH;
    public final int HEIGHT;

    private boolean gameOver = false;
    private PlayerType winner = PlayerType.EMPTY;

    public boolean getGameOver()
    {
        return gameOver;
    }

    Token[][] board;
    int[] heightsOfColumns;

    public enum PlayerType
    {
        EMPTY,
        PLAYER_A,
        PLAYER_B
    }

    public class Token
    {
        public Token(PlayerType playerType)
        {
            player = playerType;
        }

        PlayerType player;

        public Token makeCopy()
        {
            Token temp = new Token(player);
            return temp;
        }
    }

    public GameBoard(int width, int height)
    {
        WIDTH = width;
        HEIGHT = height;

        board = new Token[WIDTH][HEIGHT];
        heightsOfColumns = new int[WIDTH];

        for (int i = 0; i < WIDTH; i++)
            for (int j =0; j < HEIGHT; j++)
                board[i][j] = new Token(PlayerType.EMPTY);
    }

    public boolean checkSpaceForToken(int column)
    {
        return heightsOfColumns[column] != HEIGHT;
    }

    public int insertToken(int column, PlayerType playerType)
    {
        if (heightsOfColumns[column] == HEIGHT) return -1;

        int x = column;
        int y = heightsOfColumns[column];
        heightsOfColumns[column]++;

        board[x][y].player = playerType;

        if (checkGameOver(x, y, playerType))
        {
            gameOver = true;
            winner = playerType;
        }

        return 0;
    }

    public Token getToken(int x, int y)
    {
        return board[x][y];
    }

    public PlayerType getPlayerType(int x, int y)
    {
        return board[x][y].player;
    }

    public GameBoard makeCopy()
    {
        GameBoard temp = new GameBoard(WIDTH, HEIGHT);
        temp.gameOver = gameOver;
        temp.winner = winner;
        temp.heightsOfColumns = heightsOfColumns.clone();

        temp.board = new Token[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < HEIGHT; j++)
                temp.board[i][j] = board[i][j].makeCopy();

        for (int i = 0; i < WIDTH; i++)
            temp.heightsOfColumns[i] = heightsOfColumns[i];

        return temp;
    }

    public enum Direction
    {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL_UP,
        DIAGONAL_DOWN
    }

    private final static int[] VALUE = {0, 1, 5, 10, 50, 100000, 100000};
    private final static int[] VALUE_NEAR = {0, 2, 10, 100000, 100000, 100000, 100000};

    public int getRating(int column, PlayerType player)
    {
        if (heightsOfColumns[column] == HEIGHT) return 0;

        PlayerType opponent = player == PlayerType.PLAYER_A ? PlayerType.PLAYER_B : PlayerType.PLAYER_A;

        int x = column;
        int y = heightsOfColumns[column];

        int result = 0;

        if (x > 2 && x < WIDTH - 3) result += 5;
        if (y > 2 && y < HEIGHT - 3) result += 5;

        result += getRatingForDirection(x, y, player, Direction.HORIZONTAL);
        result += getRatingForDirection(x, y, player, Direction.VERTICAL);
        result += getRatingForDirection(x, y, player, Direction.DIAGONAL_UP);
        result += getRatingForDirection(x, y, player, Direction.DIAGONAL_DOWN);

        result -= getRatingForDirection(x, y, opponent, Direction.HORIZONTAL);
        result -= getRatingForDirection(x, y, opponent, Direction.VERTICAL);
        result -= getRatingForDirection(x, y, opponent, Direction.DIAGONAL_UP);
        result -= getRatingForDirection(x, y, opponent, Direction.DIAGONAL_DOWN);

        return result;
    }

    private int getX(int x, int dst, Direction dir)
    {
        switch (dir)
        {
            case HORIZONTAL:
            case DIAGONAL_DOWN:
            case DIAGONAL_UP:
                return x + dst;
        }

        return x;
    }

    private int getY(int y, int dst, Direction dir)
    {
        switch (dir)
        {
            case VERTICAL:
            case DIAGONAL_UP:
                return y + dst;
            case DIAGONAL_DOWN:
                return y - dst;
        }

        return y;
    }

    private int getRatingForDirection(int x, int y, PlayerType player, Direction dir)
    {
        int foundCounter = 0;
        int foundNear = 0;
        boolean near = true;
        PlayerType opponent = player == PlayerType.PLAYER_A ? PlayerType.PLAYER_B : PlayerType.PLAYER_A;

        for (int i = 1; i < 4; i++)
        {
            int hx = getX(x, i, dir);
            int hy = getY(y, i, dir);
            if (hx < 0 || hx >= WIDTH || hy < 0 || hy >= HEIGHT) continue;

            if (board[hx][hy].player == player)
            {
                foundCounter++;
                if (near) foundNear++;
            }
            if (board[hx][hy].player == opponent) break;
            if (board[hx][hy].player == PlayerType.EMPTY) near = false;
        }

        near = true;
        for (int i = -1; i > -4; i--)
        {
            int hx = getX(x, i, dir);
            int hy = getY(y, i, dir);
            if (hx < 0 || hx >= WIDTH || hy < 0 || hy >= HEIGHT) continue;

            if (board[hx][hy].player == player)
            {
                foundCounter++;
                if (near) foundNear++;
            }
            if (board[hx][hy].player == opponent) break;
            if (board[hx][hy].player == PlayerType.EMPTY) near = false;
        }

        return VALUE[foundCounter] + VALUE_NEAR[foundNear];
    }

    private boolean checkGameOver(int x, int y, PlayerType player)
    {
        if (checkGameOverForDirection(x, y, player, Direction.HORIZONTAL)) return true;
        if (checkGameOverForDirection(x, y, player, Direction.VERTICAL)) return true;
        if (checkGameOverForDirection(x, y, player, Direction.DIAGONAL_DOWN)) return true;
        if (checkGameOverForDirection(x, y, player, Direction.DIAGONAL_UP)) return true;

        return false;
    }

    private boolean checkGameOverForDirection(int x, int y, PlayerType player, Direction dir)
    {
        int foundNear = 0;

        for (int i = 1; i < 4; i++)
        {
            int hx = getX(x, i, dir);
            int hy = getY(y, i, dir);
            if (hx < 0 || hx >= WIDTH || hy < 0 || hy >= HEIGHT) continue;

            if (board[hx][hy].player == player) foundNear++;
            else break;
        }

        for (int i = -1; i > -4; i--)
        {
            int hx = getX(x, i, dir);
            int hy = getY(y, i, dir);
            if (hx < 0 || hx >= WIDTH || hy < 0 || hy >= HEIGHT) continue;

            if (board[hx][hy].player == player) foundNear++;
            else break;
        }
        return foundNear >= 3;
    }

    public void debugDisplay()
    {
        //debugPrintTokenHorizontal();

        System.out.print("  ");
        for (int i = 0; i < WIDTH; i++) System.out.print(i + " ");
        System.out.println(" ");
        for (int i = 0; i < WIDTH + 2; i++) System.out.print("--");

        System.out.println("");

        for (int i = 0; i < HEIGHT; i++)
        {
            System.out.print("|");
            for (int j = 0; j < WIDTH; j++)
            {
                PlayerType player = getPlayerType(j, HEIGHT - i - 1);
                switch(player)
                {
                    case EMPTY:
                        System.out.print(" .");
                        break;
                    case PLAYER_A:
                        System.out.print(" X");
                        break;
                    case PLAYER_B:
                        System.out.print(" O");
                        break;
                }
            }
            System.out.println(" |");
        }
        for (int i = 0; i < WIDTH + 2; i++) System.out.print("--");
        System.out.println("");

        for (int i = 0; i < WIDTH; i++)
        {
            System.out.println(i + ": " + getRating(i, PlayerType.PLAYER_B));
        }
    }
}
