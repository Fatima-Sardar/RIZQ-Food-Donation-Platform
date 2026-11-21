package rizq.ds;

public class Stack<T> {

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> top;

    // Add an item to the stack
    public void push(T value) {
        Node<T> newNode = new Node<>(value);
        newNode.next = top;
        top = newNode;
    }

    // Remove the latest item
    public T pop() {
        if (top == null) {
            return null;
        }

        T value = top.data;
        top = top.next;
        return value;
    }

    // View the latest item
    public T peek() {
        return top == null ? null : top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }
}