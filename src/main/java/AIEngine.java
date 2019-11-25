import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AIEngine
{
    public static final int INFINITY = 1000000000;
    public static final int CLOSER_VICTORY = 1000000;

    public static final boolean ENABLE_ALPHA_BETA = true;
    public static final boolean ENABLE_SORTING_CHILDREN_WITHIN_NODE = true;

    private final int WIDTH;
    private final int HEIGHT;

    private Node root;

    public class Node
    {
        public Node(GameBoard.PlayerType player)
        {
            board = new GameBoard(WIDTH, HEIGHT);
            playerType = player;
        }

        public Node(Node parent, int column, GameBoard.PlayerType player)
        {
            board = parent.board.makeCopy();
            playerType = player;
            value = board.getRating(column, player);
            if (player == GameBoard.PlayerType.PLAYER_A) value *= -1;
            board.insertToken(column, player);

            if (board.getGameOver()) isTerminal = true;
            move = column;
        }

        GameBoard board;
        ArrayList<Node> children = new ArrayList<Node>();

        boolean isTerminal = false;
        GameBoard.PlayerType playerType;
        int move;
        int value;
    }

    public AIEngine(int width, int height)
    {
        WIDTH = width;
        HEIGHT = height;

        root = new Node(GameBoard.PlayerType.PLAYER_B);

        addAllMoves();
        addAllMoves();
        addAllMoves();
        addAllMoves();
        addAllMoves();
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
            else if (!isMax && child.value < bestNode.value)
            {
                bestNode = child;
            }
            else if (isMax && child.value > bestNode.value)
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
        if (node.isTerminal)
        {
            if (node.board.getWinner() == GameBoard.PlayerType.PLAYER_B)
                node.value += CLOSER_VICTORY;
            else
                node.value -= CLOSER_VICTORY;
            return;
        }

        if (node.children.size() == 0)
        {
            for (int i = 0; i < WIDTH; i++)
            {
                if (!node.board.checkSpaceForToken(i)) continue;

                node.children.add(new Node(node, i, getSecondPlayer(node.playerType)));
            }

            if (ENABLE_SORTING_CHILDREN_WITHIN_NODE)
                sortChildrenByValue(node);

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
        Node bestMove = getNextNodeAlphaBeta(root, true, -INFINITY, INFINITY);
//        DebugUI.displayCurTree(this, 8);
        root = bestMove;

        System.out.println("Picked " + bestMove.move + "   with value " + bestMove.value);

        return bestMove.move;
    }

    public Node debugGetRoot()
    {
        return root;
    }

    private void sortChildrenByValue(Node node)
    {
        final int reverse = node.playerType == GameBoard.PlayerType.PLAYER_A ? -1 : 1;

        Collections.sort(node.children, new Comparator<Node>()
        {
            @Override
            public int compare(final Node object1, final Node object2)
            {
                if (object1.value < object2.value) return -1 * reverse;
                if (object1.value > object2.value) return 1 * reverse;
                return 0;
            }
        });
    }
}
