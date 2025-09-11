package parcial2.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import parcial2.Model.Frase;
import parcial2.Util.PerformanceMonitor;

public class ApiFrases {
    private static final Logger logger        = LogManager.getLogger(ApiFrases.class.getName());
    private static final Logger loggerTiempos = LogManager.getLogger("tiempos");
    private static List<Quote> obtenerQuotes() {
        PerformanceMonitor monitor = new PerformanceMonitor("ApiFrases.obtenerQuotes");
        monitor.inicio();

        String apiUrl = "https://zenquotes.io/api/quotes";
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        
        List<Quote> lista = new ArrayList<>();
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl))
            .header("Accept", "application/json")
            .GET()
            .build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                Quote[] arr = gson.fromJson(response.body(), Quote[].class);
                if (arr != null) {
                    for (Quote q : arr) {
                        if (q != null && q.getQ() != null && !q.getQ().isEmpty()) {
                            lista.add(q);
                        }
                    }
                }
            } else {
                System.out.println("Error API: " + response.statusCode());
            }
            
        } catch (Exception e) {
            System.out.println("Error al consumir el API: " + e.getMessage());
        }
        monitor.finalizado();
        return lista;
    }
    
    private static class Quote { 
        private String q;
        private String a; 

        public String getQ() {
            try {
                return q;
            } catch (Exception e) {
                logger.error("Error en getQ(): " + e.getMessage());
                return null;
            }
        }

        @Override
        public String toString() {
            return "\"" + q + "\" — " + a;
        }

    }
    public static void ejecutar() {
        try {
            PerformanceMonitor monitor = new PerformanceMonitor("ApiFrases.ejecutar");
            monitor.inicio();

            loggerTiempos.info("ApiFrases.ejecutar: INICIO");

            List<Quote> quotes = obtenerQuotes();
            logger.info("Se obtuvieron {} frases del API para procesar.", quotes.size());

            System.out.println("=== Frases obtenidas del API ===");
            for (Quote q : quotes) {
                logger.info("Imprimiendo frase obtenida: {}", q);
                System.out.println(q);
            }

            System.out.println("\n=== Encriptacion con tu clase Frase ===");
            for (Quote q : quotes) {
                logger.info("Procesando frase para encriptar y desencriptar: {}", q.getQ());

                String texto = q.getQ();
                Frase frase = new Frase(texto);

                logger.info("Mostrando mensaje encriptado en consola...");
                frase.showEncrypted();
                System.out.println();

                logger.info("Mostrando mensaje desencriptado en consola...");
                frase.showDecrypted();
                System.out.println();

                logger.info("Ronda Completada para frase: {}", q.getQ());
            }

            loggerTiempos.info("ApiFrases.ejecutar: FIN");
            monitor.finalizado();
        } catch (Exception e) {
            logger.error("Error en ejecutar(): " + e.getMessage());
        }
    }

}