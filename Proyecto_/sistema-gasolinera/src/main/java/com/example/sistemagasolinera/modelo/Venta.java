
package com.example.sistemagasolinera.modelo;
import java.time.LocalDateTime;

public class Venta {
    private static int contador = 1;
    private int idVenta;
    private LocalDateTime fechaHora;
    private double litrosVendidos;
    private double montoTotal;
    private TipoCombustible tipoCombustible;

    /**
     * Constructor de Venta
     */
    public Venta(double litrosVendidos, double precioPorLitro, TipoCombustible tipoCombustible) {
        this.idVenta = contador++;
        this.fechaHora = LocalDateTime.now();
        this.litrosVendidos = litrosVendidos;
        this.tipoCombustible = tipoCombustible;
        this.montoTotal = precioPorLitro * litrosVendidos;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public TipoCombustible getTipoCombustible() {
        return tipoCombustible;
    }

    public double getLitrosVendidos() {
        return litrosVendidos;
    }

    @Override
    public String toString() {
        return "Venta #" + idVenta + " | Fecha: " + fechaHora + " | Litros: " + litrosVendidos + " | Monto: Q" + String.format("%.2f", montoTotal) + " | Tipo: " + tipoCombustible;
    }
}
