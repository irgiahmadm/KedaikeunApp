package phantomshadow.example.com.kedaikeun.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import phantomshadow.example.com.kedaikeun.Model.Barang;
import phantomshadow.example.com.kedaikeun.R;

/**
 * Created by Phantom Shadow on 21/01/2018.
 */

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder> {


    Context context;
    private List<Barang> barangList = new ArrayList<>();

    public TransaksiAdapter(Context context,List<Barang> barangList){
        this.context = context;
        this.barangList = barangList;
    }
    @Override
    public TransaksiAdapter.TransaksiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_transaksi,parent,false);
        TransaksiViewHolder viewHolder = new TransaksiViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TransaksiAdapter.TransaksiViewHolder holder, int position) {
        holder.nmbarang.setText(barangList.get(position).getNamaBarang());
        holder.jmlhBarang.setText(barangList.get(position).getBanyakBarang()+"");
        holder.hargaBarang.setText(barangList.get(position).getHargaBarang()+"");
        holder.subTotal.setText(barangList.get(position).getTotalHarga()+"");
    }

    @Override
    public int getItemCount() {
        return barangList.size();
    }

    public class TransaksiViewHolder extends RecyclerView.ViewHolder{
        TextView nmbarang,jmlhBarang,hargaBarang,subTotal;

        public TransaksiViewHolder(View itemView) {
            super(itemView);

            nmbarang = itemView.findViewById(R.id.namaBarangDiTransaksi);
            jmlhBarang = itemView.findViewById(R.id.banyakBarangDiTransaksi);
            hargaBarang = itemView.findViewById(R.id.hargaBarangDiTransaksi);
            subTotal = itemView.findViewById(R.id.totalSubItemTransaksi);
        }
    }
}
