import sun.management.snmp.jvmmib.EnumJvmMemPoolType;

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

//        int[] horizontal = {0, 0};
//        int[] vertical = {0, 0};
//        int[] diagonalUp = {0, 0};    //  /
//        int[] diagonalDown = {0, 0};   //  \

        int[][] directions = new int[4][2];

        int[] total = new int[2];

        public void changeDir(Direction dir, int val, PlayerType player)
        {
            directions[getDirectionId(dir)][getPlayerTypeId(player)] += val;
            total[getPlayerTypeId(player)] += val;
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

        System.out.println("Launched");
    }

    public int insertToken(int column, PlayerType playerType)
    {
        if (heightsOfColumns[column] == HEIGHT) return -1;

        int x = column;
        int y = heightsOfColumns[column];
        heightsOfColumns[column]++;

        board[x][y].player = playerType;

        //checkHorizontal(column, heightsOfColumns[column], playerType);
        checkDirections(x, y, playerType);

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

//    private void markToken(int x, int y, Direction dir)
//    {
//        if (x < 0 || x >= WIDTH || y <0 || y >= HEIGHT) return;
//
//        //if (board[x][y] == null) board[x][y] = new Token(EMPTY);
//
//        switch(dir)
//        {
//            case HORIZONTAL:
//                board[x][y].horizontal++;
//        }
//    }

//    private void checkHorizontal(int x, int y, PlayerType playerType)
//    {
//        Token prev = (x-1) >= 0    ? board[x-1][y] : null;
//        Token next = (x+1) < WIDTH ? board[x+1][y] : null;
//
//        int playerId = getPlayerTypeId(playerType);
//        Direction dir = Direction.HORIZONTAL;
//
//        board[x][y].horizontal[playerId]++;
//
//        if (board[x][y].horizontal[playerId] == 1)
//        {
////            prev.horizontal[playerId]++;
////            next.horizontal[playerId]++;
//            changeNeighbour(x, y, -1, dir, 1, playerType);
//            changeNeighbour(x, y,  1, dir, 1, playerType);
//            return;
//        }
//
//        if (board[x][y].horizontal[playerId] >= 4)
//        {
//            gameOver = true;
//            //winner
//            return;
//        }
//
//        if (board[x][y].horizontal[playerId] == 2)
//        {
//            if (prev.player != PlayerType.EMPTY)
//            {
////                if (x-2 >= 0) board[x-2][y].horizontal[playerId]++;
////                next.horizontal[playerId] += 2;
////                prev.horizontal[playerId]++;
//                changeNeighbour(x, y,  -2, dir, 1, playerType);
//                changeNeighbour(x, y,   1, dir, 2, playerType);
//                changeNeighbour(x, y,  -1, dir, 1, playerType);
//            }
//            else
//            {
////                if (x+2 < WIDTH) board[x+2][y].horizontal[playerId]++;
////                prev.horizontal[playerId] += 2;
////                next.horizontal[playerId]++;
//                changeNeighbour(x, y,   2, dir, 1, playerType);
//                changeNeighbour(x, y,  -1, dir, 2, playerType);
//                changeNeighbour(x, y,   1, dir, 1, playerType);
//            }
//            return;
//        }
//
//        if (board[x][y].horizontal[playerId] == 3)
//        {
//            if (prev.player != PlayerType.EMPTY && next.player != PlayerType.EMPTY)
//            {
////                if (x-2 >= 0) board[x-2][y].horizontal[playerId] += 2;
////                prev.horizontal[playerId] += 2;
////                next.horizontal[playerId] += 2;
////                if (x+2 < WIDTH) board[x+2][y].horizontal[playerId] += 2;
//                changeNeighbour(x, y,  -2, dir, 2, playerType);
//                changeNeighbour(x, y,  -1, dir, 2, playerType);
//                changeNeighbour(x, y,   1, dir, 2, playerType);
//                changeNeighbour(x, y,   2, dir, 2, playerType);
//            }
//            else if (prev.player != PlayerType.EMPTY)
//            {
////                if (x-3 >= 0) board[x-3][y].horizontal[playerId]++;
////                if (x-2 >= 0) board[x-2][y].horizontal[playerId]++;
////                prev.horizontal[playerId]++;
////                next.horizontal[playerId] += 3;
//                changeNeighbour(x, y,  -3, dir, 1, playerType);
//                changeNeighbour(x, y,  -2, dir, 1, playerType);
//                changeNeighbour(x, y,  -1, dir, 1, playerType);
//                changeNeighbour(x, y,   1, dir, 3, playerType);
//            }
//            else
//            {
////                if (x+3 >= 0) board[x+3][y].horizontal[playerId]++;
////                if (x+2 >= 0) board[x+2][y].horizontal[playerId]++;
////                next.horizontal[playerId]++;
////                prev.horizontal[playerId] += 3;
//                changeNeighbour(x, y,   3, dir, 1, playerType);
//                changeNeighbour(x, y,   2, dir, 1, playerType);
//                changeNeighbour(x, y,   1, dir, 1, playerType);
//                changeNeighbour(x, y,  -1, dir, 3, playerType);
//            }
//        }
//    }

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

//        int playerId = getPlayerTypeId(playerType);

//        switch (dir)
////        {
////            case HORIZONTAL:
////                token.horizontal[playerId] += val;
////                break;
////            case VERTICAL:
////                token.vertical[playerId]  += val;
////                break;
////            case DIAGONAL_UP:
////                token.diagonalUp[playerId]  += val;
////                break;
////            case DIAGONAL_DOWN:
////                token.diagonalDown[playerId]  += val;
////                break;
////        }
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
//        Token prev = (x-1) >= 0    ? board[x-1][y] : null;
//        Token next = (x+1) < WIDTH ? board[x+1][y] : null;

        int playerId = getPlayerTypeId(playerType);
        //Direction dir = Direction.HORIZONTAL;

//        board[x][y].horizontal[playerId]++;
        board[x][y].changeDir(dir, 1, playerType);

//        if (board[x][y].horizontal[playerId] == 1)
        if (board[x][y].getDir(dir, playerType) == 1)
        {
//            prev.horizontal[playerId]++;
//            next.horizontal[playerId]++;
            changeNeighbour(x, y, -1, dir, 1, playerType);
            changeNeighbour(x, y,  1, dir, 1, playerType);
            return;
        }

//        if (board[x][y].horizontal[playerId] >= 4)
        if (board[x][y].getDir(dir, playerType) >= 4)
        {
            gameOver = true;
            //winner
            return;
        }

//        if (board[x][y].horizontal[playerId] == 2)
        if (board[x][y].getDir(dir, playerType) == 2)
        {
//            if (prev.player != PlayerType.EMPTY)
//            if (getNeighbour(x, y, -1, dir).player != PlayerType.EMPTY)
            if (getPrevType(x, y, dir) != PlayerType.EMPTY)
            {
//                if (x-2 >= 0) board[x-2][y].horizontal[playerId]++;
//                next.horizontal[playerId] += 2;
//                prev.horizontal[playerId]++;
                changeNeighbour(x, y,  -2, dir, 1, playerType);
                changeNeighbour(x, y,   1, dir, 2, playerType);
                changeNeighbour(x, y,  -1, dir, 1, playerType);
            }
            else
            {
//                if (x+2 < WIDTH) board[x+2][y].horizontal[playerId]++;
//                prev.horizontal[playerId] += 2;
//                next.horizontal[playerId]++;
                changeNeighbour(x, y,   2, dir, 1, playerType);
                changeNeighbour(x, y,  -1, dir, 2, playerType);
                changeNeighbour(x, y,   1, dir, 1, playerType);
            }
            return;
        }

//        if (board[x][y].horizontal[playerId] == 3)
        if (board[x][y].getDir(dir, playerType) == 3)
        {
//            if (prev.player != PlayerType.EMPTY && next.player != PlayerType.EMPTY)
            if (getPrevType(x, y, dir) != PlayerType.EMPTY && getNextType(x, y, dir) != PlayerType.EMPTY)
            {
//                if (x-2 >= 0) board[x-2][y].horizontal[playerId] += 2;
//                prev.horizontal[playerId] += 2;
//                next.horizontal[playerId] += 2;
//                if (x+2 < WIDTH) board[x+2][y].horizontal[playerId] += 2;
                changeNeighbour(x, y,  -2, dir, 2, playerType);
                changeNeighbour(x, y,  -1, dir, 2, playerType);
                changeNeighbour(x, y,   1, dir, 2, playerType);
                changeNeighbour(x, y,   2, dir, 2, playerType);
            }
//            else if (prev.player != PlayerType.EMPTY)
            else if (getPrevType(x, y, dir) != PlayerType.EMPTY)
            {
//                if (x-3 >= 0) board[x-3][y].horizontal[playerId]++;
//                if (x-2 >= 0) board[x-2][y].horizontal[playerId]++;
//                prev.horizontal[playerId]++;
//                next.horizontal[playerId] += 3;
                changeNeighbour(x, y,  -3, dir, 1, playerType);
                changeNeighbour(x, y,  -2, dir, 1, playerType);
                changeNeighbour(x, y,  -1, dir, 1, playerType);
                changeNeighbour(x, y,   1, dir, 3, playerType);
            }
            else
            {
//                if (x+3 >= 0) board[x+3][y].horizontal[playerId]++;
//                if (x+2 >= 0) board[x+2][y].horizontal[playerId]++;
//                next.horizontal[playerId]++;
//                prev.horizontal[playerId] += 3;
                changeNeighbour(x, y,   3, dir, 1, playerType);
                changeNeighbour(x, y,   2, dir, 1, playerType);
                changeNeighbour(x, y,   1, dir, 1, playerType);
                changeNeighbour(x, y,  -1, dir, 3, playerType);
            }
        }
    }
}
