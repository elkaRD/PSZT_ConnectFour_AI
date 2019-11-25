public class DebugUI
{
    public static void debugDisplay(GameBoard board)
    {
        System.out.print("  ");
        for (int i = 0; i < board.WIDTH; i++) System.out.print(i + " ");
        System.out.println(" ");
        for (int i = 0; i < board.WIDTH + 2; i++) System.out.print("--");

        System.out.println("");

        for (int i = 0; i < board.HEIGHT; i++)
        {
            System.out.print("|");
            for (int j = 0; j < board.WIDTH; j++)
            {
                GameBoard.PlayerType player = board.getPlayerType(j, board.HEIGHT - i - 1);
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
        for (int i = 0; i < board.WIDTH + 2; i++) System.out.print("--");
        System.out.println("");
    }

    public static void displayCurTree(AIEngine ai)
    {
        for (int i = 0; i < 5; i++)
        {
            System.out.println("LAYER " + i);

            displayLayer(ai.debugGetRoot(), i);
            System.out.println(" ");
        }
    }

    private static void displayLayer(AIEngine.Node node, int layer)
    {
        if (node.isTerminal)
        {
            int c = node.children.size();
            char p = 'a';
            if (node.board.getWinner() == GameBoard.PlayerType.PLAYER_B) p = 'b';
            System.out.print(node.move + "TERMINAL" + p + node.value + ", ");
        }

        if (layer == 0)
        {
            System.out.print(node.value + ", ");
            return;
        }

        for (AIEngine.Node child : node.children)
        {
            displayLayer(child, layer-1);
        }
    }
}
