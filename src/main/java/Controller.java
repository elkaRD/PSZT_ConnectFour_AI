import java.util.Scanner;

public class Controller
{
    public final int WIDTH;
    public final int HEIGHT;

    private Renderer mRenderer;

    private GameBoard gameBoard;

    private boolean mCurTurn;

    public Controller(int width, int height, boolean playerAStarts)
    {
        WIDTH = width;
        HEIGHT = height;

        mRenderer = new Renderer(this);

        gameBoard = new GameBoard(WIDTH, HEIGHT);

        //mRenderer.render();

        mCurTurn = playerAStarts;
    }

    public GameBoard.PlayerType getPlayerType(int x, int y)
    {
        return gameBoard.getPlayerType(x, y);
    }

    public GameBoard.Token getToken(int x, int y)
    {
        return gameBoard.getToken(x, y);
    }

    public void run() throws Exception
    {
        Scanner input = new Scanner(System.in);
        //MinMax aiEngine = new MinMax(WIDTH, true);
        AIEngine aiEngine = new AIEngine(WIDTH, HEIGHT);

        while (true)
        {
            //mRenderer.render();
            gameBoard.debugDisplay();

            GameBoard.PlayerType curPlayer = mCurTurn ? GameBoard.PlayerType.PLAYER_A : GameBoard.PlayerType.PLAYER_B;
            int selectedColumn;

            do
            {
                if (curPlayer == GameBoard.PlayerType.PLAYER_A) System.out.println("PLAYER A: ");
                else System.out.println("PLAYER B: ");

                if (curPlayer == GameBoard.PlayerType.PLAYER_A)
                {
                    System.out.println("Select column to insert ");
                    selectedColumn = input.nextInt();
                    //aiEngine.makeOpponentMove(selectedColumn);
                    aiEngine.opponentMove(selectedColumn);
                }
                else
                {
                    //selectedColumn = aiEngine.getAIMove();
                    selectedColumn = aiEngine.getAiMove();

                    if (selectedColumn < 0 || selectedColumn >= WIDTH)
                    {
                        System.out.println("FATAL ERROR " + selectedColumn);
                        throw new Exception("AI's move out of board");
                    }
                }

                //GameBoard.PlayerType p = curPlayer == GameBoard.PlayerType.PLAYER_A ? GameBoard.PlayerType.PLAYER_A : GameBoard.PlayerType.PLAYER_B;
                //gameBoard.insertToken(selectedColumn, p);
            }
            while (gameBoard.insertToken(selectedColumn, curPlayer) != 0);

            if (gameBoard.getGameOver())
            {
                String winner = mCurTurn ? "PLAYER A" : "PLAYER B";
                System.out.println("GAMEOVER: " + winner + " wins");
                break;
            }

            mCurTurn = !mCurTurn;
        }
    }
}
