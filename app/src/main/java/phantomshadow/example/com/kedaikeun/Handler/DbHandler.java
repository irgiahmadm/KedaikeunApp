package phantomshadow.example.com.kedaikeun.Handler;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import android.widget.Toast;

import java.util.ArrayList;

import java.util.List;

import phantomshadow.example.com.kedaikeun.Model.Barang;



/**
 * Created by Phantom Shadow on 15/12/2017.
 */


public class DbHandler extends SQLiteOpenHelper {

    private final Context context;


    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "db_mocash";
    private static final String TABLE_BARANG = "t_barang";
    private static final String TABLE_TRANSAKSI = "t_transaksi";
    private static final String COLUMN_ID = "id_barang";
    private static final String COLUMN_TRANSAKSI = "no_transaksi";
    private static final String COLUMN_KATEGORIBARANG = "kategori_barang";
    private static final String COLUMN_NAMABARANG = "nama_barang";
    private static final String COLUMN_HARGABARANG = "harga_barang";
    private static final String COLUMN_TAKARANBARANG = "takaran_barang";
    //private static final String COLUMN_IMGBARANG = "img_barang";
    private static final String COLUMN_BANYAKBARANG = "banyak_barang";
    private static final String COLUMN_SUBTOTAL = "subtotal";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tableBarang = "CREATE TABLE "+TABLE_BARANG+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_KATEGORIBARANG+" TEXT,"+
                COLUMN_NAMABARANG+" TEXT,"+
                COLUMN_HARGABARANG+" INTEGER,"+
                COLUMN_TAKARANBARANG+" TEXT,"+
                COLUMN_BANYAKBARANG+" INTEGER"+")";

        String tableTransaksi = "CREATE TABLE "+TABLE_TRANSAKSI+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TRANSAKSI+" INTEGER,"+
                COLUMN_KATEGORIBARANG+" TEXT,"+
                COLUMN_NAMABARANG+" TEXT,"+
                COLUMN_HARGABARANG+" INTEGER,"+
                COLUMN_TAKARANBARANG+" TEXT,"+
                COLUMN_BANYAKBARANG+" INTEGER,"+
                COLUMN_SUBTOTAL+" INTEGER"+")";

