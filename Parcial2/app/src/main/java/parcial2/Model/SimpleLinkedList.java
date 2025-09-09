package parcial2.Model;

public class SimpleLinkedList {
    private Node head;

    public void add(int valor) {
        Node newNode = new Node(valor);
        if (head == null) {
            head = newNode;
            return;
        }
        Node Lol = head;
        while (Lol.next != null) Lol = Lol.next;
        Lol.next = newNode;
    }

    public void swapAdjacent() {
        if (head == null || head.next == null) return;
        Node imaginario = new Node(0);
        imaginario.next = head;
        Node prev = imaginario;

        while (prev.next != null && prev.next.next != null) {
            Node first = prev.next;
            Node second = first.next;
            Node nextPair = second.next;

            prev.next = second;
            second.next = first;
            first.next = nextPair;

            prev = first;
        }
        head = imaginario.next;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node Lol = head;
        while (Lol != null) {
            sb.append(Lol.data);
            if (Lol.next != null) sb.append(" -> ");
            Lol = Lol.next;
        }
        return sb.toString();
    }
}
