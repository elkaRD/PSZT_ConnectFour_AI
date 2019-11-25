//import javafx.stage.FileChooser;
import java.util.Scanner;

public class Main
{
    //default values
    private static Controller.UserType aType = Controller.UserType.HUMAN;
    private static Controller.UserType bType = Controller.UserType.MACHINE;
    private static int minMaxDepth = 4;
    private static boolean enableAlfaBeta = true;


    /* args:
       [0] 0-ai, 1-human
       [1] 0-ai, 1-human
       [2] min max algorithm depth
       [3] 0-disable alfabeta algorithm, 1- enable alfabeta algorithm
     */

    public static void main(String[] args)
    {
        System.out.println("PSZT 19Z");
        System.out.println("@Reacryp");

        readInputValues(args);

        boolean aStarts = whoStartsTheGame();   // false - AI, true - human

        Controller game = new Controller(7, 8, aStarts, aType, bType);

        // AI vs. AI
        //Controller game = new Controller(7, 8, aStarts, aType, bType);

        try
        {
            game.run();
        }
        catch (Exception e)
        {
            System.out.println("GOT AN EXCEPTION");
            e.printStackTrace();
        }
    }

    private static boolean whoStartsTheGame() {
        Scanner reader = new Scanner(System.in);

        char in;

        System.out.println("Who starts the game? Enter A or P\n(A - AI, P - Human");

        while (true) {
            in = reader.next().charAt(0);
            switch (in){
                case 'A':
                    return true;
                case 'P':
                    return false;
                default:
                    System.out.println("Unexpected value, please try again");
            }
        }
    }

    private static void readInputValues(String[] args) {
        if (args.length > 0) {
            aType = Boolean.parseBoolean(args[0]) ? Controller.UserType.HUMAN : Controller.UserType.MACHINE;

            if (args[1] != null) {
                bType = Boolean.parseBoolean(args[1]) ? Controller.UserType.HUMAN : Controller.UserType.MACHINE;

                if (args[2] != null) {
                    minMaxDepth = Integer.parseInt(args[2]);

                    if (args[3] != null) {
                        enableAlfaBeta = Boolean.parseBoolean(args[3]);
                    }
                }
            }
        }



    }
}
