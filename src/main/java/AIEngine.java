import java.util.ArrayList;

public class AIEngine
{
    public static final int INFINITY = 1000000000;
    public static final boolean ENABLE_ALPHA_BETA = false;

    public class Node
    {
        public Node(GameBoard gameBoard, GameBoard.PlayerType player)
        {
            board = gameBoard.makeCopy();
            playerType = player;
            //value = board.getRating(column, player);
            //board.insertToken(column, player);

            if (board.getGameOver()) isTerminal = true;
        }

        public Node(Node parent, int column, GameBoard.PlayerType player)
        {
            board = parent.board.makeCopy();
            playerType = player;
            value = board.getRating(column, player);
            if (player == GameBoard.PlayerType.PLAYER_B) value *= -1;
            board.insertToken(column, player);

            if (board.getGameOver()) isTerminal = true;

            move = column;
        }

        GameBoard board;
        ArrayList<Node> children = new ArrayList<Node>();
        int value;
        boolean isTerminal = false;
        GameBoard.PlayerType playerType;

        int move;
    }

    private Node root;
    public final int WIDTH;
    public final int HEIGHT;

    GameBoard board;

    public AIEngine(int width, int height)
    {
        WIDTH = width;
        HEIGHT = height;

        board = new GameBoard(WIDTH, HEIGHT);
        root = new Node(board, GameBoard.PlayerType.PLAYER_B);

        addAllMoves();
        addAllMoves();
        addAllMoves();
        addAllMoves();
//        addAllMoves();
    }

    public Node getNextNodeAlphaBeta(Node node, boolean isMax, int alpha, int beta)
    {
        if (node.children.size() == 0) return node;

        Node bestNode = null;

        for (Node child : node.children)
        {
            getNextNodeAlphaBeta(child, !isMax, alpha, beta);
            if (bestNode == null)
            {
                bestNode = child;
            }
            else if (!isMax && child.value > bestNode.value)
            {
                bestNode = child;
            }
            else if (isMax && child.value < bestNode.value)
            {
                bestNode = child;
            }

            if (isMax)
                alpha = Math.max(alpha, bestNode.value);
            else
                beta = Math.min(beta, bestNode.value);

            if (ENABLE_ALPHA_BETA && alpha >= beta)
            {
//                System.out.println("ALPHA > BETA");
                break;
            }
        }

        node.value = bestNode.value;
        return bestNode;
    }

    public void addAllMoves()
    {
        addAllMoves(root);
    }

    public void addAllMoves(Node node)
    {
        if (node.isTerminal) return;

        if (node.children.size() == 0)
        {
            for (int i = 0; i < WIDTH; i++)
            {
                if (!node.board.checkSpaceForToken(i)) continue;

                node.children.add(new Node(node, i, getSecondPlayer(node.playerType)));
            }

            return;
        }

        for (Node child : node.children)
        {
            addAllMoves(child);
        }
    }

    private GameBoard.PlayerType getSecondPlayer(GameBoard.PlayerType player)
    {
        return player == GameBoard.PlayerType.PLAYER_A ? GameBoard.PlayerType.PLAYER_B : GameBoard.PlayerType.PLAYER_A;
    }

    public void opponentMove(int column)
    {
        for (Node child : root.children)
        {
            if (child.move == column)
            {
                root = child;
                break;
            }
        }

        addAllMoves();
        addAllMoves();
    }

    public int getAiMove()
    {
        //displayCurTree();



        Node bestMove = getNextNodeAlphaBeta(root, true, -INFINITY, INFINITY);
        displayCurTree();
        root = bestMove;

        System.out.println("Picked " + bestMove.move + "   with value " + bestMove.value);

        return bestMove.move;
    }







    public void displayCurTree()
    {
        for (int i = 0; i < 5; i++)
        {
            System.out.println("LAYER " + i);

            displayLayer(root, i);
            System.out.println(" ");
        }
    }

    public void displayLayer(Node node, int layer)
    {
        if (layer == 0)
        {
            System.out.print(node.value + ", ");
            return;
        }

        for (Node child : node.children)
        {
            displayLayer(child, layer-1);
        }
    }
}
