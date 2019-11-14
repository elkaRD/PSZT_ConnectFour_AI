public class Renderer
{
    //private ConnectFourLogic mBoardLogic;
    private Controller mController;
    private final int WIDTH;
    private final int HEIGHT;

    public Renderer(Controller controller)
    {
        mController = controller;
        WIDTH = mController.WIDTH;
        HEIGHT = mController.HEIGHT;
    }

    public void render()
    {
        printTokenHorizontal();

        for (int i = 0; i < WIDTH + 2; i++) System.out.print("--");

        System.out.println("");

        for (int i = 0; i < HEIGHT; i++)
        {
            System.out.print("|");
            for (int j = 0; j < WIDTH; j++)
            {
                ConnectFourLogic.PlayerType player = mController.getPlayerType(j, HEIGHT - i - 1);
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

    private void printTokenHorizontal()
    {
        for (int i = 0; i < WIDTH + 2; i++) System.out.print("--");

        System.out.println("");

        for (int i = 0; i < HEIGHT; i++)
        {
            System.out.print("#");
            for (int j = 0; j < WIDTH; j++)
            {
                ConnectFourLogic.PlayerType player = mController.getPlayerType(j, HEIGHT - i - 1);
                ConnectFourLogic.Token token = mController.getToken(j, HEIGHT - i - 1);
                switch(player)
                {
                    case EMPTY:
                        System.out.print(" " + token.horizontal[0]);
                        break;
                    case PLAYER_A:
                        System.out.print("_" + token.horizontal[0]);
                        break;
                    case PLAYER_B:
                        System.out.print("_" + token.horizontal[0]);
                        break;
                }
            }
            System.out.println(" #");
        }
        for (int i = 0; i < WIDTH + 2; i++) System.out.print("--");
        System.out.println("");
    }
}
