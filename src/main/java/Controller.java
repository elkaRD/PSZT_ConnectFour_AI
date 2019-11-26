
import java.util.Scanner;

public class Controller
{
    public final int WIDTH;
    public final int HEIGHT;

    private Renderer mRenderer;

    private GameBoard gameBoard;

    private boolean mCurTurn;

    private UserType firstPlayerType;
    private UserType secondPlayerType;

    private int algorithmDepth;
    private boolean isAlfaBetaOn;
    /*
        two more variables mean types of player A and player B
     */

    public enum UserType
    {
        MACHINE,
        HUMAN
    }

    public Controller(int width, int height, UserType firstType, UserType secondType, int minMaxDepth, boolean alfaBetaOn)
    {
        WIDTH = width;
        HEIGHT = height;

        mRenderer = new Renderer(this);

        gameBoard = new GameBoard(WIDTH, HEIGHT);

        //mRenderer.render();

        mCurTurn = true;

        firstPlayerType = firstType;
        secondPlayerType = secondType;

        algorithmDepth = minMaxDepth;
        isAlfaBetaOn = alfaBetaOn;
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

        AIEngine aiEngineFirst = new AIEngine(WIDTH, HEIGHT, algorithmDepth, GameBoard.PlayerType.PLAYER_A);
        AIEngine aiEngineSecond = new AIEngine(WIDTH, HEIGHT, algorithmDepth, GameBoard.PlayerType.PLAYER_B);
        while (true)
        {
            DebugUI.debugDisplay(gameBoard);

            UserType curPlayerType = mCurTurn ? firstPlayerType : secondPlayerType;
            GameBoard.PlayerType curPlayerNumber = mCurTurn ? GameBoard.PlayerType.PLAYER_A : GameBoard.PlayerType.PLAYER_B;
            int selectedColumn;

            do
            {
                if (curPlayerNumber == GameBoard.PlayerType.PLAYER_A) System.out.println("PLAYER A: ");
                else System.out.println("PLAYER B: ");

                if (curPlayerNumber == GameBoard.PlayerType.PLAYER_A)
                {
                    if(firstPlayerType == UserType.HUMAN) {
                        System.out.println("Select column to insert ");
                        selectedColumn = input.nextInt();
                    }
                    else{
                        selectedColumn = aiEngineFirst.getAiMove();
                    }

                    if(secondPlayerType == UserType.MACHINE) {
                        aiEngineSecond.opponentMove(selectedColumn);
                    }

                }
                else
                {
                    if(secondPlayerType == UserType.HUMAN) {
                        System.out.println("Select column to insert ");
                        selectedColumn = input.nextInt();
                    }
                    else{
                        selectedColumn = aiEngineSecond.getAiMove();
                    }

                    if(firstPlayerType == UserType.MACHINE) {
                        aiEngineFirst.opponentMove(selectedColumn);
                    }
                }

            }
            while (gameBoard.insertToken(selectedColumn, curPlayerNumber) != 0);

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
