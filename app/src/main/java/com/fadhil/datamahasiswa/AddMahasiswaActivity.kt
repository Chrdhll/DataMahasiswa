package com.fadhil.datamahasiswa

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fadhil.datamahasiswa.database.MahasiswaDatabaseHelper
import com.fadhil.datamahasiswa.databinding.ActivityAddMahasiswaBinding
import com.fadhil.datamahasiswa.model.Mahasiswa

class AddMahasiswaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMahasiswaBinding
    private lateinit var db: MahasiswaDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MahasiswaDatabaseHelper(this)

        binding.saveButton.setOnClickListener(){
            val nama = binding.NamaEt.text.toString()
            val nim = binding.NimEt.text.toString()
            val jurusan = binding.JurusanEt.text.toString()
            val mahasiswa = Mahasiswa(0,nama,nim,jurusan)
            db.insertMahasiswa(mahasiswa)
            finish()
            Toast.makeText(this,"Data mahasiswa berhasil disimpan", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}