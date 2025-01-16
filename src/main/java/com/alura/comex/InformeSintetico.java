package com.alura.comex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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

        //Refactirizamos el cálculo del indicador del indicador de "total de productos vendidos"
        this.totalDeProductosVendidos = calcularTotalDeProductosVendidos(pedidos);

        //Refactorizamos el monto de ventas
        this.montoDeVentas = calcularMontoDeVentas(pedidos);

        //Refactorizamos el calculo de total de categorias
        this.totalDeCategorias = calcularTotalDeCategorias(pedidos);

        //Refactorizamos los cálculos del pedido más barato y pedido más caro
        this.pedidoMasBarato = pedidos.stream()
                .min(Comparator.comparing(Pedido::getValorTotal))
                .orElse(null);

        this.pedidoMasCaro = pedidos.stream()
                .max(Comparator.comparing(Pedido::getValorTotal))
                .orElse(null);
    }

    //Refactorizacion 1
    private int calcularTotalDePedidosRealizados(List<Pedido> pedidos) {
        return pedidos.size();
    }

    //Refactorizacion 2
    private int calcularTotalDeProductosVendidos(List<Pedido> pedidos) {
        return pedidos.stream()
                .mapToInt(Pedido::getCantidad)
                .sum();
    }

    //Refactorizacion 3
    private BigDecimal calcularMontoDeVentas(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(Pedido::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //Refactorizacion 4
    private int calcularTotalDeCategorias(List<Pedido> pedidos) {
        return (int) pedidos.stream()
                .map(Pedido::getCategoria)
                .distinct()
                .count();
    }

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
