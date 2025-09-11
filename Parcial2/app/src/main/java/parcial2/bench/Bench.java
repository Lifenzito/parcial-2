package parcial2.bench;

import parcial2.Model.Frase;


//esta clase se creo con el fin de hacer el pnto 6 del parcial, En el proyecto se implementaron mediciones de 
//iempo mediante las 
//clases PerformanceMonitor y LoggerTiempos, las cuales registran la duración de los procesos principales 
// pero se vio necesaro crear esta clase ya que permite generar frases de distintos tamaños, repetir múltiples 
//mediciones y obtener promedios
//asi los loggers cumplen su función de seguimiento interno, mientras que la clase Bench facilita la
//obtención de datos comparativos para graficar la relación entre la cantidad de caracteres y el tiempo de ejecución.
public class Bench {
    private static long medirTiempo(String texto, int repeticiones) {
        long total = 0;
        for (int i = 0; i < repeticiones; i++) {
            long t0 = System.nanoTime();
            new Frase(texto); 
            long t1 = System.nanoTime();
            total += (t1 - t0);
        }
        return total / repeticiones;
    }

    public static void main(String[] args) {
        int[] tamaños = {100, 300, 600, 1000, 1500, 2000, 3000, 4000, 5000};
        int repeticiones = 10;

        System.out.println("Tamaño,Tiempo(ns)");
        for (int n : tamaños) {
            String texto = "A".repeat(n); 
            long promedio = medirTiempo(texto, repeticiones);
            System.out.println(n + "," + promedio);
        }
    }
}