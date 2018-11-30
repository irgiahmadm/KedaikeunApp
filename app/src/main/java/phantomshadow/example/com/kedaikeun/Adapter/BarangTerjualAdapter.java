package phantomshadow.example.com.kedaikeun.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;

import phantomshadow.example.com.kedaikeun.Model.Barang;
import phantomshadow.example.com.kedaikeun.R;

/**
 * Created by Phantom Shadow on 28/01/2018.
 */


public class BarangTerjualAdapter extends RecyclerView.Adapter<BarangTerjualAdapter.BarangTerjualViewHolder> {

    BarangTerjualViewHolder viewHolder;
    Context context;

    private List<Barang> barangList = new ArrayList<>();

    private Barang barang;


    public BarangTerjualAdapter(Context context, List<Barang> barangList){
        this.context = context;
        this.barangList = barangList;
    }

    @Override
    public BarangTerjualViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_barang_terjual,parent,false);
        viewHolder = new BarangTerjualViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BarangTerjualAdapter.BarangTerjualViewHolder holder, int position) {

        holder.tVJumlah.setText(String.valueOf(barangList.get(position).getBanyakBarang()));
        holder.tVNamaBarang.setText(barangList.get(position).getNamaBarang());
    }

    @Override
    public int getItemCount() {
        return barangList.size();
    }

    public class BarangTerjualViewHolder extends RecyclerView.ViewHolder {

        LinearLayout item;
        TextView tVNamaBarang,tVJumlah;
        RecyclerView recyclerView;

        public BarangTerjualViewHolder(View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.itemTerjual);
            recyclerView = itemView.findViewById(R.id.recyclerBarangTerjual);
            tVNamaBarang = itemView.findViewById(R.id.namaBarangTerjual);
            tVJumlah = itemView.findViewById(R.id.jmlhBarangTerjual);

        }
    }
}
