
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

    private GameWindow gameWindow;

    private GameBoard.PlayerType curPlayerNumber;
    private UserType curPlayerType;
    private UserType nextPlayerType;
    private AIEngine aiEngineFirst;
    private AIEngine aiEngineSecond;
    private boolean gameOver;

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

        aiEngineFirst = null;
        aiEngineSecond = null;

        if(firstType == UserType.MACHINE) aiEngineFirst = new AIEngine(WIDTH, HEIGHT, algorithmDepth, GameBoard.PlayerType.PLAYER_A);
        if(secondType == UserType.MACHINE) aiEngineSecond = new AIEngine(WIDTH, HEIGHT, algorithmDepth, GameBoard.PlayerType.PLAYER_B);

        curPlayerNumber = mCurTurn ? GameBoard.PlayerType.PLAYER_A : GameBoard.PlayerType.PLAYER_B;
        curPlayerType = mCurTurn ? firstPlayerType : secondPlayerType;
        gameOver = false;

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

        int col;

        if(firstPlayerType == UserType.MACHINE) {
            col = aiEngineFirst.getAiMove();
            simulateMove(col);
            if(secondPlayerType == UserType.MACHINE) {
                aiEngineSecond.opponentMove(col);
            }
        }


        if(firstPlayerType == UserType.MACHINE && secondPlayerType == UserType.MACHINE) {

            gameWindow.disableMouseListener();

            while(!gameBoard.getGameOver()) {

                col = aiEngineSecond.getAiMove();
                simulateMove(col);
                aiEngineFirst.opponentMove(col);

                if (gameBoard.getGameOver()) break;

                col = aiEngineFirst.getAiMove();
                simulateMove(col);
                aiEngineSecond.opponentMove(col);
            }
        }

    }

    public void onPickedColumn(int col) {

        if( !gameBoard.checkSpaceForToken(col) ) {
            return;
        }

        curPlayerNumber = mCurTurn ? GameBoard.PlayerType.PLAYER_A : GameBoard.PlayerType.PLAYER_B;
        nextPlayerType = mCurTurn ? secondPlayerType : firstPlayerType;
        curPlayerType = mCurTurn ? firstPlayerType : secondPlayerType;

        if(humanTurn()) {
            simulateMove(col);

            if(gameBoard.getGameOver()) return;

            if (nextPlayerType == UserType.MACHINE) {
                switch (curPlayerNumber) {
                    case PLAYER_A:
                        aiEngineSecond.opponentMove(col);
                        simulateMove(aiEngineSecond.getAiMove());
                        break;
                    case PLAYER_B:
                        aiEngineFirst.opponentMove(col);
                        simulateMove(aiEngineFirst.getAiMove());
                        break;
                }
            }
        }
        else {
            simulateMove(col);
        }

    }

    private void simulateMove(int x) {

        if( !gameBoard.checkSpaceForToken(x) ) return;

        curPlayerNumber = mCurTurn ? GameBoard.PlayerType.PLAYER_A : GameBoard.PlayerType.PLAYER_B;

        gameBoard.insertToken(x, curPlayerNumber);

        if(gameBoard.getGameOver()) {
            System.out.println("Koniec gry");
            gameWindow.disableMouseListener();
            gameWindow.gameOver = true;
        }

        gameWindow.refreshBoard(gameBoard);
        gameWindow.display();


        mCurTurn = !mCurTurn;
    }

    private boolean humanTurn() {
        return curPlayerType == UserType.HUMAN ;
    }

}


