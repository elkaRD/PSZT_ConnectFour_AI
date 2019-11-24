import java.util.Scanner;

public class Controller
{
    public final int WIDTH;
    public final int HEIGHT;

    private ConnectFourLogic mBoardLogic;
    private Renderer mRenderer;

    private GameBoard gameBoard;

    private boolean mCurTurn;

    public Controller(int width, int height, boolean playerAStarts)
    {
        WIDTH = width;
        HEIGHT = height;

        mBoardLogic = new ConnectFourLogic(WIDTH, HEIGHT);
        mRenderer = new Renderer(this);

        gameBoard = new GameBoard(WIDTH, HEIGHT);

        //mRenderer.render();

        mCurTurn = playerAStarts;
    }

    public ConnectFourLogic.PlayerType getPlayerType(int x, int y)
    {
        return mBoardLogic.getPlayerType(x, y);
    }

    public ConnectFourLogic.Token getToken(int x, int y)
    {
        return mBoardLogic.getToken(x, y);
    }

    public void run() throws Exception
    {
        Scanner input = new Scanner(System.in);
        MinMax aiEngine = new MinMax(WIDTH, true);

        while (true)
        {
            //mRenderer.render();
            gameBoard.debugDisplay();

            ConnectFourLogic.PlayerType curPlayer = mCurTurn ? ConnectFourLogic.PlayerType.PLAYER_A : ConnectFourLogic.PlayerType.PLAYER_B;
            int selectedColumn;

            do
            {
                if (curPlayer == ConnectFourLogic.PlayerType.PLAYER_A) System.out.println("PLAYER A: ");
                else System.out.println("PLAYER B: ");

                if (curPlayer == ConnectFourLogic.PlayerType.PLAYER_A)
                {
                    System.out.println("Select column to insert ");
                    selectedColumn = input.nextInt();
                    aiEngine.makeOpponentMove(selectedColumn);
                }
                else
                {
                    selectedColumn = aiEngine.getAIMove();

                    if (selectedColumn < 0 || selectedColumn >= WIDTH)
                    {
                        System.out.println("FATAL ERROR " + selectedColumn);
                        throw new Exception("AI's move out of board");
                    }
                }

                GameBoard.PlayerType p = curPlayer == ConnectFourLogic.PlayerType.PLAYER_A ? GameBoard.PlayerType.PLAYER_A : GameBoard.PlayerType.PLAYER_B;
                gameBoard.insertToken(selectedColumn, p);
            }
            while (mBoardLogic.insertToken(selectedColumn, curPlayer) != 0);

            mCurTurn = !mCurTurn;
        }
    }
}
