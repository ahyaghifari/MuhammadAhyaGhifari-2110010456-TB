/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chart;
import java.awt.Color;
import java.util.ArrayList;
import model.ObjekWisata;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.VerticalAlignment;
import table.ObjekWisataTable;

/**
 *
 * @author Ahya Ghifari
 */

// PIE CHART OBJEK WISATA
public class PieChartObjekWisata{
    
    // inisialisasi array list
    ArrayList<ObjekWisata> listData;
    
    // konstruktor
    public PieChartObjekWisata(String title, String bulan, String tahun){
        
        // isi properti dengan parameter
        ObjekWisataTable objekWisataTable = new ObjekWisataTable();
        listData = objekWisataTable.presentaseTransaksi(bulan, tahun, true);
    }
    
    // method untuk membuat dataset
    private PieDataset createDataset() {
      
        // inisialiasasi dataset
        DefaultPieDataset dataset = new DefaultPieDataset( );
       
        // isi pie chart dengan array list objek wisata
        for(ObjekWisata ow : listData){      
            dataset.setValue(ow.getNamaObjekWisata(), ow.getJmlTransaksi());
        }
      
      return dataset;         
   }
    
    // method menampilkan cgart
    public ChartPanel showChart(String title) {
        
      // membuat pie chart 
      JFreeChart chart = ChartFactory.createPieChart(      
         title,   // chart title 
         createDataset(),          
         true,              
         true, 
         true);
    
      
      // kostumisasi pie chart
      PiePlot plot = (PiePlot) chart.getPlot();
      plot.setBackgroundPaint(new Color(255, 255, 255));
      plot.setBaseSectionPaint(new Color(255, 255, 255, 255));
      plot.setShadowPaint(new Color(255, 255, 255, 255));
      plot.setSectionOutlinePaint(new Color(255, 255, 255));
      
      plot.setLabelBackgroundPaint(new Color(23, 70, 90, 50));
      plot.setLabelShadowPaint(new Color(255, 255, 255));
      plot.setLabelFont(new java.awt.Font("Poppins", 0, 9));
      
      
      // kostumisasi pie chart agar warna dari pie chart sesuai dengan tingkat dari banyaknya transaksi
      // semakin kecil maka akan transparansi warna akan semakin meningkat (semakin pudar), semakin tinggi transaksi maka warna akan semakin solid
      int bagianData = 255 / listData.size();
      int i = 0;
      int jmlTransaksi = 0;
      int alpha = 255;
      int transaksiTertinggi = listData.get(0).getJmlTransaksi();
      for(ObjekWisata ow : listData){
            if((ow.getJmlTransaksi() != transaksiTertinggi) && (ow.getJmlTransaksi() != jmlTransaksi)){
                jmlTransaksi = ow.getJmlTransaksi();
                alpha -= bagianData;
            }
            plot.setSectionPaint(i, new Color(23,70,90, alpha));
            i++;
      }
     
      
      // lanjutan konstumisasi pie chart
       chart.setBackgroundPaint(Color.WHITE);
       chart.getPlot().setOutlinePaint(Color.WHITE); 
       chart.setBorderPaint(Color.WHITE);
       chart.getLegend().setBorder(BlockBorder.NONE);
       chart.getLegend().setItemFont(new java.awt.Font("Poppins", 0, 11));
       chart.getLegend().setVerticalAlignment(VerticalAlignment.CENTER);
       chart.getLegend().setHorizontalAlignment(HorizontalAlignment.CENTER);
      
       // membuat chart panel agar bisa ditampilkan di halaman data objek wisata manajer frame
       ChartPanel ch = new ChartPanel(chart);   
       
       ch.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
       
       return ch;
   }
    

}
