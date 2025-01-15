package com.alura.comex;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        int totalDeProductosVendidos = 0;
        int totalDePedidosRealizados = 0;
        BigDecimal montoDeVentas = BigDecimal.ZERO;
        Pedido pedidoMasBarato = null;
        Pedido pedidoMasCaro = null;

        CategoriasProcesadas categoriasProcesadas = new CategoriasProcesadas();
        int totalDeCategorias = 0;

        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedidoActual = pedidos.get(i);

            if (pedidoActual == null) {
                break;
            }

            //Simplificamos el código
            if (pedidoMasBarato == null || pedidoActual.isMasBaratoQue(pedidoMasBarato)) {
                pedidoMasBarato = pedidoActual;
            }

            if (pedidoMasCaro == null || pedidoActual.isMasCaroQue(pedidoMasCaro)) {
                pedidoMasCaro = pedidoActual;
            }

            montoDeVentas = montoDeVentas.add(pedidoActual.getPrecio().multiply(new BigDecimal(pedidoActual.getCantidad())));
            totalDeProductosVendidos += pedidoActual.getCantidad();
            totalDePedidosRealizados++;

            if (!categoriasProcesadas.contains(pedidoActual.getCategoria())) {
              totalDeCategorias++;
              categoriasProcesadas.add(pedidoActual.getCategoria());
            }
        }

        System.out.println("#### INFORME DE VALORES TOTALES");
        System.out.printf("- TOTAL DE PEDIDOS REALIZADOS: %s\n", totalDePedidosRealizados);
        System.out.printf("- TOTAL DE PRODUCTOS VENDIDOS: %s\n", totalDeProductosVendidos);
        System.out.printf("- TOTAL DE CATEGORIAS: %s\n", totalDeCategorias);
        System.out.printf("- MONTO DE VENTAS: %s\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(montoDeVentas.setScale(2, RoundingMode.HALF_DOWN))); //Pueden cambiar el Locale a la moneda de su pais, siguiendo esta documentación: https://www.oracle.com/java/technologies/javase/java8locales.html
        System.out.printf("- PEDIDO MAS BARATO: %s (%s)\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(pedidoMasBarato.getPrecio().multiply(new BigDecimal(pedidoMasBarato.getCantidad())).setScale(2, RoundingMode.HALF_DOWN)), pedidoMasBarato.getProducto());
        System.out.printf("- PEDIDO MAS CARO: %s (%s)\n", NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(pedidoMasCaro.getPrecio().multiply(new BigDecimal(pedidoMasCaro.getCantidad())).setScale(2, RoundingMode.HALF_DOWN)), pedidoMasCaro.getProducto());
    }
}
