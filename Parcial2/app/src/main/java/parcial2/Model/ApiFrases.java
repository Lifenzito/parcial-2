package parcial2.Model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class ApiFrases {
    
    private static List<Quote> obtenerQuotes() {
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
        
        return lista;
    }
    
    private static class Quote { 
        private String q;
        private String a; 

        public String getQ() {
            return q;
        }

        public String getA() {
            return a;
        }

        @Override
        public String toString() {
            return "\"" + q + "\" — " + a;
        }

    }
    public static void ejecutar() {
        List<Quote> quotes = obtenerQuotes();
        
        System.out.println("=== Frases obtenidas del API ===");
        for (Quote q : quotes) {
            System.out.println(q); 
        }
    
        System.out.println("\n=== Encriptación con tu clase Frase ===");
        for (Quote q : quotes) {
            String texto = q.getQ();      
            Frase frase = new Frase(texto); 
            frase.showEncrypted();         
            System.out.println();
        }

        
    }

    
}