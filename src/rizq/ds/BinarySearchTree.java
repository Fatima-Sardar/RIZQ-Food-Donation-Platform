package rizq.ds;

public class BinarySearchTree {

    private static class Node {
        int key;
        Node left;
        Node right;

        Node(int key) {
            this.key = key;
        }
    }

    private Node root;

    public void insert(int key) {
        root = insertNode(root, key);
    }

    private Node insertNode(Node node, int key) {

        if (node == null) {
            return new Node(key);
        }

        if (key < node.key) {
            node.left = insertNode(node.left, key);
        } else if (key > node.key) {
            node.right = insertNode(node.right, key);
        }

        return node;
    }

    public boolean search(int key) {
        Node current = root;

        while (current != null) {

            if (key == current.key) {
                return true;
            }

            if (key < current.key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return false;
    }

    public void displayInOrder() {
        display(root);
        System.out.println();
    }

    private void display(Node node) {
        if (node != null) {
            display(node.left);
            System.out.print(node.key + " ");
            display(node.right);
        }
    }
}