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
    private String xNama, xNim, xIpk, xAngkatan, xSemester, xEmail, xTelepon;
    private EditText etNama, etNim, etIpk, etAngkatan, etSemester, etEmail, etTelepon;
    private Button btnUbah;
    private String yNama, yNim, yIpk, yAngkatan, ySemester, yEmail, yTelepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        Intent terima = getIntent();
        xId = terima.getIntExtra("xId", -1);
        xNama = terima.getStringExtra("xNama");
        xNim = terima.getStringExtra("xNim");
        xIpk = terima.getStringExtra("xIpk");
        xAngkatan = terima.getStringExtra("xAngkatan");
        xSemester = terima.getStringExtra("xSemester");
        xEmail = terima.getStringExtra("xEmail");
        xTelepon = terima.getStringExtra("xTelepon");

        etNama = findViewById(R.id.et_nama);
        etNim = findViewById(R.id.et_nim);
        etIpk = findViewById(R.id.et_ipk);
        etAngkatan = findViewById(R.id.et_angkatan);
        etSemester = findViewById(R.id.et_semester);
        etEmail = findViewById(R.id.et_email);
        etTelepon = findViewById(R.id.et_telepon);
        btnUbah = findViewById(R.id.btn_ubah);

        etNama.setText(xNama);
        etNim.setText(xNim);
        etIpk.setText(xIpk);
        etAngkatan.setText(xAngkatan);
        etSemester.setText(xSemester);
        etEmail.setText(xEmail);
        etTelepon.setText(xTelepon);



        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yNama = etNama.getText().toString();
                yNim = etNim.getText().toString();
                yIpk = etIpk.getText().toString();
                yAngkatan = etAngkatan.getText().toString();
                ySemester = etSemester.getText().toString();
                yEmail = etEmail.getText().toString();
                yTelepon = etTelepon.getText().toString();


                updateData();
            }
        });
    }

    private void updateData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> ubahData = ardData.ardUpdateData(xId, yNama, yNim, yIpk, yAngkatan, ySemester, yEmail, yTelepon);

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