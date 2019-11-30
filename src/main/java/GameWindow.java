import sun.print.ProxyGraphics2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class GameWindow extends JFrame implements MouseListener {

    private int WIDTH = 500;
    private int HEIGHT = 600;

    private int screenOffsetX;
    private int screenOffsetY;

    private int spotSize;

    private GameBoard board;
    private Controller controller;

    private int lineHorizontalLength;
    private int lineVerticalLength;

    //coordinates of left upper corner
    private int startX;
    private int startY;

    public boolean gameOver;
    String message;
    private int pressedX;

    private boolean disabled;


    public GameWindow(GameBoard b, Controller c) {
        board = b;
        controller = c;
        gameOver = false;
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

//        difference between pane and frame dimensions
        screenOffsetX = getWidth() - panel.getWidth();
        screenOffsetY = getHeight() - panel.getHeight();

        setFocusable(true);
        setVisible(true);
        setResizable(false);

        addMouseListener(this);

        calculateValues();

    }

    public void paint(Graphics g) {
        getContentPane().removeAll();
        Graphics2D g2 = (Graphics2D)g;
        drawBoard(g2);
        drawTokens(g2);

        if(gameOver) drawMessage(g2);
    }

    private void drawMessage(Graphics2D g) {

        Font myFont = new Font("SansSerif", Font.BOLD, 30);
        g.setColor(Color.RED);
        g.setFont(myFont);

        message = "Congratulations " + board.getWinner() + " !";
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = myFont.getStringBounds(message, context);
        g.drawString(message, (int)(WIDTH - bounds.getWidth())/2, (int)(HEIGHT - bounds.getHeight())/2);
    }


    private void calculateValues() {
        double ratioWin = (double)(WIDTH-screenOffsetX)/(HEIGHT-screenOffsetY);
        double ratioBoa = (double)board.WIDTH/board.HEIGHT;

        spotSize = ratioBoa > ratioWin ? (WIDTH-screenOffsetX)/board.WIDTH : (HEIGHT-screenOffsetY)/board.HEIGHT;

        lineVerticalLength = board.HEIGHT * spotSize;
        lineHorizontalLength = board.WIDTH * spotSize;

        startX = (WIDTH + screenOffsetX - lineHorizontalLength) / 2;
        startY = HEIGHT - lineVerticalLength + screenOffsetY - 3;
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


    private int selectedColumn(int x) {

        System.out.println("X position: " + x + " Spot length: " + spotSize);

        int col = (x-startX)/spotSize;

        return col;
    }

    public void disableMouseListener() {
         disabled = true;
    }

    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {
        if(disabled) return;

        pressedX = e.getX();

        System.out.println("Kliknięto w  x = " + pressedX);
        System.out.println("Jest to kolumna nr : " + selectedColumn(pressedX));

        if (pressedX <startX || selectedColumn(pressedX) >= board.WIDTH) return;

        controller.onPickedColumn(selectedColumn(pressedX));
    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}
}
