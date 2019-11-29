import sun.print.ProxyGraphics2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class GameWindow extends JFrame implements MouseListener {

    public int WIDTH = 500;
    public int HEIGHT = 600;

    private int magicXTranslation = 8;
    private int magicYTranslation = 37;

    public int spotSize;

    public GameBoard board;
    public Controller controller;

    int lineHorizontalLength;
    int lineVerticalLength;

    //coordinates of left upper corner
    private int startX;
    private int startY;

    private int[] xLeftSpotDim;

    private boolean gameOver;
    String message;
    public int pressedX;
    public int pressedY;

    boolean state = false;

    boolean disabled;


    public void changeState() {
        state = !state;
    }
    public GameWindow(GameBoard b, Controller c) {
        board = b;
        controller = c;
        gameOver = false;
        xLeftSpotDim = new int[board.WIDTH];
        disabled = false;
    }

    public void buildWindow()
    {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        getContentPane().add(panel, BorderLayout.CENTER);

        setBounds(0, 0, WIDTH, HEIGHT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setFocusable(true);
        setVisible(true);
        setResizable(false);

        addMouseListener(this);

        calculateValues();

    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        drawBoard(g2);
        drawTokens(g2);

        if(gameOver) drawMessage(g2);

    }

    private void drawMessage(Graphics2D g) {

        Font myFont = new Font("SansSerif", Font.BOLD, 30);
        g.setColor(Color.RED);
        g.setFont(myFont);

        message = "Congratulations!" + message;
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = myFont.getStringBounds(message, context);
        g.drawString(message, (int)(WIDTH - bounds.getWidth())/2, (int)(HEIGHT - bounds.getHeight())/2);
    }


    private void calculateValues() {
        double ratioWin = (double)WIDTH/HEIGHT;
        double ratioBoa = (double)board.WIDTH/board.HEIGHT;

        spotSize = ratioBoa > ratioWin ? WIDTH/board.WIDTH : HEIGHT/board.HEIGHT;
        {
//        if(ratioBoa > ratioWin) {
//            spotSize = WIDTH/board.WIDTH;
//        }
//        else {
//            spotSize = HEIGHT/board.HEIGHT;
//        }
        }

        lineVerticalLength = board.HEIGHT * spotSize;
        lineHorizontalLength = board.WIDTH * spotSize;

        startX = (WIDTH - lineHorizontalLength) / 2;
        startY = HEIGHT - spotSize*board.HEIGHT + magicYTranslation;
        for(int i = 0; i<board.WIDTH; ++i) {
            xLeftSpotDim[i] = startX + i * spotSize;
        }

    }

    private void drawBoard(Graphics2D g2) {

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));

        verticalLines(g2);
        horizontalLines(g2);

    }
    private void verticalLines(Graphics2D g) {
        int x = startX;
        int y = startY;

        for(int i = 0; i <= board.WIDTH; ++i, x+=spotSize) {
            g.drawLine(x, y, x, y+lineVerticalLength);
        }
    }
    private void horizontalLines(Graphics2D g) {
        int x = startX;
        int y = startY;

        for(int j=0; j <= board.HEIGHT; ++j, y += spotSize) {
            g.drawLine(x, y, x+lineHorizontalLength, y);
        }
    }

    public void refreshBoard(GameBoard newGameBoard) {
        board = newGameBoard;
    }

    private void drawTokens(Graphics2D g) {

        int x = startX + spotSize/4;
        int y = startY + spotSize/4;

        GameBoard.PlayerType player;

        for(int i = 0; i < board.HEIGHT; ++i) {
            for(int j = 0; j < board.WIDTH; ++j) {

                player = board.getToken(j, board.HEIGHT-1-i).player;

                switch (player) {
                    case PLAYER_A:
                        g.setColor(Color.blue);
                        g.fillOval(x , y , spotSize/2, spotSize/2);
                        break;
                    case PLAYER_B:
                        g.setColor(Color.YELLOW);
                        g.fillOval(x , y , spotSize/2, spotSize/2);
                        break;
                }

                x += spotSize;
            }

            x = startX + spotSize/4;
            y += spotSize;
        }
    }

    public void display() {
        repaint();
    }

    public void winningMessage(String winnerName) {
        message = winnerName;
        gameOver = true;
    }

    private int selectedColumn(int x) {

        System.out.println("X position: " + x + " Spot length: " + spotSize);

//        poprawić na xstartposition - już jest ok?
        int col = (x-startX)/spotSize;

        return col;
    }

    public int getColumn() {
        return selectedColumn(pressedX);
    }

    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {
        if(disabled) return;

        pressedX = e.getX();

        System.out.println("Kliknięto w  x = " + pressedX);
        System.out.println("Jest to kolumna nr : " + selectedColumn(pressedX));

        controller.onPickedColumn(selectedColumn(pressedX));
    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}
}
