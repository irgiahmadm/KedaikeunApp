package phantomshadow.example.com.kedaikeun.ui;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;


import phantomshadow.example.com.kedaikeun.Handler.DbHandler;
import phantomshadow.example.com.kedaikeun.Model.Barang;
import phantomshadow.example.com.kedaikeun.R;


public class InputBarang extends AppCompatActivity {

    private DbHandler dbHandler;
    //private ArrayList<KategoriBarang> kategoriList = new ArrayList<KategoriBarang>();
    //private LinkedHashMap<String,KategoriBarang> subjects = new LinkedHashMap<>();

    RadioGroup radioBtnGroup;
    RadioButton radioButton,rBtnMakanan,rBtnMinuman,rBtnLainnya;

    Button bAddImage,bSubmit;
    ImageView imgGalleryLoad;
    TextView etKategori,etNamaBarang,etHargaBarang,etTakaran;
    private static int IMG_RESULT = 1;
    //String imageDecode;

    private String action;
    //String[] FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_barang);

        dbHandler = new DbHandler(this);
        Barang barang = new Barang();
        //KategoriBarang kategoriBarang = new KategoriBarang();

        //MultipleObjects multipleObjects = new MultipleObjects(barang,kategoriBarang);

        //imgGalleryLoad = findViewById(R.id.imgTampilBarang);

        //etKategori = findViewById(R.id.editTextKategori);
        etNamaBarang = findViewById(R.id.editTextBarang);
        etTakaran = findViewById(R.id.editTextTakaran);
        etHargaBarang = findViewById(R.id.editTextHarga);

        radioBtnGroup = findViewById(R.id.radioGroup);
        rBtnMakanan = findViewById(R.id.radioMakanan);
        rBtnMinuman = findViewById(R.id.radioMinuman);
        rBtnLainnya = findViewById(R.id.radioLainnya);

        int selectedId = radioBtnGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);

        bSubmit = findViewById(R.id.btnSubmit);
        //bAddImage = findViewById(R.id.btnAddImage);

        action = getIntent().getStringExtra("action");

        /*bAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image*//*");
                startActivityForResult(intent,IMG_RESULT);
            }
        });*/

        if(action.equals("update")){
            bSubmit.setText("Ubah");

            barang = dbHandler.getOneData(getIntent().getIntExtra("id",0));
            if(barang.getKategoriBarang().contains("Makanan")){
                rBtnMakanan.setChecked(true);
            }
            if(barang.getKategoriBarang().contains("Minuman")){
                rBtnMinuman.setChecked(true);
            }
            if (barang.getKategoriBarang().contains("Lainnya")){
                rBtnLainnya.setChecked(true);
            }

            etNamaBarang.setText(barang.getNamaBarang());
            etTakaran.setText(barang.getTakaranBarang());
            etHargaBarang.setText(barang.getHargaBarang()+"");

        }

        //final KategoriBarang finalKategoriBarang = kategoriBarang;
        final Barang finalBarang =  barang;

        //final MultipleObjects finalMultipleObjects = multipleObjects;

        bSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //String kategori = etKategori.getText().toString();
                    //Drawable foto = imgGalleryLoad.getDrawable();
                    String namaBarang = etNamaBarang.getText().toString();
                    String takaran = etTakaran.getText().toString();
                    String textHarga = etHargaBarang.getText().toString();


                    //int hargaBarang = Integer.parseInt(textHarga);

                    String kategori = "";
                    if(rBtnMakanan.isChecked()){
                        kategori = rBtnMakanan.getText().toString();
                    }
                    if(rBtnMinuman.isChecked()){
                        kategori = rBtnMinuman.getText().toString();
                    }
                    if(rBtnLainnya.isChecked()){
                        kategori = rBtnLainnya.getText().toString();
                    }
                    //barang.setFotoBarang(foto);

                    if(action.equals("tambah")){

                        if(kategori.isEmpty()||namaBarang.isEmpty()||takaran.isEmpty()||textHarga.isEmpty()){
                            Toast.makeText(InputBarang.this,"Masukkan data secara lengkap",Toast.LENGTH_SHORT).show();
                        }

                        else {
                            finalBarang.setKategoriBarang(kategori);
                            finalBarang.setNamaBarang(namaBarang);
                            finalBarang.setTakaranBarang(takaran);
                            finalBarang.setHargaBarang(Integer.parseInt(textHarga));
                            finalBarang.setBanyakBarang(0);

                            dbHandler.addProduct(finalBarang);
                            Intent intent = new Intent(InputBarang.this,ListBarang2.class);
                            startActivity(intent);
                            finish();
                        }

                    }

                    else {

                        if(kategori.isEmpty()||namaBarang.isEmpty()||takaran.isEmpty()||textHarga.isEmpty()){
                            Toast.makeText(InputBarang.this,"Masukkan data secara lengkap",Toast.LENGTH_SHORT).show();
                        }
                        else {

                            finalBarang.setKategoriBarang(kategori);
                            finalBarang.setNamaBarang(namaBarang);
                            finalBarang.setTakaranBarang(takaran);
                            finalBarang.setHargaBarang(Integer.parseInt(textHarga));
                            finalBarang.setBanyakBarang(0);

                            dbHandler.updateData(finalBarang);

                            Intent intent = new Intent(InputBarang.this,ListBarang2.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                }
            });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            try{
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgGalleryLoad.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(InputBarang.this,"Terjadi Kesalahan",Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(InputBarang.this,"Anda Belum Memilih Gambar",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(InputBarang.this,ListBarang2.class);
        startActivity(intent);
        finish();
    }
}
