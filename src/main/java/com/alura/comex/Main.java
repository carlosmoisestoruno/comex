package com.alura.comex;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        /*
            Al extraer la lógica de lectura del archivo CSV de la clase Main a una nueva clase llamada
            ProcesadorDeCsv, favorecemos el principio "Single Responsibility Principle" (SRP), uno de los
            cinco principios SOLID.

            La clase Main se enfoca en orquestar el flujo de la aplicación.
            Mientras que la clase ProcesadorDeCsv se especializa en procesar archivos CSV y convertirlos en objetos.
        */
        ProcesadorDeCSV procesadorDeCSV = new ProcesadorDeCSV();
        List<Pedido> pedidos;

        try {
            pedidos = procesadorDeCSV.procesadorArchivo("pedidos.csv");
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Error procesando el archivo CSV", e);
        }

        // Crear y utilizar la clase InformeSintetico para asignar responsabilidades unicas
        InformeSintetico informe = new InformeSintetico(pedidos);
        informe.imprimirInforme();
    }
}
