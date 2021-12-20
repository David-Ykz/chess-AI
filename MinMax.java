import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

public class MinMax {
    static Queue<Node> queue = new LinkedList<>();
    static Node selectedNode = null;

    public static void createTree(int width, Node node) {
        if (node.depth == 0) {
            return;
        }
        System.out.print("node: " + node.value + ",");
        for (int i = 0; i < width; i++) {
            int sign;
            if (Math.random() > 0.5) {
                sign = 1;
            } else {
                sign = -1;
            }
            int value = node.value - (int)(sign * Math.random() * 10);
            System.out.print(" next: " + value);
            Node newNode = new Node(value, node, node.depth - 1);
            node.next.add(newNode);
            queue.add(newNode);
        }
        System.out.println(" depth: " + node.depth);
        while (!queue.isEmpty()) {
            createTree(width, queue.remove());
        }
    }


    public static void traverseTree(Queue<Node> levelQueue) {
        Queue<Node> childQueue = new LinkedList<>();
        while (!levelQueue.isEmpty()) {
            Node node = levelQueue.remove();
            System.out.print(node.value + " ");
            if (!node.next.isEmpty()) {
                childQueue.addAll(node.next);
            }
        }
        System.out.println();
        if (childQueue.isEmpty()) { return; }
        traverseTree(childQueue);
    }


    public static int findMax(ArrayList<Node> nodes) {
        Node largestNode = nodes.get(0);
        for (Node node : nodes) {
            if (node.value > largestNode.value) {
                largestNode = node;
            }
        }
        return largestNode.value;
    }

    public static int findMin(ArrayList<Node> nodes) {
        Node smallestNode = nodes.get(0);
        for (Node node : nodes) {
            if (node.value < smallestNode.value) {
                smallestNode = node;
            }
        }
        return smallestNode.value;
    }

    public static void findBest(Node node, int maxmin, int depth) {
        if (depth == 0) {
            return;
        }
        findBest(node, -maxmin, depth - 1);
        if (maxmin > 0) {
            node.value = findMax(node.next);
        } else if (maxmin < 0) {
            node.value = findMin(node.next);
        }
    }




    public static void main(String[]args) {
        Node root = new Node(0, null, 3);
        createTree(3, root);
        Queue<Node> levelQueue = new LinkedList<>();
        levelQueue.add(root);
        traverseTree(levelQueue);

        findBest(root, 1, 3);
        System.out.println("Search Value: " + root.value);




    }
}

class Node {
    int value;
    Node parent;
    ArrayList<Node> next = new ArrayList<>();
    int depth;
    Node(int value, Node parent, int depth) {
        this.value = value;
        this.parent = parent;
        this.depth = depth;
//        this.next = next;
    }
}


