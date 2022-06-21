package com.example.retrofit.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofit.API.APIRequestData;
import com.example.retrofit.API.RetroServer;
import com.example.retrofit.Model.ResponseModel;
import com.example.retrofit.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ubah extends AppCompatActivity {
    private int xId;
    private String xNama, xNim, xKelas;
    private EditText etNama, etNim, etKelas;
    private Button btnUbah;
    private String yNama, yNim, yKelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        Intent terima = getIntent();
        xId = terima.getIntExtra("xId", -1);
        xNama = terima.getStringExtra("xNama");
        xNim = terima.getStringExtra("xNim");
        xKelas = terima.getStringExtra("xKelas");

        etNama = findViewById(R.id.et_nama);
        etNim = findViewById(R.id.et_nim);
        etKelas = findViewById(R.id.et_kelas);
        btnUbah = findViewById(R.id.btn_ubah);

        etNama.setText(xNama);
        etNim.setText(xNim);
        etKelas.setText(xKelas);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yNama = etNama.getText().toString();
                yNim = etNim.getText().toString();
                yKelas = etKelas.getText().toString();

                updateData();
            }
        });
    }

    private void updateData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> ubahData = ardData.ardUpdateData(xId, yNama, yNim, yKelas);

        ubahData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(ubah.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ubah.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}