        db.execSQL(tableBarang);
        db.execSQL(tableTransaksi);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BARANG);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TRANSAKSI);
        onCreate(db);
    }

    public void addProduct(Barang barang){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(COLUMN_IMGBARANG,barang.getFotoBarang());
        values.put(COLUMN_KATEGORIBARANG,barang.getKategoriBarang());
        values.put(COLUMN_NAMABARANG,barang.getNamaBarang());
        values.put(COLUMN_HARGABARANG,barang.getHargaBarang());
        values.put(COLUMN_TAKARANBARANG,barang.getTakaranBarang());
        values.put(COLUMN_BANYAKBARANG,barang.getBanyakBarang());

        int result = (int) db.insert(TABLE_BARANG,null,values);

        barang.setId(result);

    }
    public int countData(){

        String count = "SELECT COUNT(*) FROM "+TABLE_BARANG;
        int jmlh = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(count,null);
        if (cursor.moveToFirst()){
            jmlh = cursor.getInt(0);
        }
        cursor.close();
        return jmlh;
    }

    public List<Barang> getAllData(){
        List<Barang> barangList = new ArrayList<>();

        String select = "SELECT * FROM "+TABLE_BARANG;
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(select,null);

        if(cursor.moveToFirst()){
            do {

                Barang barang =  new Barang(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(4),cursor.getInt(3),
                        cursor.getInt(5));
                barangList.add(barang);
              /*  KategoriBarang kategoriBarang = subjects.get(cursor.getString(1));

                if(kategoriBarang == null){
                    kategoriBarang = new KategoriBarang();
                    kategoriBarang.setNamaKategori(cursor.getString(1));
                    subjects.put(cursor.getString(1),kategoriBarang);
                    kategoriList.add(kategoriBarang);
                }

                ArrayList<Barang> barangList = kategoriBarang.getBarangBarang();
                int listSize =  barangList.size();
                listSize++;*/

                /*Barang barang =  new Barang(cursor.getInt(0),cursor.getString(2),cursor.getString(3),cursor.getInt(4),
                                            cursor.getInt(5));*/

                /*Barang barang = new Barang();
                //barang.setFotoBarang(foto);
                barang.setId(cursor.getInt(0));
                barang.setNamaBarang(cursor.getString(2));
                barang.setHargaBarang(cursor.getInt(3));
                barang.setTakaranBarang(cursor.getString(4));
                barang.setBanyakBarang(cursor.getInt(5));

                Log.d("asdfghjkl", String.valueOf(cursor.getInt(4)));

                barangList.add(barang);

                //Log.d(String.valueOf(cursor.getString(2)),"asdfghjkl");

                kategoriBarang.setBarangBarang(barangList);
                groupPosition = kategoriList.indexOf(kategoriBarang);*/

            }while (cursor.moveToNext());
        }
        return barangList;
    }

    public Barang getOneData(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_BARANG,new String[]{COLUMN_ID,COLUMN_KATEGORIBARANG,COLUMN_NAMABARANG,COLUMN_TAKARANBARANG,
                                COLUMN_HARGABARANG,COLUMN_BANYAKBARANG},COLUMN_ID +" = ? ",new String[]{String.valueOf(id)},null,null
                                ,null);
        if(cursor != null)
            cursor.moveToFirst();

        //KategoriBarang kategoriBarang = new KategoriBarang(cursor.getString(1));

            return new Barang(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getInt(4), cursor.getInt(5));
    }

    public void updateData(Barang barang){

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(COLUMN_NAMABARANG,barang.getNamaBarang());
        //values.put(COLUMN_KATEGORIBARANG,multipleObjects.getKategoriBarang().getNamaKategori());
        values.put(COLUMN_TAKARANBARANG,barang.getTakaranBarang());
        values.put(COLUMN_HARGABARANG,barang.getHargaBarang());
        values.put(COLUMN_BANYAKBARANG,barang.getBanyakBarang());

        //Toast.makeText(context,"Data Terubah",Toast.LENGTH_SHORT).show();

        db.update(TABLE_BARANG, values, COLUMN_ID + " = ? ", new String[]{String.valueOf(barang.getId())});

    }


    public void deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BARANG,COLUMN_ID+"=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void insertTransaksi(Barang kdBarang, Barang barang){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(COLUMN_IMGBARANG,barang.getFotoBarang());
        //values.put(COLUMN_KATEGORIBARANG,kategoriBarang.getNamaKategori());
        values.put(COLUMN_TRANSAKSI,kdBarang.getKdTransaksi());
        values.put(COLUMN_NAMABARANG,barang.getNamaBarang());
        values.put(COLUMN_HARGABARANG,barang.getHargaBarang());
        values.put(COLUMN_TAKARANBARANG,barang.getTakaranBarang());
        values.put(COLUMN_BANYAKBARANG,barang.getBanyakBarang());
        values.put(COLUMN_SUBTOTAL,barang.getTotalHarga());
        int result = (int) db.insert(TABLE_TRANSAKSI,null,values);

        if(result != - 1){
            Toast.makeText(context,"Transaksi Ditambahkan",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context,"Transaksi Gagal Diambahkan",Toast.LENGTH_SHORT).show();
        }
        barang.setId(result);

    }


    public List<Barang> showBarangCount(){

        List<Barang> barangList = new ArrayList<>();

        String select = "SELECT "+COLUMN_ID+","+COLUMN_NAMABARANG+","+"SUM("+COLUMN_BANYAKBARANG+") FROM "+TABLE_TRANSAKSI+" GROUP BY "+COLUMN_NAMABARANG;
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(select,null);

        if(cursor.moveToFirst()){
            do{
                Barang barang = new Barang();
                barang.setId(cursor.getInt(0));
                barang.setNamaBarang(cursor.getString(1));
                barang.setBanyakBarang(cursor.getInt(2));
                barangList.add(barang);
            }
            while (cursor.moveToNext());

        }
        return barangList;
    }

    public int countPendapatan(){

        int pendapatan = 0;
        String select = "SELECT SUM("+COLUMN_SUBTOTAL+") FROM "+TABLE_TRANSAKSI;
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(select, null);

        if(cursor.moveToFirst()){

            do{
                pendapatan = cursor.getInt(0);
            }while (cursor.moveToNext());
        }

        return pendapatan;
    }

    public int countTransaksi(){

        int transaksi = 0;
        String select = "SELECT DISTINCT COUNT("+COLUMN_TRANSAKSI+") FROM "+TABLE_TRANSAKSI;
        SQLiteDatabase db =  this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor =  db.rawQuery(select, null);
        if(cursor.moveToFirst()){
            do{
                transaksi = cursor.getInt(0);
            }while (cursor.moveToNext());

        }
        return transaksi;
    }

    public void deleteTransaksi(String namaBarang){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSAKSI,COLUMN_NAMABARANG+"= ?", new String[]{namaBarang});
        db.close();
    }

}
