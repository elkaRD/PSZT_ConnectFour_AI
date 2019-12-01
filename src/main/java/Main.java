//import javafx.stage.FileChooser;
import javax.swing.*;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.Set;
import java.util.TreeSet;

public class Main
{
    /* args:
       [0] columns
       [1] rows
       [2] 0-ai, 1-human
       [3] 0-ai, 1-human
       [4] min max algorithm depth
       [5] 0-disable alfabeta algorithm, 1- enable alfabeta algorithm
     */

    public static void main(String[] args)
    {



        System.out.println("PSZT 19Z");
        System.out.println("Maria Jarek");
        System.out.println("Robert Dudzinski");

        GameParams.getInstance().readInputValues(args);

        System.out.println("argument read correctly");

        Controller game = new Controller(GameParams.getInstance().columns,
                GameParams.getInstance().rows,
                GameParams.getInstance().aType,
                GameParams.getInstance().bType,
                GameParams.getInstance().minMaxDepth,
                GameParams.getInstance().enableAlfaBeta);

        System.out.println("game prepared");

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
}

