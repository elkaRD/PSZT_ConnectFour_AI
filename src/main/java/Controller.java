import java.util.Scanner;

public class Controller
{
    public final int WIDTH;
    public final int HEIGHT;

    private ConnectFourLogic mBoardLogic;
    private Renderer mRenderer;

    private boolean mCurTurn;

    public Controller(int width, int height, boolean playerAStarts)
    {
        WIDTH = width;
        HEIGHT = height;

        mBoardLogic = new ConnectFourLogic(WIDTH, HEIGHT);
        mRenderer = new Renderer(this);

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

    public void run()
    {
        Scanner input = new Scanner(System.in);


        while (true)
        {
            mRenderer.render();

            ConnectFourLogic.PlayerType curPlayer = mCurTurn ? ConnectFourLogic.PlayerType.PLAYER_A : ConnectFourLogic.PlayerType.PLAYER_B;
            int selectedRow;

            do
            {
                if (curPlayer == ConnectFourLogic.PlayerType.PLAYER_A) System.out.println("PLAYER A: ");
                else System.out.println("PLAYER B: ");
                System.out.println("Select row to insert ");
                selectedRow = input.nextInt();
            }
            while (mBoardLogic.insertToken(selectedRow, ConnectFourLogic.PlayerType.PLAYER_A) != 0); //TODO: change it back after debugging

            mCurTurn = !mCurTurn;
        }
    }
}
