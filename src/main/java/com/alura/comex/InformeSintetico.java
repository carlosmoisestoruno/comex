package com.alura.comex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
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
        int productosVendidos = 0;
        BigDecimal montoVentas = BigDecimal.ZERO;
        Pedido masBarato = null;
        Pedido masCaro = null;

        CategoriasProcesadas categoriasProcesadas = new CategoriasProcesadas();

        for (Pedido pedido : pedidos) {
            productosVendidos += pedido.getCantidad();
            montoVentas = montoVentas.add(pedido.getValorTotal());

            if (masBarato == null || pedido.isMasBaratoQue(masBarato)) {
                masBarato = pedido;
            }
            if (masCaro == null || pedido.isMasCaroQue(masCaro)) {
                masCaro = pedido;
            }

            if (!categoriasProcesadas.contains(pedido.getCategoria())) {
                categoriasProcesadas.add(pedido.getCategoria());
            }
        }

        this.totalDePedidosRealizados = pedidos.size();
        this.totalDeProductosVendidos = productosVendidos;
        this.totalDeCategorias = categoriasProcesadas.size();
        this.montoDeVentas = montoVentas;
        this.pedidoMasBarato = masBarato;
        this.pedidoMasCaro = masCaro;
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
