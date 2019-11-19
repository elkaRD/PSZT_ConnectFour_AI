import java.util.ArrayList;

public class MinMax
{
    public class Node
    {
        public Node()
        {

        }

        public Node(int value)
        {
            this.value = value;
        }

        public void add(int value)
        {
            children.add(new Node(value));
        }

        public ArrayList<Node> children = new ArrayList<Node>();
        public int value;
    }

    private Node root;

    public MinMax()
    {
        root = new Node();

        Node l = new Node();
        Node r = new Node();
        Node ll = new Node();
        Node lll = new Node();
        Node llr = new Node();
        Node lr = new Node();
        Node lrl = new Node();
        Node lrr = new Node();

        Node rl = new Node();
        Node rll = new Node();
        Node rlr = new Node();
        Node rr = new Node();
        Node rrl = new Node();
        Node rrr = new Node();

        root.children.add(l);
        root.children.add(r);

        l.children.add(ll);
        l.children.add(lr);

        r.children.add(rl);
        r.children.add(rr);

        ll.children.add(lll);
        ll.children.add(llr);

        lr.children.add(lrl);
        lr.children.add(lrr);

        rl.children.add(rll);
        rl.children.add(rlr);

        rr.children.add(rrl);
        rr.children.add(rrr);

        lll.value = 3;
        llr.value = 5;
        lrl.value = 6;
        lrr.value = 9;

        rll.value = 1;
        rlr.value = 2;
        rrl.value = 0;
        rrr.value = -1;

        Node temp = getNextNode2(root, true);
        Node temp2 = getNextNodeAlphaBeta(root, true, -1000, 1000);

        System.out.println("GOT VALUE FROM MIN-MAX: " + temp.value);
        System.out.println("GOT VALUE FROM ALPHA_BETA: " + temp2.value);
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

        Node bestNode = node.children.get(0);
        getNextNodeAlphaBeta(bestNode, !isMax, alpha, beta);

        if (isMax)
            alpha = Math.max(alpha, bestNode.value);
        else
            beta = Math.min(beta, bestNode.value);

        System.out.println("DEBUG " + node.children.get(0).value + ": " + alpha + ", " + beta);

        if (alpha < beta)
        for (int i = 1; i < node.children.size(); i++)
        {
            getNextNodeAlphaBeta(node.children.get(i), !isMax, alpha, beta);
            if (isMax && node.children.get(i).value > bestNode.value)
            {
                bestNode = node.children.get(i);
            }
            else if (!isMax && node.children.get(i).value < bestNode.value)
            {
                bestNode = node.children.get(i);
            }

            if (isMax)
                alpha = Math.max(alpha, bestNode.value);
            else
                beta = Math.min(beta, bestNode.value);

            System.out.println("DEBUG " + node.children.get(i).value + ": " + alpha + ", " + beta);

            if (alpha >= beta)
            {
                System.out.println("ALPHA > BETA");
                break;
            }
        }
        else
            System.out.println("2ALPHA > BETA");

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
