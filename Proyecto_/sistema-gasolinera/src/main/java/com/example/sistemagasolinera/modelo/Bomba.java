
package com.example.sistemagasolinera.modelo;

import java.util.ArrayList;
import java.util.List;

public class Bomba {
    // ...existing code...
    private int idBomba;
    private TipoCombustible tipoCombustible;
    private double precioPorLitro;
    private double ventasAcumuladas;
    private List<Venta> historialVentas;
    private EstadosBombas estadoBomba;
    private double capacidadBomba;
    private double capacidadMaxima;

    ////constructor
    
    public Bomba(int idBomba, TipoCombustible tipoCombustible, double precioPorLitro, double capacidadMaxima){
        this.idBomba = idBomba;
        this.tipoCombustible = tipoCombustible;
        this.precioPorLitro = precioPorLitro;
        this.ventasAcumuladas = 0.0;
        this.historialVentas = new ArrayList<>();
        this.capacidadMaxima = capacidadMaxima;
        this.capacidadBomba = capacidadMaxima; // Al inicio, llena
        this.estadoBomba = EstadosBombas.ACTIVA;
    }

    public boolean registrarVenta(double litros){
        if (estadoBomba != EstadosBombas.ACTIVA) {
            return false; //si la bomba no está activa, no se puede iniciar una venta
            }
        if (litros > capacidadBomba){
            return false;}
        
        Venta nuevaVenta=new Venta(litros, precioPorLitro, tipoCombustible);
        this.historialVentas.add(nuevaVenta);
        this.ventasAcumuladas+=nuevaVenta.getMontoTotal();
        capacidadBomba-=litros;
        return true;
    }


///setters y getters
    public void setEstado(EstadosBombas nuevoEstado){
        this.estadoBomba=nuevoEstado;
    }
    
    public List<Venta> getHistorialVentas(){
        return historialVentas;

    }

    /**
     * Recarga la bomba con una cantidad de litros.
     * @param litros Cantidad de litros a agregar.
     */
    /**
     * Recarga la bomba con una cantidad de litros, sin exceder la capacidad máxima.
     * @param litros Cantidad de litros a agregar.
     * @return true si la recarga fue exitosa, false si excede la capacidad máxima.
     */
    public boolean recargarCombustible(double litros) {
        if (litros <= 0) return false;
        if (this.capacidadBomba + litros > this.capacidadMaxima) {
            return false;
        }
        this.capacidadBomba += litros;
        return true;
    }
    /**
     * Devuelve la capacidad máxima de la bomba.
     */
    public double getCapacidadMaxima() {
        return capacidadMaxima;
    }

    /**
     * Devuelve el identificador de la bomba.
     * @return id de la bomba.
     */
    public int getIdBomba() {
        return idBomba;
    }

    /**
     * Devuelve el tipo de combustible de la bomba.
     * @return tipo de combustible.
     */
    public TipoCombustible getTipoCombustible() {
        return tipoCombustible;
    }

    /**
     * Devuelve el precio por litro de la bomba.
     * @return precio por litro.
     */
    public double getPrecioPorLitro() {
        return precioPorLitro;
    }

    /**
     * Devuelve el total de ventas acumuladas.
     * @return ventas acumuladas.
     */
    public double getVentasAcumuladas() {
        return ventasAcumuladas;
    }

    /**
     * Devuelve el estado actual de la bomba.
     * @return Estado de la bomba.
     */
    public EstadosBombas getEstado() {
        return estadoBomba;
    }

    /**
     * Devuelve la cantidad de combustible disponible en la bomba.
     * @return Litros disponibles.
     */
    public double getCapacidadDisponible() {
        return capacidadBomba;
    }

    @Override
    public String toString() {
        return tipoCombustible.toString();
    }
}
