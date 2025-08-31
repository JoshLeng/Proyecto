package com.example.sistemagasolinera;

import com.example.sistemagasolinera.modelo.Bomba;
import com.example.sistemagasolinera.modelo.Estacion;
import com.example.sistemagasolinera.modelo.TipoCombustible;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private ComboBox<Bomba> comboBombas;
    @FXML
    private ComboBox<Bomba> comboBombasReporte;
    @FXML
    private ComboBox<TipoCombustible> comboTipoGasolina;
    @FXML
    private TextField txtLitros;
    @FXML
    private TextField txtRecarga;
    @FXML
    private TextArea txtAreaMensajes;

    private Estacion estacion;

    @FXML
    public void initialize() {
        estacion = Estacion.getInstancia();
        // Inicializar tipos de gasolina
        comboTipoGasolina.setItems(FXCollections.observableArrayList(TipoCombustible.values()));
        comboTipoGasolina.setOnAction(e -> filtrarBombasPorTipo());
        comboTipoGasolina.getSelectionModel().selectFirst();
        filtrarBombasPorTipo();
        // Inicializar ComboBox de bombas para reportes
        ObservableList<Bomba> bombasList = FXCollections.observableArrayList(estacion.getBombas());
        comboBombasReporte.setItems(bombasList);
        if (!bombasList.isEmpty()) {
            comboBombasReporte.getSelectionModel().selectFirst();
        }
        comboBombasReporte.setOnAction(e -> mostrarReporteBombaAuto());
        txtAreaMensajes.setText("Bienvenido al sistema de gasolinera.\nSeleccione tipo de gasolina, bomba y registre una venta o consulte el inventario.");
    }

    private void filtrarBombasPorTipo() {
        TipoCombustible tipo = comboTipoGasolina.getSelectionModel().getSelectedItem();
        if (tipo == null) return;
        ObservableList<Bomba> bombasFiltradas = FXCollections.observableArrayList();
        for (Bomba b : estacion.getBombas()) {
            if (b.getTipoCombustible() == tipo) {
                bombasFiltradas.add(b);
            }
        }
        comboBombas.setItems(bombasFiltradas);
        if (!bombasFiltradas.isEmpty()) {
            comboBombas.getSelectionModel().selectFirst();
        }
    }

    @FXML
    public void registrarVenta() {
        Bomba bomba = comboBombas.getSelectionModel().getSelectedItem();
        if (bomba == null) {
            mostrarAlerta("Seleccione una bomba.");
            return;
        }
        String litrosStr = txtLitros.getText();
        double litros;
        try {
            litros = Double.parseDouble(litrosStr);
            if (litros <= 0) {
                mostrarAlerta("Ingrese una cantidad válida de litros.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Ingrese una cantidad válida de litros.");
            return;
        }
        boolean exito = bomba.registrarVenta(litros);
        if (exito) {
            txtAreaMensajes.appendText("\nVenta registrada en bomba " + bomba.getIdBomba() + " (" + bomba.getTipoCombustible() + "): " + litros + " litros.");
        } else {
            txtAreaMensajes.appendText("\nNo se pudo registrar la venta. Verifique el estado o inventario de la bomba.");
        }
        txtLitros.clear();
    }

    @FXML
    public void iniciarBomba() {
        Bomba bomba = comboBombas.getSelectionModel().getSelectedItem();
        if (bomba == null) {
            mostrarAlerta("Seleccione una bomba.");
            return;
        }
        String recargaStr = txtRecarga.getText();
        double litros;
        try {
            litros = Double.parseDouble(recargaStr);
            if (litros <= 0) {
                mostrarAlerta("Ingrese una cantidad válida de litros para recargar.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Ingrese una cantidad válida de litros para recargar.");
            return;
        }
        boolean exito = bomba.recargarCombustible(litros);
        if (exito) {
            bomba.setEstado(com.example.sistemagasolinera.modelo.EstadosBombas.ACTIVA);
            txtAreaMensajes.appendText("\nBomba " + bomba.getIdBomba() + " recargada con " + litros + " litros y activada.");
        } else {
            mostrarAlerta("No se puede recargar: excede la capacidad máxima de la bomba (" + bomba.getCapacidadMaxima() + " litros).");
        }
        txtRecarga.clear();
    }

    @FXML
    public void reporteBomba() {
        mostrarReporteBombaAuto();
    }

    private void mostrarReporteBombaAuto() {
        Bomba bomba = comboBombasReporte.getSelectionModel().getSelectedItem();
        if (bomba == null) {
            mostrarAlerta("Seleccione una bomba para el reporte.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- Reporte de Bomba ---\n");
        sb.append("ID: ").append(bomba.getIdBomba()).append("\n");
        sb.append("Tipo: ").append(bomba.getTipoCombustible()).append("\n");
        sb.append("Estado: ").append(bomba.getEstado()).append("\n");
        sb.append("Litros disponibles: ").append(bomba.getCapacidadDisponible()).append("\n");
        sb.append("Precio actual por litro: Q").append(String.format("%.2f", bomba.getPrecioPorLitro())).append("\n");
        sb.append("Ventas acumuladas: Q").append(String.format("%.2f", bomba.getVentasAcumuladas())).append("\n");
        sb.append("Historial de ventas:\n");
        for (var venta : bomba.getHistorialVentas()) {
            String ventaStr = venta.toString().replace("$", "Q");
            // Si el toString de Venta tiene el monto, asegúrate de que esté en Q
            sb.append(ventaStr).append("\n");
        }
        txtAreaMensajes.setText(sb.toString());
    }

    @FXML
    public void mostrarInventario() {
        String reporte = estacion.reporteInventario();
        txtAreaMensajes.appendText("\n\n--- Inventario Actual ---\n" + reporte);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}