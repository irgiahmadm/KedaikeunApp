package phantomshadow.example.com.kedaikeun.ui;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import phantomshadow.example.com.kedaikeun.Adapter.BarangAdapter;
import phantomshadow.example.com.kedaikeun.Handler.DbHandler;
import phantomshadow.example.com.kedaikeun.Model.Barang;
import phantomshadow.example.com.kedaikeun.R;

public class ListBarang2 extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DbHandler dbHandler;
    List<Barang> barangList = new ArrayList<>();
    BarangAdapter barangAdapter;
    //Barang barang = new Barang();
    LinearLayout layoutTotalHarga;
    TextView tVTotalHarga;
    String totalHarga;

    Button tambahBarang;
    int posisi;
    int jmlhBarang;

    Barang barang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_barang2);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        tVTotalHarga = findViewById(R.id.totalHargaBarang);

        layoutTotalHarga = findViewById(R.id.layoutTotHarBarang);

        dbHandler = new DbHandler(ListBarang2.this);


        barangList = dbHandler.getAllData();
        dbHandler.close();

        barangAdapter = new BarangAdapter(ListBarang2.this,barangList,tVTotalHarga);
        recyclerView.setAdapter(barangAdapter);
        barangAdapter.notifyDataSetChanged();

        /*posisi = 0;
        for (Barang barang : barangList){
            jmlhBarang = barang.getBanyakBarang();
            Log.d("jmlh",posisi+"-"+jmlhBarang+"");
            posisi++;
        }*/

        //Toast.makeText(ListBarang2.this,totalHarga,Toast.LENGTH_SHORT).show();

        //final Barang barang = new Barang();


        barang  = new Barang();

        layoutTotalHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListBarang2.this,Transaksi.class);

                jmlhBarang = barang.getBanyakBarang();
                for(Barang barang : barangList){
                    jmlhBarang += barang.getBanyakBarang();
                }

                if(jmlhBarang > 0){
                    intent.putExtra("listbarang", (Serializable) barangList);

                    totalHarga = tVTotalHarga.getText().toString();
                    intent.putExtra("totalharga",totalHarga);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(ListBarang2.this,"Belum Memilih Barang",Toast.LENGTH_SHORT).show();
                }


            }
        });

        tambahBarang = findViewById(R.id.btnTambahBarang);
        tambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListBarang2.this,InputBarang.class);
                intent.putExtra("action","tambah");
                startActivity(intent);
                finish();
            }
        });

    }


}