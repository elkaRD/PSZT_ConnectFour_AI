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

        System.out.println("argument read correctly");

        Controller game = new Controller(5, 7, aType, bType, minMaxDepth, enableAlfaBeta); //-astart +min, alfa

        System.out.println("game prepared");

        // AI vs. AI
        //Controller game = new Controller(7, 8, aStarts, bType, bType);

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


    private static void readInputValues(String[] args) {

        int argsNumber = args.length;

        switch (argsNumber) {
            case 4:
                aType = Boolean.parseBoolean(args[0]) ? Controller.UserType.HUMAN : Controller.UserType.MACHINE;
                bType = Boolean.parseBoolean(args[1]) ? Controller.UserType.HUMAN : Controller.UserType.MACHINE;
                minMaxDepth = Integer.parseInt(args[2]);
                enableAlfaBeta = Boolean.parseBoolean(args[3]);
                break;

            case 3:
                aType = Boolean.parseBoolean(args[0]) ? Controller.UserType.HUMAN : Controller.UserType.MACHINE;
                bType = Boolean.parseBoolean(args[1]) ? Controller.UserType.HUMAN : Controller.UserType.MACHINE;
                minMaxDepth = Integer.parseInt(args[2]);
                enterAlfaBetaOnOff();
                break;

            case 2:
                aType = Boolean.parseBoolean(args[0]) ? Controller.UserType.HUMAN : Controller.UserType.MACHINE;
                bType = Boolean.parseBoolean(args[1]) ? Controller.UserType.HUMAN : Controller.UserType.MACHINE;
                enterAlgorithmMaxDepth();
                enterAlfaBetaOnOff();
                break;

            case 1:
                aType = Boolean.parseBoolean(args[0]) ? Controller.UserType.HUMAN : Controller.UserType.MACHINE;
                enterPlayerBType();
                enterAlgorithmMaxDepth();
                enterAlfaBetaOnOff();
                break;

            case 0:
                enterPlayerAType();
                enterPlayerBType();
                enterAlgorithmMaxDepth();
                //enterAlfaBetaOnOff();
                break;
        }
/*
        if (args.length > 0) {
            aType = Boolean.parseBoolean(args[0]) ? Controller.UserType.HUMAN : Controller.UserType.MACHINE;

            if (args[1] != null) {
                bType = Boolean.parseBoolean(args[1]) ? Controller.UserType.HUMAN : Controller.UserType.MACHINE;

                if (args[2] != null) {
                    minMaxDepth = Integer.parseInt(args[2]);

                    if (args[3] != null) {
                        enableAlfaBeta = Boolean.parseBoolean(args[3]);
                    }
                    else {
                        enterAlfaBetaOnOff();
                    }
                }
                else {
                    enterAlgorithmMaxDepth();
                    enterAlfaBetaOnOff();
                }
            }
            else {
                enterPlayerBType();
                enterAlgorithmMaxDepth();
                enterAlfaBetaOnOff();
            }
        }
        else {
            enterPlayerAType();
            enterPlayerBType();
            enterAlgorithmMaxDepth();
            enterAlfaBetaOnOff();
        }

*/
    }

    private static void enterAlfaBetaOnOff() {
        Scanner reader = new Scanner(System.in);

        System.out.println("Enter if alfa-beta algorithm should be enabled:\n(1 - enable, 0 - disable)");

        enableAlfaBeta = (reader.nextInt()==1);
    }

    private static void enterAlgorithmMaxDepth() {
        Scanner reader = new Scanner(System.in);

        int in;

        System.out.println("Enter max depth in MinMax algorithm: ");

        minMaxDepth = reader.nextInt();
    }

    private static void enterPlayerBType() {
        Scanner reader = new Scanner(System.in);

        char in;

        System.out.println("PLAYER B type: (enter A-ai, H-human");

        while (true) {
            in = reader.next().charAt(0);
            switch (in){
                case 'A':
                case 'a':
                    bType = Controller.UserType.MACHINE;
                    return;
                case 'H':
                case 'h':
                    bType = Controller.UserType.HUMAN;
                    return;
                default:
                    System.out.println("Unexpected value, please try again");
                    break;
            }
        }
    }

    private static void enterPlayerAType() {
        Scanner reader = new Scanner(System.in);

        char in;

        System.out.println("PLAYER A type: (enter A-ai, H-human");

        while (true) {
            in = reader.next().charAt(0);
            switch (in){
                case 'A':
                case 'a':
                    aType = Controller.UserType.MACHINE;
                    return;
                case 'H':
                case 'h':
                    aType = Controller.UserType.HUMAN;
                    return;
                default:
                    System.out.println("Unexpected value, please try again");
                    break;
            }
        }
    }
}

