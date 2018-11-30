package phantomshadow.example.com.kedaikeun.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import java.util.ArrayList;
import java.util.List;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import phantomshadow.example.com.kedaikeun.Handler.DbHandler;
import phantomshadow.example.com.kedaikeun.ui.InputBarang;
import phantomshadow.example.com.kedaikeun.Model.Barang;
import phantomshadow.example.com.kedaikeun.R;

/**
 * Created by Phantom Shadow on 20/01/2018.
 */

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.BarangViewHolder> {

    BarangViewHolder viewHolder;
    private Context context;
    private List<Barang> barangList = new ArrayList<>();
    private Barang barang;

    private  TextView tVTotalHarga;
    private int jmlhKlik,harga,posisi,total;

    public BarangAdapter(Context context,List<Barang> barangList,TextView tVTotalHarga){
        this.barangList = barangList;
        this.context = context;
        this.tVTotalHarga = tVTotalHarga;
        this.jmlhKlik = jmlhKlik;
    }
    @Override
    public BarangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_barang,parent,false);
        viewHolder = new BarangViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BarangViewHolder holder, final int position) {

        holder.nmBarang.setText(barangList.get(position).getNamaBarang());
        holder.tkrbarang.setText(barangList.get(position).getTakaranBarang());
        holder.hargaBarang.setText(barangList.get(position).getHargaBarang()+"");
        holder.bnykBarang.setText(barangList.get(position).getBanyakBarang()+"");
        if (barangList.get(position).getKategoriBarang().contains("Makanan")){
            holder.kategoriBarang.setImageResource(R.drawable.food);
        }
        else if(barangList.get(position).getKategoriBarang().contains("Minuman")){
            holder.kategoriBarang.setImageResource(R.drawable.drink);
        }
        else{
            holder.kategoriBarang.setImageResource(R.drawable.basket);
        }

        holder.buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                jmlhKlik = barangList.get(position).getBanyakBarang();
                jmlhKlik++;

                barangList.get(position).setBanyakBarang(jmlhKlik);

                harga = barangList.get(position).getHargaBarang();
                harga = harga * jmlhKlik;

                barangList.get(position).setTotalHarga(harga);

                total = 0;
                posisi = 0;


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("jmlhbarang",jmlhKlik);
                editor.commit();

                for (Barang tothar : barangList){
                    total += tothar.getTotalHarga();
                    posisi++;
                }

                tVTotalHarga.setText(total+"");
                notifyDataSetChanged();

            }
        });
        holder.buttonKurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jmlhKlik = barangList.get(position).getBanyakBarang();
                if(jmlhKlik > 0 ){
                    jmlhKlik--;
                    barangList.get(position).setBanyakBarang(jmlhKlik);


                    harga = barangList.get(position).getHargaBarang();
                    harga = harga * jmlhKlik;

                    barangList.get(position).setTotalHarga(harga);

                    Intent intent = new Intent();
                    intent.putExtra("hmbrr",jmlhKlik+"");
                    total = 0;
                    posisi = 0;

                    for (Barang tothar : barangList){
                        total += tothar.getTotalHarga();
                        posisi++;
                    }

                    tVTotalHarga.setText(total+"");
                    notifyDataSetChanged();
                }
            }
        });
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                barang = barangList.get(holder.getAdapterPosition());

                final CharSequence[] dialogItem = {"Delete","Update"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Pilih");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                DbHandler dbHandler = new DbHandler(context);
                                dbHandler.deleteData(barang.getId());
                                dbHandler.deleteTransaksi(barang.getNamaBarang());
                                barangList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                break;
                            case 1 :
                                Intent intent = new Intent(context,InputBarang.class);

                                intent.putExtra("action","update");
                                intent.putExtra("id",barang.getId());
                                //Log.d("asdfasdas", String.valueOf(barang.getId()));

                                context.startActivity(intent);
                                ((Activity)context).finish();

                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return barangList.size();
    }

    class BarangViewHolder extends RecyclerView.ViewHolder {

        LinearLayout item;
        ImageView kategoriBarang;
        TextView nmBarang,bnykBarang,tkrbarang,hargaBarang;
        Button buttonTambah,buttonKurang;
        RecyclerView recyclerView;

        BarangViewHolder(View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.itemRecycler);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            kategoriBarang = itemView.findViewById(R.id.category);
            nmBarang = itemView.findViewById(R.id.textNamaBarang);
            bnykBarang = itemView.findViewById(R.id.jmlhItem);
            tkrbarang = itemView.findViewById(R.id.textTakaranBarang);
            hargaBarang = itemView.findViewById(R.id.textHargaBarang);
            buttonTambah = itemView.findViewById(R.id.buttonPlus);
            buttonKurang = itemView.findViewById(R.id.buttonMin);

        }
    }
}
