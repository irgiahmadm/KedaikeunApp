package phantomshadow.example.com.kedaikeun.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import phantomshadow.example.com.kedaikeun.R;

public class MainActivity extends AppCompatActivity {

    LinearLayout mListBarang,mHelp,mLaporan,mAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListBarang = findViewById(R.id.menuAtasKiri);
        mHelp = findViewById(R.id.menuBawahKiri);
        mLaporan =  findViewById(R.id.menuAtasKanan);
        mAbout =  findViewById(R.id.menuBawahKanan);
        mListBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListBarang2.class);
                startActivity(intent);
            }
        });

        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HelpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LaporanTransaksi.class);
                startActivity(intent);
            }
        });

        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}
