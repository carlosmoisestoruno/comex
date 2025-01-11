package com.alura.comex;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pedido {

    private String categoria;
    private String producto;
    private String cliente;

    private BigDecimal precio;
    private int cantidad;

    private LocalDate fecha;

    public Pedido(String categoria, String producto, String cliente, BigDecimal precio, int cantidad, LocalDate fecha) {
        this.categoria = categoria;
        this.producto = producto;
        this.cliente = cliente;
        this.precio = precio;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getProducto() {
        return producto;
    }

    public String getCliente() {
        return cliente;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "categoria='" + categoria + '\'' +
                ", producto='" + producto + '\'' +
                ", cliente='" + cliente + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                ", fecha=" + fecha +
                '}';
    }

    //Encapsulamiento, responsabilidad unica y reducción de dependecias
    //Este metodo encápsula la lógica para calcular el total de un pedido
    //Le asignamos la responsabilidad a la clase pedido
    public BigDecimal getValorTotal(){
        return precio.multiply(BigDecimal.valueOf(cantidad));
    }

    //Este metodo compara el costo total es menor a otro pedido
    //Delegamos la responsabilidad a la clase correspondiente
    public boolean isMasBaratoQue(Pedido otroPedido){
        return this.getValorTotal().compareTo(otroPedido.getValorTotal()) < 0;
    }

    //Este metodo compara el costo total es mayor a otro pedido
    //Delegamos la responsabilidad a la clase correspondiente
    public boolean isMasCaroQue(Pedido otroPedido){
        return this.getValorTotal().compareTo(otroPedido.getValorTotal()) > 0;
    }
}
