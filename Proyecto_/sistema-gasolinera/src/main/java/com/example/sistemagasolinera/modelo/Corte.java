
package com.example.sistemagasolinera.modelo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Corte {
	private LocalDateTime fechaCorte;
	private List<Venta> ventasCorte;
	private double totalCorte;

	public Corte() {
		this.fechaCorte = LocalDateTime.now();
		this.ventasCorte = new ArrayList<>();
		this.totalCorte = 0.0;
	}

	public void agregarVenta(Venta venta) {
		ventasCorte.add(venta);
		totalCorte += venta.getMontoTotal();
	}

	public double getTotalCorte() {
		return totalCorte;
	}

	public List<Venta> getVentasCorte() {
		return ventasCorte;
	}

	public LocalDateTime getFechaCorte() {
		return fechaCorte;
	}

	public String reporteCorte() {
		StringBuilder sb = new StringBuilder();
		sb.append("Corte realizado el: ").append(fechaCorte).append("\n");
		for (Venta v : ventasCorte) {
			sb.append(v.toString()).append("\n");
		}
		sb.append("Total del corte: Q").append(String.format("%.2f", totalCorte));
		return sb.toString();
	}
}
