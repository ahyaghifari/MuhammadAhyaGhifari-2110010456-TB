/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 *
 * @author Ahya Ghifari
 */
// CLASS UNTUK KONVERSI NILAI DOUBLE KE RUPIAH INDONESIA
public class KonversiRupiah {  
    
    public static String konversi(double nilai){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(nilai);
    }
}
