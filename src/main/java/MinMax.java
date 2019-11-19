import sun.jvm.hotspot.debugger.windbg.WindbgDebugger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MinMax
{
    public class Node
    {
        public Node() //REMOVE IT
        {

        }

        public Node(Node parent, int move, boolean playerType)
        {
            ConnectFourLogic.PlayerType player = playerType ? ConnectFourLogic.PlayerType.PLAYER_A : ConnectFourLogic.PlayerType.PLAYER_B;

            board = parent.board.makeCopy();
            board.insertToken(move, player);

            value = board.getRatingForPlayer(player);

            board.debugDisplay();

            if (!playerType) value *= -1;
        }

        public Node(int value)
        {
            this.value = value;
        } // REMOVE IT, assign value in default constructor

        public void add(int value)
        {
            children.add(new Node(value));
        }

        public ArrayList<Node> children = new ArrayList<Node>();
        public int value;

        public ConnectFourLogic board;
        public int move;
    }

    Queue<Node> q = new LinkedList<Node>();

    public void displayCurTree()
    {
//        q.clear();
//        displayCurTree(root);

        for (int i = 0; i < 3; i++)
        {
            System.out.println("LAYER " + i);

            displayLayer(root, i);
            System.out.println(" ");
        }
    }

    public void displayCurTree(Node node)
    {

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

    private Node root;

    public final int WIDTH;

    public MinMax(int width)
    {
        WIDTH = width;

        root = new Node();
        root.board = new ConnectFourLogic(WIDTH, 8);
//        addAllMoves(root, true);
//        addAllMoves(root, false);

        addAllMoves(root, true);

        displayCurTree();

        Node temp = getNextNode2(root, true);
        Node temp2 = getNextNodeAlphaBeta(root, true, -1000, 1000);

        System.out.println("GOT VALUE FROM MIN-MAX: " + temp.value);
        System.out.println("GOT VALUE FROM ALPHA_BETA: " + temp2.value);

//        WIDTH = width;
//
//        root = new Node();
//
//        Node l = new Node();
//        Node r = new Node();
//        Node ll = new Node();
//        Node lll = new Node();
//        Node llr = new Node();
//        Node lr = new Node();
//        Node lrl = new Node();
//        Node lrr = new Node();
//
//        Node rl = new Node();
//        Node rll = new Node();
//        Node rlr = new Node();
//        Node rr = new Node();
//        Node rrl = new Node();
//        Node rrr = new Node();
//
//        root.children.add(l);
//        root.children.add(r);
//
//        l.children.add(ll);
//        l.children.add(lr);
//
//        r.children.add(rl);
//        r.children.add(rr);
//
//        ll.children.add(lll);
//        ll.children.add(llr);
//
//        lr.children.add(lrl);
//        lr.children.add(lrr);
//
//        rl.children.add(rll);
//        rl.children.add(rlr);
//
//        rr.children.add(rrl);
//        rr.children.add(rrr);
//
//        lll.value = 3;
//        llr.value = 5;
//        lrl.value = 6;
//        lrr.value = 9;
//
//        rll.value = 1;
//        rlr.value = 2;
//        rrl.value = 0;
//        rrr.value = -1;
//
//        Node temp = getNextNode2(root, true);
//        Node temp2 = getNextNodeAlphaBeta(root, true, -1000, 1000);
//
//        System.out.println("GOT VALUE FROM MIN-MAX: " + temp.value);
//        System.out.println("GOT VALUE FROM ALPHA_BETA: " + temp2.value);
    }

    public void addAllMoves(Node node, boolean isMax)
    {
        if (node.children.size() == 0)
        {
            for (int i = 0; i < WIDTH; i++)
            {
                if (!node.board.checkSpaceForToken(i)) continue;

                node.children.add(new Node(node, i, !isMax));
            }

            return;
        }

        for (Node child : node.children)
        {
            addAllMoves(child, !isMax);
        }
    }

    public void addAllMoves()
    {
        for (int i = 0; i < WIDTH; i++)
            addMove(root, i);
    }

    public void addMove(Node node, int move)
    {
        if (node.children.size() == 0)
        {


            return;
        }

        for (Node child : node.children)
        {
            addMove(child, move);
        }
    }

//    public Node getNextNode(Node node, boolean isMax)
//    {
//        Node bestNode = node.children.get(0);
//
//        for (int i = 1; i < node.children.size(); i++)
//        {
//            if (isMax && node.children.get(i).value > bestNode.value)
//            {
//                bestNode = node.children.get(i);
//            }
//            else if (!isMax && node.children.get(i).value < bestNode.value)
//            {
//                bestNode = node.children.get(i);
//            }
//        }
//
//        return bestNode;
//    }

    public Node getNextNodeAlphaBeta(Node node, boolean isMax, int alpha, int beta)
    {
        if (node.children.size() == 0) return node;

        Node bestNode = null;

//        for (int i = 0; i < node.children.size(); i++)
        for (Node child : node.children)
        {
            getNextNodeAlphaBeta(child, !isMax, alpha, beta);
            if (bestNode == null)
            {
                bestNode = child;
            }
            else if (isMax && child.value > bestNode.value)
            {
                bestNode = child;
            }
            else if (!isMax && child.value < bestNode.value)
            {
                bestNode = child;
            }

            if (isMax)
                alpha = Math.max(alpha, bestNode.value);
            else
                beta = Math.min(beta, bestNode.value);

//            System.out.println("DEBUG " + node.children.get(i).value + ": " + alpha + ", " + beta);

            if (alpha >= beta)
            {
//                System.out.println("ALPHA > BETA");
                break;
            }
        }

        node.value = bestNode.value;
        return bestNode;
    }

    public Node getNextNode2(Node node, boolean isMax)
    {
        if (node.children.size() == 0) return node;

        Node bestNode = node.children.get(0);
        getNextNode2(bestNode, !isMax);

        for (int i = 1; i < node.children.size(); i++)
        {
            getNextNode2(node.children.get(i), !isMax);
            if (isMax && node.children.get(i).value > bestNode.value)
            {
                bestNode = node.children.get(i);
            }
            else if (!isMax && node.children.get(i).value < bestNode.value)
            {
                bestNode = node.children.get(i);
            }
        }

        node.value = bestNode.value;
        return bestNode;
    }
}
