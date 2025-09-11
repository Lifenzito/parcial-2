package parcial2.Model;

import java.util.ArrayDeque;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import parcial2.Util.PerformanceMonitor;

public class Frase {
    private static final Logger logger = LogManager.getLogger(Frase.class.getName());
    private static final Logger loggerTiempos = LogManager.getLogger("tiempos");

    private final String original;
    private final ArrayDeque<SimpleLinkedList> PalabrasEncriptadas;

    public Frase(String text) {
        this.original = text;
        this.PalabrasEncriptadas = new ArrayDeque<>();
        try {
            logger.info("Frase creada con texto original = \"{}\"", text);
            encriptar();
        } catch (Exception e) {
            logger.error("Error en la creación de la frase o en la encriptación: ", e);
        }
    }

    private void encriptar() {
        PerformanceMonitor monitor = new PerformanceMonitor("Frase.encriptar");
        try {
            monitor.inicio();

            loggerTiempos.info("Frase.encriptar: INICIO");
            String[] words = original.split(" ");
            int odd;

            for (int wi = 0; wi < words.length; wi++) {
                String w = words[wi];
                logger.info("Encriptando palabra#{} = \"{}\"", wi, w);

                SimpleLinkedList list = new SimpleLinkedList();
                odd = 1;

                for (int ci = 0; ci < w.length(); ci++) {
                    char c = w.charAt(ci);
                    int valor = ((int) c) + odd;
                    logger.info(" char#{} '{}' -> ascii={} + odd={} = {}", ci, c, (int) c, odd, valor);
                    list.add(valor);
                    odd += 2;
                }

                list.swapAdjacent();
                logger.info(" Palabra#{} rotada por pares y lista enlazada lista.", wi);

                PalabrasEncriptadas.addLast(list);
                logger.info(" Palabra#{} encolada en ArrayDeque. Deque size={}", wi, PalabrasEncriptadas.size());
            }

            loggerTiempos.info("Frase.encriptar: FIN");
        } catch (Exception e) {
            logger.error("Error durante la encriptación: ", e);
        } finally {
            monitor.finalizado();
        }
    }

    public String decrypt() {
        PerformanceMonitor monitor = new PerformanceMonitor("Frase.decrypt");
        String result = "";
        try {
            monitor.inicio();

            loggerTiempos.info("Frase.decrypt: INICIO");
            StringBuilder message = new StringBuilder();

            int wi = 0;
            for (SimpleLinkedList encList : PalabrasEncriptadas) {
                logger.info("Procesando palabra en deque idx={}", wi);

                encList.swapAdjacent();
                logger.info(" Palabra idx={} revertida con swapAdjacent().", wi);

                int[] vals = encList.toIntArray();
                StringBuilder word = new StringBuilder(vals.length);
                int odd = 1;
                for (int i = 0; i < vals.length; i++) {
                    int ascii = vals[i] - odd;
                    logger.info("  pos#{} valor={} - odd={} -> ascii={} ('{}')", i, vals[i], odd, ascii, (char) ascii);
                    word.append((char) ascii);
                    odd += 2;
                }

                if (message.length() > 0) message.append(' ');
                message.append(word);
                logger.info(" Palabra idx={} desencriptada = \"{}\"", wi, word);

                wi++;
            }

            logger.info("Mensaje completo reconstruido = \"{}\"", message);
            loggerTiempos.info("Frase.decrypt: FIN");

            result = message.toString();
        } catch (Exception e) {
            logger.error("Error durante la desencriptación: ", e);
        } finally {
            monitor.finalizado();
        }

        return result;
    }

    public void showEncrypted() {
        try {
            logger.info("Mostrando estado encriptado (listas enlazadas):");
            System.out.println("Mensaje encriptado:");
            for (SimpleLinkedList l : PalabrasEncriptadas) {
                System.out.println(l);
            }
            System.out.println(PalabrasEncriptadas);
        } catch (Exception e) {
            logger.error("Error al mostrar el estado encriptado: ", e);
        }
    }

    public void showDecrypted() {
        try {
            logger.info("Mostrando mensaje desencriptado:");
            System.out.println("Mensaje desencriptado:");
            System.out.println(decrypt());
        } catch (Exception e) {
            logger.error("Error al mostrar el mensaje desencriptado: ", e);
        }
    }
}