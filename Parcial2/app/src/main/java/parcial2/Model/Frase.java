package parcial2.Model;

import java.util.ArrayDeque;

public class Frase {
    private final String original;
    private final ArrayDeque<SimpleLinkedList> PalabrasEncriptadas;

    public Frase(String text) {
        this.original = text;
        this.PalabrasEncriptadas = new ArrayDeque<>();
        encriptar();
    }

    private void encriptar() {
        String[] words = original.split(" ");
        int odd = 1;

        for (String w : words) {
            SimpleLinkedList list = new SimpleLinkedList();
            for (char c : w.toCharArray()) {
                list.add((int) c + odd);
                odd += 2;
            }
            list.swapAdjacent();
            PalabrasEncriptadas.addLast(list);
        }
    }

    public void showEncrypted() {
        System.out.println("Mensaje encriptado:");
        for (SimpleLinkedList l : PalabrasEncriptadas) {
            System.out.println(l);
        }

        //SE IMPRIME PARA QUE SE VEA LA IMPLEMENTACION DE LAS LINKEDLIST EN EL ARRAYDEQUE( Y COMO SE ALMACENAN), NO ES MALA PRACTICA >:)
        System.out.println(PalabrasEncriptadas);
    }
}
