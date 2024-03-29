import java.awt.*;

public class GameBoard
{
    private final static int[] VALUE = {0, 1, 5, 10, 50, 100000, 100000};
    private final static int[] VALUE_NEAR = {0, 2, 10, 100000, 100000, 100000, 100000};

    public final int WIDTH;
    public final int HEIGHT;

    private boolean gameOver = false;
    private PlayerType winner = PlayerType.EMPTY;

    private Token[][] board;
    private int[] heightsOfColumns;

    private boolean isWinningMove;
    private int numOfFreeSpots;

    public class PointCoord
    {
        public int x = -1;
        public int y = -1;

        void set(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }

    public class BoardLine
    {
        PointCoord beg = new PointCoord();
        PointCoord end = new PointCoord();
    }

    private PointCoord lastMove = new PointCoord();
    private BoardLine winningLine = new BoardLine();

    public enum PlayerType
    {
        EMPTY,
        PLAYER_A,
        PLAYER_B
    }

    public enum Direction
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

        numOfFreeSpots = width*height;

        for (int i = 0; i < WIDTH; i++)
            for (int j =0; j < HEIGHT; j++)
                board[i][j] = new Token(PlayerType.EMPTY);
    }

    public boolean getGameOver()
    {
        return gameOver;
    }

    public PlayerType getWinner()
    {
        return winner;
    }

    public PointCoord getLastMove()
    {
        return lastMove;
    }

    public BoardLine getWinningLine()
    {
        return winningLine;
    }

    public boolean checkSpaceForToken(int column)
    {
        return heightsOfColumns[column] != HEIGHT;
    }

    public int insertToken(int column, PlayerType playerType)
    {
        if (column < 0 || column >= WIDTH)
        {
            System.out.println("Column " + column + "out of range");
            return -1;
        }

        if (heightsOfColumns[column] == HEIGHT) return -1;

        int x = column;
        int y = heightsOfColumns[column];
        heightsOfColumns[column]++;
        numOfFreeSpots--;

        board[x][y].player = playerType;

        if (checkGameOver(x, y, playerType))
        {
            gameOver = true;
        }

        lastMove.set(x, y);

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

    public int getRating(int column, PlayerType player)
    {
        if (heightsOfColumns[column] == HEIGHT) return 0;

        PlayerType opponent = player == PlayerType.PLAYER_B ? PlayerType.PLAYER_A : PlayerType.PLAYER_B;

        int x = column;
        int y = heightsOfColumns[column];

        int result = 0;

        if (x > 2 && x < WIDTH - 3) result += 5;
        if (y > 2 && y < HEIGHT - 3) result += 5;

        isWinningMove = false;

        result += getRatingForDirection(x, y, player, Direction.HORIZONTAL);
        result += getRatingForDirection(x, y, player, Direction.VERTICAL);
        result += getRatingForDirection(x, y, player, Direction.DIAGONAL_UP);
        result += getRatingForDirection(x, y, player, Direction.DIAGONAL_DOWN);

        if (!isWinningMove && y + 1 < HEIGHT)
        {
            result -= 0.8 * (double) getRatingForDirection(x, y+1, opponent, Direction.HORIZONTAL);
            result -= 0.8 * (double) getRatingForDirection(x, y+1, opponent, Direction.VERTICAL);
            result -= 0.8 * (double) getRatingForDirection(x, y+1, opponent, Direction.DIAGONAL_UP);
            result -= 0.8 * (double) getRatingForDirection(x, y+1, opponent, Direction.DIAGONAL_DOWN);
        }

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
        PlayerType opponent = player == PlayerType.PLAYER_B ? PlayerType.PLAYER_A : PlayerType.PLAYER_B;

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

        isWinningMove = foundNear >= 3;

        return VALUE[foundCounter] + VALUE_NEAR[foundNear];
    }

    private boolean checkGameOver(int x, int y, PlayerType player)
    {
        if (checkGameOverForDirection(x, y, player, Direction.HORIZONTAL) ||
            checkGameOverForDirection(x, y, player, Direction.VERTICAL) ||
            checkGameOverForDirection(x, y, player, Direction.DIAGONAL_DOWN) ||
            checkGameOverForDirection(x, y, player, Direction.DIAGONAL_UP))
        {
            winner = player;
            return true;
        }

        if(numOfFreeSpots == 0) return true;

        return false;
    }

    private boolean checkGameOverForDirection(int x, int y, PlayerType player, Direction dir)
    {
        int foundNear = 0;
        BoardLine line = new BoardLine();
        line.beg.set(x, y);
        line.end.set(x, y);

        for (int i = 1; i < 4; i++)
        {
            int hx = getX(x, i, dir);
            int hy = getY(y, i, dir);
            if (hx < 0 || hx >= WIDTH || hy < 0 || hy >= HEIGHT) continue;

            if (board[hx][hy].player == player) foundNear++;
            else break;

            line.beg.set(hx, hy);
        }

        for (int i = -1; i > -4; i--)
        {
            int hx = getX(x, i, dir);
            int hy = getY(y, i, dir);
            if (hx < 0 || hx >= WIDTH || hy < 0 || hy >= HEIGHT) continue;

            if (board[hx][hy].player == player) foundNear++;
            else break;

            line.end.set(hx, hy);
        }

        if (foundNear >= 3)
        {
            winningLine = line;
            return true;
        }

        return false;
    }

    public void debugDisplay()
    {
        DebugUI.debugDisplay(this);
    }
}
