package rizq.ds;

public class SinglyLinkedList<T> {

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private Node<T> tail;

    public void add(T value) {
        Node<T> newNode = new Node<>(value);

        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public boolean contains(T value) {
        Node<T> current = head;

        while (current != null) {
            if (current.data.equals(value)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void display() {
        Node<T> current = head;

        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
}