import sun.management.snmp.jvmmib.EnumJvmMemPoolType;

import static java.lang.Math.max;
import static java.lang.Math.min;

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

        int[][] directions = new int[4][2];

        int[] total = new int[2];

        public void changeDir(Direction dir, int val, PlayerType player)
        {
            int dirId = getDirectionId(dir);
            int playerId = getPlayerTypeId(player);

            directions[dirId][playerId] += val;
            directions[dirId][playerId] = min(directions[dirId][playerId], 3);

            if (getDir(dir, player) > total[playerId])
            {
                total[playerId] = getDir(dir, player);
            }
        }

        public int getDir(Direction dir, PlayerType player)
        {
            return directions[getDirectionId(dir)][getPlayerTypeId(player)];
        }

        public int getTotal(PlayerType player)
        {
            return total[getPlayerTypeId(player)];
        }
    }

    Token[][] board;
    int[] heightsOfColumns;

    public ConnectFourLogic(int width, int height)
    {
        WIDTH = width;
        HEIGHT = height;

        board = new Token[WIDTH][HEIGHT];
        heightsOfColumns = new int[WIDTH];

        for (int i = 0; i < WIDTH; i++)
            for (int j =0; j < HEIGHT; j++)
                board[i][j] = new Token(PlayerType.EMPTY);
    }

    public int insertToken(int column, PlayerType playerType)
    {
        if (heightsOfColumns[column] == HEIGHT) return -1;

        int x = column;
        int y = heightsOfColumns[column];
        heightsOfColumns[column]++;

        board[x][y].player = playerType;

        checkDirections(x, y, playerType);

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

    private PlayerType getNextType(int x, int y, Direction dir)
    {
        Token token = getNeighbour(x, y, 1, dir);
        return token == null ? PlayerType.EMPTY : token.player;
    }

    private PlayerType getPrevType(int x, int y, Direction dir)
    {
        Token token = getNeighbour(x, y, -1, dir);
        return token == null ? PlayerType.EMPTY : token.player;
    }

    private void changeNeighbour(int x, int y, int dst, Direction dir, int val, PlayerType playerType)
    {
        Token token = getNeighbour(x, y, dst, dir);
        if (token == null)
            return;

        token.changeDir(dir, val, playerType);
    }
    
    private int getPlayerTypeId(PlayerType playerType)
    {
        return playerType == PlayerType.PLAYER_A ? 0 : 1;
    }

    private int getDirectionId(Direction dir)
    {
        switch(dir)
        {
            case HORIZONTAL:
                return 0;
            case VERTICAL:
                return 1;
            case DIAGONAL_UP:
                return 2;
            case DIAGONAL_DOWN:
                return 3;
        }

        return -1;
    }

    private void checkDirections(int x, int y, PlayerType playerType)
    {
        checkDirection(x, y, Direction.HORIZONTAL, playerType);
        checkDirection(x, y, Direction.VERTICAL, playerType);
        checkDirection(x, y, Direction.DIAGONAL_UP, playerType);
        checkDirection(x, y, Direction.DIAGONAL_DOWN, playerType);
    }

    private void checkDirection(int x, int y, Direction dir, PlayerType playerType)
    {
        board[x][y].changeDir(dir, 1, playerType);

        if (board[x][y].getDir(dir, playerType) == 1)
        {
            changeNeighbour(x, y, -1, dir, 1, playerType);
            changeNeighbour(x, y,  1, dir, 1, playerType);
            return;
        }

        if (board[x][y].getDir(dir, playerType) >= 4)
        {
            gameOver = true;
            winner = playerType;
            return;
        }

        if (board[x][y].getDir(dir, playerType) == 2)
        {
            if (getPrevType(x, y, dir) != PlayerType.EMPTY)
            {
                changeNeighbour(x, y,  -2, dir, 1, playerType);
                changeNeighbour(x, y,   1, dir, 2, playerType);
                changeNeighbour(x, y,  -1, dir, 1, playerType);
            }
            else
            {
                changeNeighbour(x, y,   2, dir, 1, playerType);
                changeNeighbour(x, y,  -1, dir, 2, playerType);
                changeNeighbour(x, y,   1, dir, 1, playerType);
            }
            return;
        }

        if (board[x][y].getDir(dir, playerType) == 3)
        {
            if (getPrevType(x, y, dir) != PlayerType.EMPTY && getNextType(x, y, dir) != PlayerType.EMPTY)
            {
                changeNeighbour(x, y,  -2, dir, 2, playerType);
                changeNeighbour(x, y,  -1, dir, 2, playerType);
                changeNeighbour(x, y,   1, dir, 2, playerType);
                changeNeighbour(x, y,   2, dir, 2, playerType);
            }
            else if (getPrevType(x, y, dir) != PlayerType.EMPTY)
            {
                changeNeighbour(x, y,  -3, dir, 1, playerType);
                changeNeighbour(x, y,  -2, dir, 1, playerType);
                changeNeighbour(x, y,  -1, dir, 1, playerType);
                changeNeighbour(x, y,   1, dir, 3, playerType);
            }
            else
            {
                changeNeighbour(x, y,   3, dir, 1, playerType);
                changeNeighbour(x, y,   2, dir, 1, playerType);
                changeNeighbour(x, y,   1, dir, 1, playerType);
                changeNeighbour(x, y,  -1, dir, 3, playerType);
            }
        }
    }
}
