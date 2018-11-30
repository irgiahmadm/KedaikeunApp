package phantomshadow.example.com.kedaikeun.ui;

import android.annotation.SuppressLint;
import android.view.MenuInflater;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import phantomshadow.example.com.kedaikeun.Adapter.TransaksiAdapter;
import phantomshadow.example.com.kedaikeun.Handler.DbHandler;
import phantomshadow.example.com.kedaikeun.Model.Barang;
import phantomshadow.example.com.kedaikeun.R;

public class Transaksi extends AppCompatActivity {

    Button bLihatLaporan , bTambahtransaksi ,bScreenShot;

    List<Barang> barangList = new ArrayList<>();

    TextView tVTotalHarga , tVNoTransaksi ,tvTgl;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    TransaksiAdapter transaksiAdapter;
    String total;
    int posisi,noTransaksi,countNm;
    Barang kdBarang;
    DbHandler dbHandler;
    View view;
    List<Barang> pesanan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        dbHandler = new DbHandler(Transaksi.this);

        Toast.makeText(Transaksi.this,"Disarankan Screenshot Terlebih Dahulu Sebelum Menambahkan Laporan",Toast.LENGTH_LONG).show();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf =  new SimpleDateFormat("dd-MM-yyyy");
        tvTgl = findViewById(R.id.tglDiTransaksi);
        long date = System.currentTimeMillis();
        String tgl = sdf.format(date);
        tvTgl.setText(tgl);

        bTambahtransaksi = findViewById(R.id.btnTambahTransaksi);

        recyclerView = findViewById(R.id.recyclerViewTransaksi);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();

        posisi = 0;


        barangList = (List<Barang>) getIntent().getSerializableExtra("listbarang");

        pesanan = new ArrayList<>();

        for(int i=0;i < barangList.size(); i++){
            Log.i("list ke-"+i,barangList.get(i).getNamaBarang()+":"+barangList.get(i).getBanyakBarang()+"");
            if(barangList.get(i).getBanyakBarang() != 0){
                pesanan.add(barangList.get(i));
            }
        }

        SharedPreferences preferences = getSharedPreferences("MyPref",Context.MODE_PRIVATE);
        int count = preferences.getInt("count", 0);
        countNm = preferences.getInt("countNm", 0);

        kdBarang = new Barang();

        tVNoTransaksi = findViewById(R.id.transaksiCount);
        tVNoTransaksi.setVisibility(View.GONE);
        if(count > 1 ){
            noTransaksi = count;
            tVNoTransaksi.setText(String.valueOf(noTransaksi));
        }
        else {
            noTransaksi = Integer.parseInt(tVNoTransaksi.getText().toString());
        }


        /*for(Barang barang : barangList){
            if(barang.getBanyakBarang() != 0){
                pesanan.add(barangList.get(posisi));
            }
            posisi++;
        }*/

        bTambahtransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                posisi = 0;

                if(noTransaksi >= 1){
                    noTransaksi++;
                    kdBarang.setKdTransaksi(noTransaksi);
                    tVNoTransaksi.setText(String.valueOf(kdBarang.getKdTransaksi()));

                    SharedPreferences shared = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();

                    editor.putInt("count",noTransaksi);
                    editor.apply();
                }

                for(Barang barang : pesanan){
                    if(barang.getBanyakBarang() != 0){
                        dbHandler.insertTransaksi(kdBarang,pesanan.get(posisi));
                    }
                    posisi++;
                }
            }
        });



        //int jmlhBarang = intent.getIntExtra("jmlhbarang",0);

        transaksiAdapter = new TransaksiAdapter(Transaksi.this,pesanan);
        recyclerView.setAdapter(transaksiAdapter);
        transaksiAdapter.notifyDataSetChanged();
        transaksiAdapter.notifyItemRemoved(posisi);


        tVTotalHarga = findViewById(R.id.totalHarKesTransaksi);

        total = intent.getStringExtra("totalharga");

        tVTotalHarga.setText(total);

        bLihatLaporan = findViewById(R.id.btnLihatLaporan);
        bLihatLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Transaksi.this,LaporanTransaksi.class);
                startActivity(intent);
            }
        });

        bScreenShot = findViewById(R.id.btnScreenshoot);
        bScreenShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap = takeScreenShot(Transaksi.this);

                //SharedPreferences shared = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                //SharedPreferences.Editor editor = shared.edit();

                String no = tVNoTransaksi.getText().toString();

                String dirName = "Kedaikeun App";
                File imageRoot = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),dirName);
                imageRoot.mkdirs();
                final File imagePath = new File(imageRoot, "Kedaikeun_" + no +".png");

                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(imagePath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    Log.e("Screenshot", "saved successfully");

                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    Log.e("GREC", e.getMessage(), e);
                } catch (IOException e) {
                    Log.e("GREC", e.getMessage(), e);
                }

                Toast.makeText(Transaksi.this,"Screenhoot Berhasil Data Di Simpan Di Penyimpanan Perangkat",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static Bitmap takeScreenShot(Activity activity) {

        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        Log.e("Screenshot", "taken successfully");
        return b;

    }

    public boolean saveToPdf(String pdfName , List<Barang> barangList){

        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/Kedaikeun"+pdfName+".pdf");
        try{

            if(!file.exists()){
                file.createNewFile();
            }
            Intent intent = getIntent();
            total = intent.getStringExtra("totalharga");

            Font font = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD,new BaseColor(0,0,0));
            Font font2 = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD,new BaseColor(0,0,0));
            Document document = new Document();
            PdfWriter.getInstance(document,new FileOutputStream(file));
            document.open();
            for(Barang barang : pesanan){
                document.add(new Paragraph(barang.getNamaBarang(),font));
                document.add(new Paragraph(String.valueOf(barang.getBanyakBarang())+" x "+String.valueOf(barang.getHargaBarang())+
                        " = "+barang.getTotalHarga(),font));
                document.add(new Paragraph(new Chunk(new DottedLineSeparator())));

            }
            document.add(new Paragraph("Total Harga",font2));
            document.add(new Paragraph(total));
            document.close();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }catch (DocumentException e){
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pdf,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences preferences = getSharedPreferences("MyPref",Context.MODE_PRIVATE);
        int count = preferences.getInt("count", 0);
        countNm = preferences.getInt("countNm", 0);

        switch(item.getItemId()){

            case R.id.savetopdf:
                saveToPdf(String.valueOf(count),pesanan);
                Toast.makeText(Transaksi.this,"Transaksi Berhasil Disimpan Ke PDF , Lihat Di Penyimpanan Perangkat",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}



