package com.alura.comex;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ProcesadorDeCSV {
    public List<Pedido> procesadorArchivo(String nombreArchivo) throws IOException, URISyntaxException{
        Objects.requireNonNull(nombreArchivo, "El nombre del archivo no puede ser nulo");

        List<Pedido> pedidos = new ArrayList<>();
        Path caminoDelArchivo = Path.of(Objects.requireNonNull(
                ClassLoader.getSystemResource(nombreArchivo),
                "El archivo no fue encontrado").toURI()
        );

        try(Scanner lectorDeLineas = new Scanner(caminoDelArchivo)){
            lectorDeLineas.nextLine();

            while (lectorDeLineas.hasNextLine()){
                String linea = lectorDeLineas.nextLine();
                String[] registro = linea.split(",");

                Pedido pedido = crearPedidoDeRegistro(registro);
                pedidos.add(pedido);
            }
        }

        return pedidos;
    }

    private Pedido crearPedidoDeRegistro(String[] registro){
        String categoria = registro[0];
        String producto = registro[1];
        BigDecimal precio = new BigDecimal(registro[2]);
        int cantidad = Integer.parseInt(registro[3]);
        LocalDate fecha = LocalDate.parse(registro[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String cliente = registro[5];

        return new Pedido(categoria, producto, cliente, precio, cantidad, fecha);
    }
}
