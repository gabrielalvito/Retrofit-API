package com.example.retrofit.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class tambah extends AppCompatActivity {
    private EditText etNama, etNim, etIpk, etAngkatan, etSemester, etEmail, etTelepon;
    private Button btnSimpan;
    private String nama, nim, ipk, angkatan, semester, email, telepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etNim = findViewById(R.id.et_nim);
        etIpk = findViewById(R.id.et_ipk);
        etAngkatan = findViewById(R.id.et_angkatan);
        etSemester = findViewById(R.id.et_semester);
        etEmail = findViewById(R.id.et_email);
        etTelepon = findViewById(R.id.et_telepon);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = etNama.getText().toString();
                nim = etNim.getText().toString();
                ipk = etIpk.getText().toString();
                angkatan = etAngkatan.getText().toString();
                semester = etSemester.getText().toString();
                email = etEmail.getText().toString();
                telepon = etTelepon.getText().toString();

                if(nama.trim().equals("")){
                    etNama.setError("Nama Harus Diisi");
                }
                else if(nim.trim().equals("")){
                    etNim.setError("Nim Harus Diisi");
                }
                else if(ipk.trim().equals("")){
                    etIpk.setError("IPK Harus Diisi");
                }
                else if(angkatan.trim().equals("")){
                    etAngkatan.setError("Angkatan Harus Diisi");
                }
                else if(semester.trim().equals("")){
                    etSemester.setError("Semester Harus Diisi");
                }
                else if(email.trim().equals("")){
                    etEmail.setError("Email Harus Diisi");
                }
                else if(telepon.trim().equals("")){
                    etTelepon.setError("Telepon Harus Diisi");
                }
                else{
                    createData();
                }
            }
        });
    }

    private void createData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> simpanData = ardData.ardCreateData(nama, nim, ipk, angkatan, semester, email, telepon);

        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(tambah.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(tambah.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
