package phantomshadow.example.com.kedaikeun.ui;


import android.app.Dialog;
import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import phantomshadow.example.com.kedaikeun.Adapter.BarangTerjualAdapter;
import phantomshadow.example.com.kedaikeun.Handler.DbHandler;
import phantomshadow.example.com.kedaikeun.Model.Barang;
import phantomshadow.example.com.kedaikeun.R;

public class LaporanTransaksi extends AppCompatActivity {

    TextView tVJumlahBarang, tVPendapatan, tVTransaksi, tVLaba, tVModal;

    List<Barang> barangList = new ArrayList<>();

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    DbHandler dbHandler;

    BarangTerjualAdapter adapter;

    EditText eTextModal;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_transaksi);


        dbHandler = new DbHandler(LaporanTransaksi.this);


        tVLaba = findViewById(R.id.labaUsaha);

        tVJumlahBarang = findViewById(R.id.jmlhBarangTersedia);
        tVJumlahBarang.setText(String.valueOf(dbHandler.countData()));

        tVPendapatan = findViewById(R.id.jmlhPendapatan);
        tVPendapatan.setText(String.valueOf(dbHandler.countPendapatan()));

        int pendapatan = Integer.parseInt(tVPendapatan.getText().toString());

        SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        final int modalAwal = preferences.getInt("modalawal", 0);

        tVModal = findViewById(R.id.lblShowModal);

        if (pendapatan > modalAwal) {
            int laba = pendapatan - modalAwal;
            tVLaba.setText(String.valueOf(laba));

        } else if (modalAwal == 0) {
            String txt = "Modal : 0 (klik untuk mengubah)";
            tVModal.setText(txt);
            tVLaba.setText("0");
            DialogModal();
        } else if (modalAwal <= pendapatan) {
            tVLaba.setText("0");
        }

        String txt = "Modal : "+modalAwal+" (klik untuk mengubah)";
        tVModal.setText(txt);
        tVModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogModal();
            }
        });

        tVTransaksi = findViewById(R.id.jmlhTransaksi);
        tVTransaksi.setText(String.valueOf(dbHandler.countTransaksi()));


        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.recyclerBarangTerjual);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        barangList = dbHandler.showBarangCount();
        adapter = new BarangTerjualAdapter(LaporanTransaksi.this, barangList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

    }

    public void DialogModal() {

        final Dialog dialog = new Dialog(LaporanTransaksi.this);
        dialog.setContentView(R.layout.dialog_modal);

        btnSubmit = dialog.findViewById(R.id.btnSubmitModal);
        eTextModal = dialog.findViewById(R.id.editTextModal);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eTextModal = dialog.findViewById(R.id.editTextModal);
                String modalText = eTextModal.getText().toString();


                int pendapatan = Integer.parseInt(tVPendapatan.getText().toString());

                if (TextUtils.isEmpty(modalText)) {

                    Toast.makeText(LaporanTransaksi.this, "Anda Belum Memasukkan Modal", Toast.LENGTH_SHORT).show();
                } else {

                    int modal = Integer.parseInt(modalText);

                    SharedPreferences shared = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putInt("modalawal", modal);
                    editor.apply();


                    if (pendapatan > modal) {
                        int laba = pendapatan - modal;
                        String txt = "Modal : "+modal+" (klik untuk mengubah)";
                        tVModal.setText(txt);
                        Log.d("laba", laba + "");
                        tVLaba.setText(String.valueOf(laba));
                    }

                    if (modal == 0) {
                        eTextModal.setText("");
                        tVLaba.setText("0");
                        DialogModal();
                        Toast.makeText(LaporanTransaksi.this, "Modal 0 tidak bisa dihitung", Toast.LENGTH_SHORT).show();
                    }
                    if (modal <= pendapatan) {
                        String txt = "Modal : "+modal+" (klik untuk mengubah)";
                        tVModal.setText(txt);
                        tVLaba.setText("0");
                    }

                    else {
                        String txt = "Modal : "+modal+" (klik untuk mengubah)";
                        tVModal.setText(txt);
                        tVLaba.setText("0");
                    }
                    dialog.dismiss();
                }

            }
        });
        dialog.setCancelable(true);
        dialog.show();

    }
}
