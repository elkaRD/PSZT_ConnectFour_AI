import javafx.stage.FileChooser;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("PSZT 19Z");
        System.out.println("@Reacryp");

        Controller game = new Controller(7, 8, true);

        try
        {
            //game.run();
            testBoard1();
            testBoard2();
            testBoard3();
            testBoard4();
        }
        catch (Exception e)
        {
            System.out.println("GOT AN EXCEPTION");
            e.printStackTrace();
        }
    }

    private static void testBoard1()
    {
        GameBoard board = new GameBoard(7, 8);

        board.insertToken(5, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(5, GameBoard.PlayerType.PLAYER_B);

        board.insertToken(4, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(4, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(4, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(4, GameBoard.PlayerType.PLAYER_A);

        board.insertToken(3, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(3, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(3, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(3, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(3, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(3, GameBoard.PlayerType.PLAYER_A);

        board.insertToken(2, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(2, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(2, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(2, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(2, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(2, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(2, GameBoard.PlayerType.PLAYER_B);

        board.debugDisplay();

        int valueA = board.getRating(5, GameBoard.PlayerType.PLAYER_A);
        int valueB = board.getRating(5, GameBoard.PlayerType.PLAYER_B);

        System.out.println("VAL_A: " + valueA + ",   VAL_B: " + valueB);
    }

    private static void testBoard2()
    {
        GameBoard board = new GameBoard(7, 8);

        board.insertToken(2, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(2, GameBoard.PlayerType.PLAYER_B);

        board.insertToken(3, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(3, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(3, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(3, GameBoard.PlayerType.PLAYER_A);

        board.insertToken(4, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(4, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(4, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(4, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(4, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(4, GameBoard.PlayerType.PLAYER_A);

        board.insertToken(5, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(5, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(5, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(5, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(5, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(5, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(5, GameBoard.PlayerType.PLAYER_B);

        board.debugDisplay();

        int valueA = board.getRating(2, GameBoard.PlayerType.PLAYER_A);
        int valueB = board.getRating(2, GameBoard.PlayerType.PLAYER_B);

        System.out.println("VAL_A: " + valueA + ",   VAL_B: " + valueB);
    }

    private static void testBoard3()
    {
        GameBoard board = new GameBoard(7, 8);

        board.insertToken(0, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(0, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(0, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(0, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(0, GameBoard.PlayerType.PLAYER_A);

        board.insertToken(1, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(1, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(1, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(1, GameBoard.PlayerType.PLAYER_A);

        board.insertToken(2, GameBoard.PlayerType.PLAYER_A);

        board.insertToken(3, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(3, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(3, GameBoard.PlayerType.PLAYER_B);
        board.insertToken(3, GameBoard.PlayerType.PLAYER_B);

        board.insertToken(4, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(4, GameBoard.PlayerType.PLAYER_A);

        board.debugDisplay();

        int valueA = board.getRating(2, GameBoard.PlayerType.PLAYER_A);
        int valueB = board.getRating(2, GameBoard.PlayerType.PLAYER_B);

        System.out.println("VAL_A: " + valueA + ",   VAL_B: " + valueB);
    }

    private static void testBoard4()
    {
        GameBoard board = new GameBoard(7, 8);

        board.insertToken(2, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(3, GameBoard.PlayerType.PLAYER_A);
        board.insertToken(5, GameBoard.PlayerType.PLAYER_A);

        board.debugDisplay();

        int valueA = board.getRating(4, GameBoard.PlayerType.PLAYER_A);
        int valueB = board.getRating(4, GameBoard.PlayerType.PLAYER_B);

        System.out.println("VAL_A: " + valueA + ",   VAL_B: " + valueB);
    }
}
