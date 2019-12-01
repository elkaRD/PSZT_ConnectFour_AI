import javax.swing.*;
import java.util.Scanner;

public class GameParams
{
    //default values
    public Controller.UserType aType = Controller.UserType.HUMAN;
    public Controller.UserType bType = Controller.UserType.MACHINE;
    public int columns = 7;
    public int rows = 8;
    public int minMaxDepth1 = 4;
    public boolean enableAlfaBeta1 = true;
    public int minMaxDepth2 = 4;
    public boolean enableAlfaBeta2 = true;

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

        if (argsNumber >= 1)
            columns = Integer.parseInt(args[0]);

        if (argsNumber >= 2)
            rows = Integer.parseInt(args[1]);

        if (argsNumber >= 3)
            aType = Integer.parseInt(args[2]) != 0 ? Controller.UserType.HUMAN : Controller.UserType.MACHINE;

        if (argsNumber >= 4)
            bType = Integer.parseInt(args[3]) != 0  ? Controller.UserType.HUMAN : Controller.UserType.MACHINE;

        if (argsNumber >= 5)
            minMaxDepth1 = minMaxDepth2 = Integer.parseInt(args[4]);

        if (argsNumber >= 6)
            enableAlfaBeta1 = enableAlfaBeta2 = Boolean.parseBoolean(args[5]);

        dialogWindowParams();
    }

    private void enterAlfaBetaOnOff() {
        Scanner reader = new Scanner(System.in);

        System.out.println("Enter if alfa-beta algorithm should be enabled:\n(1 - enable, 0 - disable)");

        enableAlfaBeta1 = enableAlfaBeta2 = (reader.nextInt()==1);
    }

    private void enterAlgorithmMaxDepth() {
        Scanner reader = new Scanner(System.in);

        int in;

        System.out.println("Enter max depth in MinMax algorithm: ");

        minMaxDepth1 = minMaxDepth2 = reader.nextInt();
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
        JCheckBox fieldAiFirst = new JCheckBox("1st player AI");
        JCheckBox fieldAiSecond = new JCheckBox("2nd player AI");
        JTextField fieldDepth1 = new JTextField();
        JCheckBox fieldAlphaBeta1 = new JCheckBox("Enable alpha beta pruning");
        JTextField fieldDepth2 = new JTextField();
        JCheckBox fieldAlphaBeta2 = new JCheckBox("Enable alpha beta pruning");

        fieldColumns.setText(Integer.toString(columns));
        fieldRows.setText(Integer.toString(rows));
        fieldAiFirst.setSelected(aType == Controller.UserType.MACHINE);
        fieldAiSecond.setSelected(bType == Controller.UserType.MACHINE);
        fieldDepth1.setText(Integer.toString(minMaxDepth1));
        fieldAlphaBeta1.setSelected(enableAlfaBeta1);
        fieldDepth2.setText(Integer.toString(minMaxDepth1));
        fieldAlphaBeta2.setSelected(enableAlfaBeta1);

        final JComponent[] inputs = new JComponent[] {
                new JLabel("Number of columns:"),
                fieldColumns,
                new JLabel("Number of rows:"),
                fieldRows,
                new JLabel("MinMax AI 1:"),
                fieldDepth1,
                fieldAlphaBeta1,
                new JLabel("MinMax AI 2:"),
                fieldDepth2,
                fieldAlphaBeta2,
                new JLabel("Players type:"),
                fieldAiFirst,
                fieldAiSecond

        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Connect four - settings", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION)
        {
            columns = Integer.parseInt(fieldColumns.getText());
            rows = Integer.parseInt(fieldRows.getText());
            minMaxDepth1 = Integer.parseInt(fieldDepth1.getText());
            enableAlfaBeta1 = fieldAlphaBeta1.isSelected();
            minMaxDepth2 = Integer.parseInt(fieldDepth2.getText());
            enableAlfaBeta2 = fieldAlphaBeta2.isSelected();
            aType = fieldAiFirst.isSelected()  ? Controller.UserType.MACHINE : Controller.UserType.HUMAN;
            bType = fieldAiSecond.isSelected() ? Controller.UserType.MACHINE : Controller.UserType.HUMAN;
        } else {
            System.out.println("User canceled / closed the dialog, result = " + result);
        }
    }

    public boolean checkParameters()
    {
        if (columns < 4 || rows < 4) return false;
        if (minMaxDepth1 < 2 || minMaxDepth2 < 2) return false;

        return true;
    }
}
