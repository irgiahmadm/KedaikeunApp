package phantomshadow.example.com.kedaikeun.Model;

import java.io.Serializable;

/**
 * Created by Phantom Shadow on 20/11/2017.
 */

public class Barang implements Serializable{

    int id;
    int fotoBarang;
    private String kategoriBarang;
    private String namaBarang;
    private int hargaBarang;
    private String takaranBarang;
    private int banyakBarang;
    private int totalHarga;
    private int kdTransaksi;
    public Barang(int id ,String kategoriBarang, String namaBarang , String takaranBarang , int hargaBarang,int banyakBarang){
        this.id = id;
        this.fotoBarang = fotoBarang;
        this.kategoriBarang = kategoriBarang;
        this.namaBarang = namaBarang;
        this.takaranBarang = takaranBarang;
        this.hargaBarang = hargaBarang;
        this.banyakBarang = banyakBarang;
    }

    public String getKategoriBarang() {
        return kategoriBarang;
    }

    public void setKategoriBarang(String kategoriBarang) {
        this.kategoriBarang = kategoriBarang;
    }

    public Barang(){

    }

    public Barang(int id,int kdTransaksi,String namaBarang,int banyakBarang){
        this.id = id;
        this.kdTransaksi = kdTransaksi;
        this.namaBarang = namaBarang;
        this.banyakBarang = banyakBarang;
    }
    public int getKdTransaksi() {
        return kdTransaksi;
    }

    public void setKdTransaksi(int kdTransaksi) {
        this.kdTransaksi = kdTransaksi;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}
    public Integer getFotoBarang() {
        return fotoBarang;
    }

    public void setFotoBarang(int fotoBarang) {
        this.fotoBarang = fotoBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public int getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(int hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public String getTakaranBarang() {
        return takaranBarang;
    }

    public void setTakaranBarang(String takaranBarang) {
        this.takaranBarang = takaranBarang;
    }

    public int getBanyakBarang() {
        return banyakBarang;
    }

    public void setBanyakBarang(int banyakBarang) {
        this.banyakBarang = banyakBarang;
    }
}
