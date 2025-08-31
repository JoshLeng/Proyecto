package com.example.sistemagasolinera.modelo;
import java.util.ArrayList;
import java.util.List;

public class Estacion {
    //Uso de Singleton para tener una única instancia
    private static Estacion instancia;
    private List<Bomba> bombas;

    //
    private Estacion() {
        bombas = new ArrayList<>();
        // inicialización de bombas (siempre 3, una por tipo)
        bombas.add(new Bomba(1, TipoCombustible.REGULAR, 20.7, 100.0));
        bombas.add(new Bomba(2, TipoCombustible.SUPER, 22.2, 120.0));
        bombas.add(new Bomba(3, TipoCombustible.DIESEL, 18.3, 200.0));
    }

    public List<Bomba> getBombas() {
        return bombas;
    }

    //Instancia única
    public static Estacion getInstancia() {
        if (instancia == null) {
            instancia = new Estacion();
        }
        return instancia;
    }


    /**
     * Busca una bomba por su ID.
     * @param idBomba ID de la bomba a buscar.
     * @return Bomba encontrada o null si no existe.
     */
    public Bomba getBombaPorId(int idBomba) {
        for (Bomba b : bombas) {
            if (b.getIdBomba() == idBomba) {
                return b;
            }
        }
        return null;
    }

    /**
     * Obtiene el total de ventas acumuladas de todas las bombas.
     * @return Total de ventas.
     */
    public double getVentasTotales() {
        double total = 0.0;
        for (Bomba b : bombas) {
            total += b.getVentasAcumuladas();
        }
        return total;
    }

    /**
     * Genera un reporte de inventario de todas las bombas.
     * @return String con el reporte.
     */
    public String reporteInventario() {
        StringBuilder sb = new StringBuilder();
        for (Bomba b : bombas) {
            sb.append("Bomba ").append(b.getIdBomba())
              .append(" (Tipo: ").append(b.getTipoCombustible())
              .append(") - Litros disponibles: ").append(b.getCapacidadDisponible())
              .append(" | Ventas acumuladas: Q").append(String.format("%.2f", b.getVentasAcumuladas()))
              .append("\n");
        }
        return sb.toString();
    }

    /**
     * Limpia el historial de ventas de todas las bombas (para corte).
     */
    public void limpiarVentasBombas() {
        for (Bomba b : bombas) {
            b.getHistorialVentas().clear();
        }
    }
}