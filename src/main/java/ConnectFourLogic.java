import static java.lang.Math.max;
import static java.lang.Math.min;

public class ConnectFourLogic
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

    public ConnectFourLogic makeCopy()
    {
        ConnectFourLogic temp = new ConnectFourLogic(WIDTH, HEIGHT);
        temp.gameOver = gameOver;
        temp.winner = winner;
        //temp.board = board.clone();
        temp.heightsOfColumns = heightsOfColumns.clone();
        //temp.board = board.clone();
//        for (int i = 0; i < temp.board.length; i++)
//        {
//            temp.board[i] = temp.board[i].makeCopy();
//
//        }
        temp.board = new Token[WIDTH][HEIGHT];
//        temp.heightsOfColumns = new int[WIDTH];

        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < HEIGHT; j++)
                temp.board[i][j] = board[i][j].makeCopy();

        for (int i = 0; i < WIDTH; i++)
            temp.heightsOfColumns[i] = heightsOfColumns[i];

        return temp;
    }

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

        public Token makeCopy()
        {
            Token temp = new Token(player);
            //temp.directions = directions.clone();
            temp.directions = new int [4][2];
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 2; j++)
                    temp.directions[i][j] = directions[i][j];
            temp.total = total.clone();
            return temp;
        }
    }

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

    public boolean checkSpaceForToken(int column)
    {
        return heightsOfColumns[column] != HEIGHT;
    }

    public int getRatingForColumn(int column) {
        //int playerId = getPlayerTypeId(playerType);

        int result = 0;
        int multiplier = -1;

        for (int playerId = 0; playerId < 2; playerId++)
        {
            if (playerId == 1) multiplier += 2;
            //for (int x = 0; x < WIDTH; x++)
            {
                for (int y = 0; y < HEIGHT; y++)
                {
                    if (board[column][y].total[playerId] == 3) return 10000 * multiplier;
                    if (board[column][y].total[playerId] == 2) result += 100 * multiplier;
                    if (board[column][y].total[playerId] == 1) result += 10 * multiplier;
                }
            }
        }

        return result;
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

    public void debugDisplay()
    {
        debugPrintTokenHorizontal();

        for (int i = 0; i < WIDTH + 2; i++) System.out.print("--");

        System.out.println("");

        for (int i = 0; i < HEIGHT; i++)
        {
            System.out.print("|");
            for (int j = 0; j < WIDTH; j++)
            {
                ConnectFourLogic.PlayerType player = getPlayerType(j, HEIGHT - i - 1);
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
    }

    private void debugPrintTokenHorizontal()
    {
        for (int i = 0; i < WIDTH + 2; i++) System.out.print("--");

        System.out.println("");

        for (int i = 0; i < HEIGHT; i++)
        {
            System.out.print("#");
            for (int j = 0; j < WIDTH; j++)
            {
                ConnectFourLogic.PlayerType player = getPlayerType(j, HEIGHT - i - 1);
                ConnectFourLogic.Token token = getToken(j, HEIGHT - i - 1);
                switch(player)
                {
                    case EMPTY:
//                        System.out.print(" " + token.getDir(HO));
                        System.out.print(" " + token.getTotal(ConnectFourLogic.PlayerType.PLAYER_B));
                        break;
                    case PLAYER_A:
//                        System.out.print(" " + token.getDir(ConnectFourLogic.Direction.HORIZONTAL, ConnectFourLogic.PlayerType.PLAYER_A));
//                        System.out.print(" " + token.getTotal(ConnectFourLogic.PlayerType.PLAYER_A));
                        System.out.print("  ");
                        break;
                    case PLAYER_B:
//                        System.out.print("_" + token.horizontal[0]);
                        System.out.print("  ");
                        break;
                }
            }
            System.out.println(" #");
        }
        for (int i = 0; i < WIDTH + 2; i++) System.out.print("--");
        System.out.println("");
    }
}
