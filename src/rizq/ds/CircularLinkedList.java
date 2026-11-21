package rizq.ds;

public class CircularLinkedList<T> {

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> tail;
    private Node<T> current;

    public void add(T value) {
        Node<T> newNode = new Node<>(value);

        if (tail == null) {
            tail = newNode;
            newNode.next = newNode;
            current = newNode;
        } else {
            newNode.next = tail.next;
            tail.next = newNode;
            tail = newNode;
        }
    }

    // Move to the next item circularly
    public T next() {
        if (current == null) {
            return null;
        }

        T value = current.data;
        current = current.next;
        return value;
    }

    public boolean isEmpty() {
        return tail == null;
    }

    public void displayOnce() {
        if (tail == null) {
            return;
        }

        Node<T> node = tail.next;

        do {
            System.out.println(node.data);
            node = node.next;
        } while (node != tail.next);
    }
}
