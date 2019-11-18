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

        lll.value = 5;
        llr.value = 2;
        lrl.value = 6;
        lrr.value = 10;

        rll.value = 7;
        rlr.value = 8;
        rrl.value = 9;
        rrr.value = 12;

        Node temp = getNextNode2(root, true);

        System.out.println("GOT VALUE FROM MIN-MAX: " + temp.value);
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
