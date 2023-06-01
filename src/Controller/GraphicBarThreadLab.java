/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interface.Graficos;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author ramos
 */
// Importar las clases necesarias...

public class GraphicBarThreadLab extends Thread {
    public Graficos grafico;
    public List<Map<String, Object>> datos;
    // Resto de variables...

    public GraphicBarThreadLab(Graficos grafico, List<Map<String, Object>> datos) {
        this.grafico = grafico;
        this.datos = datos;
        // Resto de la inicialización...
    }

    @Override
    public void run() {
        long tiempoInicial = System.currentTimeMillis();
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
        DataProcessor dataProcessor = new DataProcessor(datos);
        dataProcessor.processData();

        DefaultCategoryDataset datosGrafico = dataProcessor.getDataset();

        JFreeChart grafico_barras = ChartFactory.createBarChart3D(
            "Cantidad de computadoras por laboratorio",
            "Laboratorio",
            "Cantidad",
            datosGrafico,
            PlotOrientation.HORIZONTAL,
            true,
            true,
            false
        );

        // Resto del código para mostrar el gráfico...

        long tiempoFinal = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion: " + (tiempoFinal - tiempoInicial) + " milisegundos");
    }

    public void sumarLabo() {
        // Código relacionado con sumarLabo()...
    }
}

class DataProcessor {
    private List<Map<String, Object>> datos;
    private DefaultCategoryDataset dataset;

    public DataProcessor(List<Map<String, Object>> datos) {
        this.datos = datos;
        this.dataset = new DefaultCategoryDataset();
    }

    public void processData() {
        int numPcLab1 = 0;
        int numPcLab2 = 0;
        // Resto de variables...

        for (int i = 0; i < datos.size(); i++) {
            switch (datos.get(i).get("id_lab").toString()) {
                case "1":
                    numPcLab1++;
                    break;
                case "2":
                    numPcLab2++;
                    break;
                // Resto de los casos...
            }
        }

        dataset.setValue(numPcLab1, "Lab 1", "Laboratorio 1");
        dataset.setValue(numPcLab2, "Lab 2", "Laboratorio 2");
        // Resto de los valores...
    }

    public DefaultCategoryDataset getDataset() {
        return dataset;
    }
}
