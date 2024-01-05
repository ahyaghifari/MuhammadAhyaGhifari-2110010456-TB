
package chart;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import model.PelangganTerdaftar;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import table.PelangganTable;

/**
 *
 * @author Ahya Ghifari
 */

// LINE CHART UNTUK PELANGGAN
public class LineChartPelanggan {
   
    // inisialisasi properti bulan dan tahun
   private final String bulan;
   private final String tahun;
   
   public LineChartPelanggan( String bulan, String tahun) {

       // isi properti sesuai parameter
       this.bulan = bulan;
       this.tahun = tahun;
   }

   // membuat dataset untuk chart
   private DefaultCategoryDataset createDataset( ) {
       
           
       // inisialisasi tabel pelanggan dan mengambil data
      PelangganTable pelangganTable = new PelangganTable();
      ArrayList<PelangganTerdaftar> list = pelangganTable.pelangganTerdaftar(bulan, tahun);
      int sizeList = list.size();
      
      // inisialiasi dataset
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
       
      // inisialisasi series untuk chart
      String ser1 = "Tanggal";
      
      // mengisi nilai chart sesuai data di array list pelanggan
      for (int y = 0; y < sizeList; y++) {
            String n = list.get(y).getTanggal().substring(8);
            dataset.addValue(list.get(y).getJmlPelanggan(), ser1, n);
      }

      return dataset; 
   }
   
   // method untuk menampilkan chart
   public ChartPanel showChart(String title){
       
       // membuat chart sesuai dengan dataset
       JFreeChart chart = ChartFactory.createLineChart(
         title,
         "Tanggal","Pelanggan Terdaftar",
         createDataset(),
         PlotOrientation.VERTICAL,
         true,true,false);
      
       // kostumisasi chart
       chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(23, 70, 90));
       chart.getCategoryPlot().getRenderer().setSeriesShape(0, new Ellipse2D.Double(-3d, -3d, 6d, 6d));
       chart.getLegend().setWidth(0);
       chart.setBackgroundPaint(new Color(255, 255, 255));

      chart.getLegend().setItemFont(new java.awt.Font("Poppins", 0, 11));
      
      // membuat chart panel untuk ditambahkan ke panel pelanggan di halamaan data pelanggan manajerframe
      ChartPanel chartPanel = new ChartPanel( chart );
      
      return chartPanel;
   }
}