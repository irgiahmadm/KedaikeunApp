//package phantomshadow.example.com.mocash.Adapter;

/*import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;*/

//import phantomshadow.example.com.mocash.ListBarang;
/*import phantomshadow.example.com.mocash.Model.Barang;
import phantomshadow.example.com.mocash.Model.KategoriBarang;
import phantomshadow.example.com.mocash.R;
import phantomshadow.example.com.mocash.Transaksi;*/

/**
 * Created by Phantom Shadow on 18/11/2017.
 */


/*
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<KategoriBarang> kategoriList;

    private ArrayList<Barang> barangList;
    TextView tNamaBarang, tTakaranBarang, tHargaBarang, tJumlahKlik, tKategori, tvTotalHarga;
    Button buttonPlus, buttonMin;
    ImageView imgBarang;
    LinearLayout item;

    int jumlahKlik;

    int harga , position , total;

    public ExpandableListAdapter(Context context, ArrayList<KategoriBarang> kategoriList, TextView tvTotalHarga) {
        this.context = context;
        this.kategoriList = kategoriList;
        this.tvTotalHarga = tvTotalHarga;
        this.barangList = barangList;
    }


    @Override
    public int getGroupCount() {
        return kategoriList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        barangList = kategoriList.get(groupPosition).getBarangBarang();
        return barangList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return kategoriList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        barangList = kategoriList.get(groupPosition).getBarangBarang();
        return barangList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        KategoriBarang kategoriBarang = (KategoriBarang) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.kategori_barang, null);
        }

        tKategori = (TextView) convertView.findViewById(R.id.expandKategoriBarang);
        tKategori.setText(kategoriBarang.getNamaKategori());

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, View convertView, ViewGroup parent) {

        final KategoriBarang kategoriBarang = (KategoriBarang) getGroup(groupPosition);;
        final Barang barang = kategoriBarang.getBarangBarang().get(childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_barang, null);
        }

        item = (LinearLayout) convertView.findViewById(R.id.childItem);
        tNamaBarang = (TextView) convertView.findViewById(R.id.textNamaBarang);
        tHargaBarang = (TextView) convertView.findViewById(R.id.textHarga);
        tTakaranBarang = (TextView) convertView.findViewById(R.id.textTakaran);
        tJumlahKlik = (TextView) convertView.findViewById(R.id.textJumlahKlik);

        buttonPlus = (Button) convertView.findViewById(R.id.btnTambahJumlah);
        buttonMin = (Button) convertView.findViewById(R.id.btnKurangJumlah);

        imgBarang = (ImageView) convertView.findViewById(R.id.imageBarang);


        */
/*tNamaBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context,"Clicked On " + barang.getNamaBarang(),Toast.LENGTH_SHORT).show();
            }
        });*//*


        tNamaBarang.setText(barang.getNamaBarang());

        tHargaBarang.setText(barang.getHargaBarang() + "");
        tTakaranBarang.setText(barang.getTakaranBarang());
        tJumlahKlik.setText(barang.getBanyakBarang() + "");
        imgBarang.setImageResource(barang.getFotoBarang());

        buttonMin.setTag(barang);
        buttonPlus.setTag(barang);

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlahKlik = barang.getBanyakBarang();
                jumlahKlik += 1;

                barang.setBanyakBarang(jumlahKlik);

                harga = barang.getHargaBarang();

                harga = harga * jumlahKlik;
                barang.setTotalHarga(harga);

                notifyDataSetChanged();

                position = 0;
                total = 0;


                for (Barang tothar : barangList) {
                    total += tothar.getTotalHarga();
                    barangList.get(position++);
                }
                Toast.makeText(context,position+"",Toast.LENGTH_SHORT).show();

                tvTotalHarga.setText(total+"");


//                tJumlahKlik.setText(jumlahKlik+"");

            }
        });

        buttonMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                jumlahKlik = barang.getBanyakBarang();

                if (jumlahKlik > 0) {
                    jumlahKlik--;
                    barang.setBanyakBarang(jumlahKlik);


                    //Toast.makeText(context,harga+"",Toast.LENGTH_SHORT).show();
                    harga = barang.getHargaBarang();

                    harga = harga * jumlahKlik;
                    barang.setTotalHarga(harga);

                    notifyDataSetChanged();

                    position = 0;
                    total = 0;

                    for (Barang tothar : barangList) {
                        total += tothar.getTotalHarga();
                        position++;
                    }

                    tvTotalHarga.setText(total+"");

                }

            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}

*/
