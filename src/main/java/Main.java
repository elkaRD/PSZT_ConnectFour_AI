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
            game.run();
        }
        catch (Exception e)
        {
            System.out.println("GOT AN EXCEPTION");
            e.printStackTrace();
        }
    }
}
