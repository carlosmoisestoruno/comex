package com.alura.comex;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        /*
            Al extraer la lógica de lectura del archivo CSV de la clase Main a una nueva clase llamada
            ProcesadorDeCsv, favorecemos el principio "Single Responsibility Principle" (SRP), uno de los
            cinco principios SOLID.

            La clase Main se enfoca en orquestar el flujo de la aplicación.
            Mientras que la clase ProcesadorDeCsv se especializa en procesar archivos CSV y convertirlos en objetos.
        */
        ProcesadorDeCSV procesadorDeCSV = new ProcesadorDeCSV();
        List<Pedido> pedidos = new ArrayList<>();

        try {
            URL recursoCSV = ClassLoader.getSystemResource("pedidos.csv");
            Path caminoDelArchivo = Path.of(recursoCSV.toURI());

            Scanner lectorDeLineas = new Scanner(caminoDelArchivo);
            lectorDeLineas.nextLine();

                while (lectorDeLineas.hasNextLine()) {
                    String linea = lectorDeLineas.nextLine();
                    String[] registro = linea.split(",");

                    String categoria = registro[0];
                    String producto = registro[1];
                    BigDecimal precio = new BigDecimal(registro[2]);
                    int cantidad = Integer.parseInt(registro[3]);
                    LocalDate fecha = LocalDate.parse(registro[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String cliente = registro[5];

                    Pedido pedido = new Pedido(categoria, producto, cliente, precio, cantidad, fecha);
                    pedidos.add(pedido);
                }
            } catch (URISyntaxException e) {
                throw new RuntimeException("Archivo pedido.csv no localizado!");
            } catch (IOException e) {
                throw new RuntimeException("Error al abrir Scanner para procesar archivo!");
            }

        // Crear y utilizar la clase InformeSintetico para asignar responsabilidades unicas
        InformeSintetico informe = new InformeSintetico(pedidos);
        informe.imprimirInforme();
    }
}
