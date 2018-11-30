package phantomshadow.example.com.kedaikeun.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Phantom Shadow on 20/11/2017.
 */

public class KategoriBarang implements Serializable{


    private String namaKategori;
    private ArrayList<Barang> barangBarang = new ArrayList<Barang>();

    public KategoriBarang(String namaKategori){
        this.namaKategori = namaKategori;
    }

    public KategoriBarang(){

    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public ArrayList<Barang> getBarangBarang() {
        return barangBarang;
    }

    public void setBarangBarang(ArrayList<Barang> barangBarang) {
        this.barangBarang = barangBarang;
    }
}
