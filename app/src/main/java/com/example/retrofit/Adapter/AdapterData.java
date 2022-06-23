package com.example.retrofit.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit.API.APIRequestData;
import com.example.retrofit.API.RetroServer;
import com.example.retrofit.Activity.MainActivity;
import com.example.retrofit.Activity.ubah;
import com.example.retrofit.Model.ResponseModel;
import com.example.retrofit.R;
import com.example.retrofit.Model.DataModel;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{
    private Context ctx;
    private List<DataModel> listData;
    private List<DataModel> listMahasiswa;
    private int idMahasiswa;

    public AdapterData(Context ctx, List<DataModel> listData) {
        this.ctx = ctx;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dm = listData.get(position);

        holder.tvId.setText(String.valueOf(dm.getId()));
        holder.tvNama.setText(dm.getNama());
        holder.tvNim.setText(dm.getNim());
        holder.tvIpk.setText(dm.getIpk());
        holder.tvAngkatan.setText(dm.getAngkatan());
        holder.tvSemester.setText(dm.getSemester());
        holder.tvEmail.setText(dm.getEmail());
        holder.tvTelepon.setText(dm.getTelepon());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvId, tvNama, tvNim, tvIpk, tvAngkatan, tvSemester, tvEmail, tvTelepon;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvNim = itemView.findViewById(R.id.tv_nim);
            tvIpk = itemView.findViewById(R.id.tv_ipk);
            tvAngkatan = itemView.findViewById(R.id.tv_angkatan);
            tvSemester = itemView.findViewById(R.id.tv_semester);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvTelepon = itemView.findViewById(R.id.tv_telepon);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Pilih Operasi yang Akan Dilakukan");
                    dialogPesan.setTitle("Perhatian");
                    dialogPesan.setIcon(R.mipmap.ic_launcher_round);
                    dialogPesan.setCancelable(true);

                    idMahasiswa = Integer.parseInt(tvId.getText().toString());

                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteData();
                            dialogInterface.dismiss();
                            Handler hand = new Handler();
                            hand.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((MainActivity) ctx).retrieveData();
                                }
                            }, 1000);
                        }
                    });

                    dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getData();
                            dialogInterface.dismiss();
                        }
                    });

                    dialogPesan.show();

                    return false;
                }
            });
        }

        private void deleteData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> hapusData = ardData.ardDeleteData(idMahasiswa);

            hapusData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void getData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> ambilData = ardData.ardGetData(idMahasiswa);

            ambilData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    listMahasiswa = response.body().getData();

                    int varIdMahasiswa= listMahasiswa.get(0).getId();
                    String varNamaMahasiswa = listMahasiswa.get(0).getNama();
                    String varNimMahasiswa = listMahasiswa.get(0).getNim();
                    String varIpkMahasiswa= listMahasiswa.get(0).getIpk();
                    String varAngkatanMahasiswa = listMahasiswa.get(0).getAngkatan();
                    String varSemesterMahasiswa = listMahasiswa.get(0).getSemester();
                    String varEmailMahasiswa = listMahasiswa.get(0).getEmail();
                    String varTeleponMahasiswa = listMahasiswa.get(0).getTelepon();

                    Intent kirim = new Intent(ctx, ubah.class);
                    kirim.putExtra("xId", varIdMahasiswa);
                    kirim.putExtra("xNama", varNamaMahasiswa);
                    kirim.putExtra("xNim", varNimMahasiswa);
                    kirim.putExtra("xIpk", varIpkMahasiswa);
                    kirim.putExtra("xAngkatan", varAngkatanMahasiswa);
                    kirim.putExtra("xSemester", varSemesterMahasiswa);
                    kirim.putExtra("xEmail", varEmailMahasiswa);
                    kirim.putExtra("xTelepon", varTeleponMahasiswa);
                    ctx.startActivity(kirim);
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}