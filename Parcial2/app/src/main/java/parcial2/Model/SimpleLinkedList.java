package parcial2.Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import parcial2.Util.PerformanceMonitor;

public class SimpleLinkedList {
    // Loggers
    private static final Logger logger = LogManager.getLogger(SimpleLinkedList.class.getName());
    private static final Logger loggerTiempos = LogManager.getLogger("tiempos");

    private Node head;
    private int size;

    public void add(int valor) {
        PerformanceMonitor monitor = new PerformanceMonitor("SimpleLinkedList.add");
        try {
            monitor.inicio();

            loggerTiempos.info("SimpleLinkedList.add: INICIO");
            logger.info("Add: se va a insertar el valor {} {}", valor, (head == null ? "(lista vacía)" : "(al final)"));

            Node newNode = new Node(valor);
            if (head == null) {
                head = newNode;
                size++;
                logger.info("Add: insertado como primer nodo. size={}", size);
                loggerTiempos.info("SimpleLinkedList.add: FIN");
                monitor.finalizado();
                return;
            }

            Node Lol = head;
            int pos = 0;
            while (Lol.next != null) { Lol = Lol.next; pos++; }
            Lol.next = newNode;
            size++;

            logger.info("Add: insertado al final (posición tail={}). size={}", pos + 1, size);
            loggerTiempos.info("SimpleLinkedList.add: FIN");
        } catch (Exception e) {
            logger.error("Error al agregar el valor {} a la lista: ", valor, e);
        } finally {
            monitor.finalizado();
        }
    }

    public void swapAdjacent() {
        PerformanceMonitor monitor = new PerformanceMonitor("SimpleLinkedList.swapAdjacent");
        try {
            monitor.inicio();

            loggerTiempos.info("SimpleLinkedList.swapAdjacent: INICIO");
            if (head == null || head.next == null) {
                logger.warn("SwapAdjacent: no hay suficientes nodos para intercambiar (size={}).", size);
                loggerTiempos.info("SimpleLinkedList.swapAdjacent: FIN (trivial)");
                monitor.finalizado();
                return;
            }

            Node Imaginario = new Node(0);
            Imaginario.next = head;
            Node prev = Imaginario;

            int par = 0;
            while (prev.next != null && prev.next.next != null) {
                Node first = prev.next;
                Node second = first.next;
                Node nextPair = second.next;

                logger.info("SwapAdjacent: intercambiando par #{} -> [{} , {}]", par, first.data, second.data);

                prev.next = second;
                second.next = first;
                first.next = nextPair;

                prev = first;
                par++;
            }
            head = Imaginario.next;

            logger.info("SwapAdjacent: intercambio finalizado. Pares procesados={}", par);
            loggerTiempos.info("SimpleLinkedList.swapAdjacent: FIN");
        } catch (Exception e) {
            logger.error("Error durante el intercambio de nodos adyacentes: ", e);
        } finally {
            monitor.finalizado();
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int[] toIntArray() {
        PerformanceMonitor monitor = new PerformanceMonitor("SimpleLinkedList.toIntArray");
        int[] out = null;
        try {
            monitor.inicio();

            loggerTiempos.info("SimpleLinkedList.toIntArray: INICIO");
            out = new int[size];
            Node Lol = head;
            int i = 0;
            while (Lol != null) {
                out[i++] = Lol.data;
                Lol = Lol.next;
            }
            logger.info("toIntArray: arreglo generado con length={}", out.length);
            loggerTiempos.info("SimpleLinkedList.toIntArray: FIN");
        } catch (Exception e) {
            logger.error("Error al convertir la lista a arreglo: ", e);
        } finally {
            monitor.finalizado();
        }
        return out;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        SimpleLinkedList other = (SimpleLinkedList) obj;
        Node a = this.head, b = other.head;

        while (a != null && b != null) {
            if (a.data != b.data) return false;
            a = a.next; b = b.next;
        }
        return a == null && b == null;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        Node Lol = head;
        while (Lol != null) {
            hash = 31 * hash + Lol.data;
            Lol = Lol.next;
        }
        return hash;
    }
}