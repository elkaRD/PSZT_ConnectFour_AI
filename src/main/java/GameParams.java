import javax.swing.*;
import java.util.Scanner;

public class GameParams
{
    //default values
    public Controller.UserType aType = Controller.UserType.HUMAN;
    public Controller.UserType bType = Controller.UserType.MACHINE;
    public int columns = 7;
    public int rows = 8;
    public int minMaxDepth = 4;
    public boolean enableAlfaBeta = true;

    private static GameParams instance = new GameParams();

    private GameParams()
    {

    }

    public static GameParams getInstance()
    {
        return instance;
    }

    public void readInputValues(String[] args) {

        int argsNumber = args.length;

        if (argsNumber == 0)
        {
            dialogWindowParams();
            return;
        }

        if (argsNumber <= 1)
            columns = Integer.parseInt(args[0]);

        if (argsNumber <= 2)
            rows = Integer.parseInt(args[1]);

        if (argsNumber <= 3)
            aType = Boolean.parseBoolean(args[0]) ? Controller.UserType.HUMAN : Controller.UserType.MACHINE;

        if (argsNumber <= 4)
            bType = Boolean.parseBoolean(args[1]) ? Controller.UserType.HUMAN : Controller.UserType.MACHINE;

        if (argsNumber <= 5)
            minMaxDepth = Integer.parseInt(args[2]);

        if (argsNumber <= 6)
            enableAlfaBeta = Boolean.parseBoolean(args[3]);

        dialogWindowParams();
    }

    private void enterAlfaBetaOnOff() {
        Scanner reader = new Scanner(System.in);

        System.out.println("Enter if alfa-beta algorithm should be enabled:\n(1 - enable, 0 - disable)");

        enableAlfaBeta = (reader.nextInt()==1);
    }

    private void enterAlgorithmMaxDepth() {
        Scanner reader = new Scanner(System.in);

        int in;

        System.out.println("Enter max depth in MinMax algorithm: ");

        minMaxDepth = reader.nextInt();
    }

    private void enterPlayerBType() {
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

    private void enterPlayerAType() {
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

    private void dialogWindowParams()
    {
        JTextField fieldColumns = new JTextField();
        JTextField fieldRows = new JTextField();
        JTextField fieldDepth = new JTextField();
        JCheckBox fieldAlphaBeta = new JCheckBox("Enable alpha beta pruning");
        JCheckBox fieldAiFirst = new JCheckBox("1st player AI");
        JCheckBox fieldAiSecond = new JCheckBox("2nd player AI");

        fieldColumns.setText(Integer.toString(columns));
        fieldRows.setText(Integer.toString(rows));
        fieldDepth.setText(Integer.toString(minMaxDepth));
        fieldAlphaBeta.setSelected(enableAlfaBeta);
        fieldAiFirst.setSelected(aType == Controller.UserType.MACHINE);
        fieldAiSecond.setSelected(bType == Controller.UserType.MACHINE);

        final JComponent[] inputs = new JComponent[] {
                new JLabel("Number of columns:"),
                fieldColumns,
                new JLabel("Number of rows:"),
                fieldRows,
                new JLabel("MinMax depth:"),
                fieldDepth,
                fieldAlphaBeta,
                new JLabel("Players type:"),
                fieldAiFirst,
                fieldAiSecond

        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION)
        {
            columns = Integer.parseInt(fieldColumns.getText());
            rows = Integer.parseInt(fieldRows.getText());
            minMaxDepth = Integer.parseInt(fieldDepth.getText());
            enableAlfaBeta = fieldAlphaBeta.isSelected();
            aType = fieldAiFirst.isSelected()  ? Controller.UserType.MACHINE : Controller.UserType.HUMAN;
            bType = fieldAiSecond.isSelected() ? Controller.UserType.MACHINE : Controller.UserType.HUMAN;
        } else {
            System.out.println("User canceled / closed the dialog, result = " + result);
        }
    }
}
