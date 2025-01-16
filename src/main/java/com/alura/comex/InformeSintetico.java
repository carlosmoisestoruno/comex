package com.alura.comex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class InformeSintetico {
    private final int totalDePedidosRealizados;
    private final int totalDeProductosVendidos;
    private final int totalDeCategorias;
    private final BigDecimal montoDeVentas;
    private final Pedido pedidoMasBarato;
    private final Pedido pedidoMasCaro;

    public InformeSintetico(List<Pedido> pedidos) {
        //Refactorizamos el cálculo del indicador de "total de pedidos realizados"
        this.totalDePedidosRealizados = calcularTotalDePedidosRealizados(pedidos);
<<<<<<< HEAD

        //Refactirizamos el cálculo del indicador del indicador de "total de productos vendidos"
        this.totalDeProductosVendidos = calcularTotalDeProductosVendidos(pedidos);

=======
        int productosVendidos = 0;
>>>>>>> a4f7eaa8cbb4798bd90f97a1e0ec23adfc1fb71d
        BigDecimal montoVentas = BigDecimal.ZERO;
        Pedido masBarato = null;
        Pedido masCaro = null;

        //Encapsulamos el cálculo y la representación del informe sintético, alineándolo con el Principio de
        //Responsabilidad Única (SRP) y simplificando la clase Main.
        Set<String> categoriasProcesadas = new HashSet<>();

        for (Pedido pedido : pedidos) {
            montoVentas = montoVentas.add(pedido.getValorTotal());

            if (masBarato == null || pedido.isMasBaratoQue(masBarato)) {
                masBarato = pedido;
            }
            if (masCaro == null || pedido.isMasCaroQue(masCaro)) {
                masCaro = pedido;
            }

            categoriasProcesadas.add(pedido.getCategoria());
        }

<<<<<<< HEAD
=======
        this.totalDeProductosVendidos = productosVendidos;
>>>>>>> a4f7eaa8cbb4798bd90f97a1e0ec23adfc1fb71d
        this.totalDeCategorias = categoriasProcesadas.size();
        this.montoDeVentas = montoVentas;
        this.pedidoMasBarato = masBarato;
        this.pedidoMasCaro = masCaro;
    }

    private int calcularTotalDePedidosRealizados(List<Pedido> pedidos) {
        return pedidos.size();
    }

<<<<<<< HEAD
    private int calcularTotalDeProductosVendidos(List<Pedido> pedidos) {
        return pedidos.stream()
                .mapToInt(Pedido::getCantidad)
                .sum();
    }

=======
>>>>>>> a4f7eaa8cbb4798bd90f97a1e0ec23adfc1fb71d
    public int getTotalDePedidosRealizados() {
        return totalDePedidosRealizados;
    }

    public int getTotalDeProductosVendidos() {
        return totalDeProductosVendidos;
    }

    public int getTotalDeCategorias() {
        return totalDeCategorias;
    }

    public BigDecimal getMontoDeVentas() {
        return montoDeVentas;
    }

    public Pedido getPedidoMasBarato() {
        return pedidoMasBarato;
    }

    public Pedido getPedidoMasCaro() {
        return pedidoMasCaro;
    }

    public void imprimirInforme() {
        Locale locale = new Locale("es", "AR");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

        System.out.println("#### INFORME DE VALORES TOTALES");
        System.out.printf("- TOTAL DE PEDIDOS REALIZADOS: %d%n", totalDePedidosRealizados);
        System.out.printf("- TOTAL DE PRODUCTOS VENDIDOS: %d%n", totalDeProductosVendidos);
        System.out.printf("- TOTAL DE CATEGORIAS: %d%n", totalDeCategorias);
        System.out.printf("- MONTO DE VENTAS: %s%n", currencyFormat.format(montoDeVentas.setScale(2, RoundingMode.HALF_DOWN)));
        System.out.printf("- PEDIDO MAS BARATO: %s (%s)%n",
                currencyFormat.format(pedidoMasBarato.getValorTotal().setScale(2, RoundingMode.HALF_DOWN)),
                pedidoMasBarato.getProducto());
        System.out.printf("- PEDIDO MAS CARO: %s (%s)%n",
                currencyFormat.format(pedidoMasCaro.getValorTotal().setScale(2, RoundingMode.HALF_DOWN)),
                pedidoMasCaro.getProducto());
    }
}
