
import javax.swing.*;
import java.awt.*;
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



    public GameWindow gameWindow;
    private int pickedColumn;

    // nowa funkcja
    GameBoard.PlayerType curPlayerNumber;
    GameBoard.PlayerType nextPlayerNumber;
    AIEngine aiEngineFirst;
    AIEngine aiEngineSecond;
    int selectedColumn;


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



        gameWindow = new GameWindow(gameBoard, this);


        aiEngineFirst = new AIEngine(WIDTH, HEIGHT, algorithmDepth, GameBoard.PlayerType.PLAYER_A);
        aiEngineSecond = new AIEngine(WIDTH, HEIGHT, algorithmDepth, GameBoard.PlayerType.PLAYER_B);

        curPlayerNumber = mCurTurn ? GameBoard.PlayerType.PLAYER_A : GameBoard.PlayerType.PLAYER_B;
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
        gameWindow.buildWindow();

        if(firstPlayerType == UserType.MACHINE) {
            selectedColumn = aiEngineFirst.getAiMove();
            gameBoard.insertToken(selectedColumn, curPlayerNumber);

            gameWindow.refreshBoard(gameBoard);
            gameWindow.display();

            mCurTurn = !mCurTurn;
        }

        if(firstPlayerType == UserType.MACHINE && secondPlayerType == UserType.MACHINE) {

        }
//        Scanner input = new Scanner(System.in);
//
//        AIEngine aiEngineFirst = new AIEngine(WIDTH, HEIGHT, algorithmDepth, GameBoard.PlayerType.PLAYER_A);
//        AIEngine aiEngineSecond = new AIEngine(WIDTH, HEIGHT, algorithmDepth, GameBoard.PlayerType.PLAYER_B);
//
//
//        gameWindow.buildWindow();
//
//        int pom = 0;
//        while (true)
//        {
//
//            DebugUI.debugDisplay(gameBoard);
//
//
//
//            UserType curPlayerType = mCurTurn ? firstPlayerType : secondPlayerType;
//            GameBoard.PlayerType curPlayerNumber = mCurTurn ? GameBoard.PlayerType.PLAYER_A : GameBoard.PlayerType.PLAYER_B;
//            int selectedColumn;
//
//            do
//            {
//                if (curPlayerNumber == GameBoard.PlayerType.PLAYER_A) System.out.println("PLAYER A: ");
//                else System.out.println("PLAYER B: ");
//
//                if (curPlayerNumber == GameBoard.PlayerType.PLAYER_A)
//                {
//                    if(firstPlayerType == UserType.HUMAN) {
//                        System.out.println("Select column to insert ");
//                        selectedColumn = input.nextInt();
//                    }
//                    else{
//                        selectedColumn = aiEngineFirst.getAiMove();
//                    }
//
//                    if(secondPlayerType == UserType.MACHINE) {
//                        aiEngineSecond.opponentMove(selectedColumn);
//                    }
//
//                }
//                else
//                {
//                    if(secondPlayerType == UserType.HUMAN) {
//                        System.out.println("Select column to insert ");
//                        selectedColumn = input.nextInt();
//                    }
//                    else{
//                        selectedColumn = aiEngineSecond.getAiMove();
//                    }
//
//                    if(firstPlayerType == UserType.MACHINE) {
//                        aiEngineFirst.opponentMove(selectedColumn);
//                    }
//                }
//
//                gameWindow.refreshBoard(gameBoard);
//                gameWindow.display();
//            }
//            while (gameBoard.insertToken(selectedColumn, curPlayerNumber) != 0);
//
//            if (gameBoard.getGameOver())
//            {
//                String winner = mCurTurn ? "PLAYER A" : "PLAYER B";
//                System.out.println("GAMEOVER: " + winner + " wins");
//
//                gameWindow.winningMessage(winner);
//                gameWindow.display();
//
//                break;
//
//            }
//
//            mCurTurn = !mCurTurn;
//
//        }
    }

    public void onPickedColumn() {

        pickedColumn = gameWindow.getColumn();

        if( !gameBoard.checkSpaceForToken(pickedColumn) ) {
            return;
        }


        curPlayerNumber = mCurTurn ? GameBoard.PlayerType.PLAYER_A : GameBoard.PlayerType.PLAYER_B;
        nextPlayerNumber = mCurTurn ? GameBoard.PlayerType.PLAYER_B : GameBoard.PlayerType.PLAYER_A;


        if (curPlayerNumber == GameBoard.PlayerType.PLAYER_A) System.out.println("PLAYER A: ");
        else System.out.println("PLAYER B: ");

        if (curPlayerNumber == GameBoard.PlayerType.PLAYER_A)
        {
            if(firstPlayerType == UserType.HUMAN) {
                System.out.println("Select column to insert ");
                selectedColumn = pickedColumn;
                gameBoard.insertToken(selectedColumn, curPlayerNumber);
                if (gameBoard.getGameOver()) gameWindow.winningMessage("Player A");
            }
            else{
                selectedColumn = aiEngineFirst.getAiMove();
                gameBoard.insertToken(selectedColumn, curPlayerNumber);
                if (gameBoard.getGameOver()) gameWindow.winningMessage("Player A");
            }

            if(secondPlayerType == UserType.MACHINE) {
                aiEngineSecond.opponentMove(pickedColumn);
                selectedColumn = aiEngineSecond.getAiMove();
                gameBoard.insertToken(selectedColumn, nextPlayerNumber);
                if (gameBoard.getGameOver()) gameWindow.winningMessage("Player B");
            }

        }
        else {
            if(secondPlayerType == UserType.HUMAN) {
                System.out.println("Select column to insert ");
                selectedColumn = pickedColumn;
                gameBoard.insertToken(selectedColumn, curPlayerNumber);

                if (gameBoard.getGameOver()) gameWindow.winningMessage("Player B");
            }
            else{
                selectedColumn = aiEngineSecond.getAiMove();
                gameBoard.insertToken(selectedColumn, curPlayerNumber);
                if (gameBoard.getGameOver()) gameWindow.winningMessage("Player B");
            }

            if(firstPlayerType == UserType.MACHINE) {
                aiEngineFirst.opponentMove(pickedColumn);
                selectedColumn = aiEngineFirst.getAiMove();
                gameBoard.insertToken(selectedColumn, nextPlayerNumber);
                if (gameBoard.getGameOver()) gameWindow.winningMessage("Player A");
            }
        }

//        gameBoard.insertToken(selectedColumn, curPlayerNumber);

        gameWindow.refreshBoard(gameBoard);
        if (gameBoard.getGameOver()) gameWindow.winningMessage("");
        gameWindow.display();

    }

    public boolean selectedColumn(int column) {
        return gameBoard.checkSpaceForToken(column);
    }


}


