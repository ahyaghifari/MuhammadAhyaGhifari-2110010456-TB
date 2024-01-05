
package chart;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import model.DataTransaksi;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import table.TransaksiTable;

/**
 *
 * @author Ahya Ghifari
 */

// LINE CHART TRANSAKSI
public class LineChartTransaksi {
   
   // inisialisasi properti
   private final String bulan;
   private final String tahun;
   
   // kontruktor
   public LineChartTransaksi( String bulan, String tahun) {

       // mengisi properti sesuai parameter
       this.bulan = bulan;
       this.tahun = tahun;
   }

   private DefaultCategoryDataset createDataset( ) {
       
      // inisialisasi tabel transaksi dan mengisinya ke array list
      TransaksiTable transaksiTable = new TransaksiTable();
      ArrayList<DataTransaksi> listTransaksi = transaksiTable.getDataTransaksi(bulan, tahun);
      int sizeList = listTransaksi.size();
      
      // inisialisasi dataset
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      
      // inisialisasi series dari chart
      String series = "Tanggal";
      
      // isi nilai dari chart sesuai dengan array list transaksi
      for (int y = 0; y < sizeList; y++) {
            String n = listTransaksi.get(y).getTanggal().substring(8);
            dataset.addValue(listTransaksi.get(y).getTotal(), series, n);
      }
       
      return dataset; 
   }
   
   // method menampilkan chart
   public ChartPanel showChart(String title){
      // membuat chart dan mengisi nilai nya dari dataser
       JFreeChart chart = ChartFactory.createLineChart(
         title,
         "Tanggal","Pemasukan",
         createDataset(),
         PlotOrientation.VERTICAL,
         true,true,false);
      
       // kostumisasi chart
      chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(23, 70, 90));
      chart.getCategoryPlot().getRenderer().setSeriesShape(0, new Ellipse2D.Double(-3d, -3d, 6d, 6d));
      chart.getLegend().setWidth(0);
      chart.setBackgroundPaint(new Color(255, 255, 255));

      chart.getLegend().setItemFont(new java.awt.Font("Poppins", 0, 11));
      
      // membuat chart panel agar bisa ditampilkan di halaman transaksi manajer frame
      ChartPanel chartPanel = new ChartPanel( chart );
      
      return chartPanel;
   }
}