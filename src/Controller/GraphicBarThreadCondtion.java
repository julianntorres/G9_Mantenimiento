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
public class GraphicBarThreadCondtion extends Thread{
    public Graficos grafico;
    public List<Map<String, Object>> datos;
    public int operativo;
    public int defectuosa;
    public int mantenimiento;
    public GraphicBarThreadCondtion(Graficos grafico, List<Map<String, Object>> datos) {
        this.grafico = grafico;
        this.datos = datos;
        this.mantenimiento = 0;
        this.defectuosa = 0;
        this.operativo = 0 ;
        
    }
    
    private void asignarValores(int[]labo_Operativo, int[]labo_Defectuoso, int []labo_Mantenimiento){
        for (int i=0;i<datos.size();i++){
            int opcion =  Integer.parseInt(datos.get(i).get("id_lab").toString());
            switch (datos.get(i).get("estado").toString()) {
                        case "operativo":
                            labo_Operativo[opcion-1]++;
                            break;
                        case "defectuosa":
                            labo_Defectuoso[opcion-1]++;
                            break;
                        default:
                            labo_Mantenimiento[opcion-1]++;
                            break;
                    }
        }
    }
    private void almacenarDatos(DefaultCategoryDataset datos, int[]labo_Operativo, int[]labo_Defectuoso, int []labo_Mantenimiento){
        datos.setValue(labo_Operativo[0],"PC operativa","Laboratorio 1");
        datos.setValue(labo_Defectuoso[0],"PC defectuosa","Laboratorio 1");
        datos.setValue(labo_Mantenimiento[0],"PC en mantenimiento","Laboratorio 1");
        datos.setValue(labo_Operativo[1],"PC operativa","Laboratorio 2");
        datos.setValue(labo_Defectuoso[1],"PC defectuosa","Laboratorio 2");
        datos.setValue(labo_Mantenimiento[1],"PC en mantenimiento","Laboratorio 2");
        datos.setValue(labo_Operativo[2],"PC operativa","Laboratorio 3");
        datos.setValue(labo_Defectuoso[2],"PC defectuosa","Laboratorio 3");
        datos.setValue(labo_Mantenimiento[2],"PC en mantenimiento","Laboratorio 3");
        datos.setValue(labo_Operativo[3],"PC operativa","Laboratorio 4");
        datos.setValue(labo_Defectuoso[3],"PC defectuosa","Laboratorio 4");
        datos.setValue(labo_Mantenimiento[3],"PC en mantenimiento","Laboratorio 4");
        datos.setValue(labo_Operativo[4],"PC operativa","Laboratorio 5");
        datos.setValue(labo_Defectuoso[4],"PC defectuosa","Laboratorio 5");
        datos.setValue(labo_Mantenimiento[4],"PC en mantenimiento","Laboratorio 5");
        datos.setValue(labo_Operativo[5],"PC operativa","Laboratorio 6");
        datos.setValue(labo_Defectuoso[5],"PC defectuosa","Laboratorio 6");
        datos.setValue(labo_Mantenimiento[5],"PC en mantenimiento","Laboratorio 6");
        datos.setValue(labo_Operativo[6],"PC operativa","Laboratorio 7");
        datos.setValue(labo_Defectuoso[6],"PC defectuosa","Laboratorio 7");
        datos.setValue(labo_Mantenimiento[6],"PC en mantenimiento","Laboratorio 7");
    }
    
    @Override
    public void run(){
        // ver cuanto tiempo demora en ejecutarse el hilo
        long tiempoInicial = System.currentTimeMillis();
        int[] labo_Operativo = new int[7];

        int[] labo_Defectuoso = new int[7];

        int[] labo_Mantenimiento= new int[7];
        
        asignarValores(labo_Operativo,labo_Defectuoso,labo_Mantenimiento);
        /*for (int i=0;i<datos.size();i++){
            int opcion =  Integer.parseInt(datos.get(i).get("id_lab").toString());
            switch (datos.get(i).get("estado").toString()) {
                        case "operativo":
                            labo_Operativo[opcion-1]++;
                            break;
                        case "defectuosa":
                            labo_Defectuoso[opcion-1]++;
                            break;
                        default:
                            labo_Mantenimiento[opcion-1]++;
                            break;
                    }
        }*/
        
        /*for (int i=0;i<datos.size();i++){
            switch (datos.get(i).get("id_lab").toString()) {
                case "1":
                    switch (datos.get(i).get("estado").toString()) {
                        case "operativo":
                            labo_Operativo[0]++;
                            break;
                        case "defectuosa":
                            labo_Defectuoso[0]++;
                            break;
                        default:
                            labo_Mantenimiento[0]++;
                            break;
                    }
         
            

        }*/
        DefaultCategoryDataset datos = new DefaultCategoryDataset();
      
        almacenarDatos(datos,labo_Operativo,labo_Defectuoso,labo_Mantenimiento);
        
        JFreeChart grafico_barras = ChartFactory.createBarChart3D("Estado de las PC por laboratorio", 
                "Laboratorio", 
                "Cantidad", datos, 
                PlotOrientation.HORIZONTAL, true, 
                true, false
                );
        ChartPanel panel = new ChartPanel(grafico_barras);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(400,250));
        
        grafico.jPanel2.setLayout(new BorderLayout());
        grafico.jPanel2.add(panel,BorderLayout.NORTH);
        
        grafico.pack();
        // saber cuanto tiempo se demora en ejecutar el codigo
        long tiempoFinal = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion: " + (tiempoFinal - tiempoInicial) + " milisegundos");
        
        
    }
    
//    public void sumarLabo(){
//        
//    }
        
}
