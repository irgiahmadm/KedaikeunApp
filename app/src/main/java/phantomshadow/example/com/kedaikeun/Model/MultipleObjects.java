package phantomshadow.example.com.kedaikeun.Model;

/**
 * Created by Phantom Shadow on 06/01/2018.
 */

public class MultipleObjects {

    private Barang barang;
    private KategoriBarang kategoriBarang;

    public MultipleObjects(Barang barang,KategoriBarang kategoriBarang){
        this.barang = barang;
        this.kategoriBarang = kategoriBarang;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public KategoriBarang getKategoriBarang() {
        return kategoriBarang;
    }

    public void setKategoriBarang(KategoriBarang kategoriBarang) {
        this.kategoriBarang = kategoriBarang;
    }

}
