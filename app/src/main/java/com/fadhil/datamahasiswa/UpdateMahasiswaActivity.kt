package com.fadhil.datamahasiswa

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fadhil.datamahasiswa.database.MahasiswaDatabaseHelper
import com.fadhil.datamahasiswa.databinding.ActivityUpdateMahasiswaBinding
import com.fadhil.datamahasiswa.model.Mahasiswa

class UpdateMahasiswaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUpdateMahasiswaBinding
    private lateinit var db : MahasiswaDatabaseHelper
    private var mahasiswaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUpdateMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MahasiswaDatabaseHelper(this)

        mahasiswaId = intent.getIntExtra("mahasiswa_id",-1)
        if(mahasiswaId == -1){
            finish()
            return
        }

        val mahasiswa = db.getMahasiswaById(mahasiswaId)
        binding.updateNamaEt.setText(mahasiswa.nama)
        binding.updateNimEt.setText(mahasiswa.nim)
        binding.updateJurusanEt.setText(mahasiswa.jurusan)

        binding.updatesaveButton.setOnClickListener(){
            val newNama = binding.updateNamaEt.text.toString()
            val newNim = binding.updateNimEt.text.toString()
            val newJurusan = binding.updateJurusanEt.text.toString()

            val updateMahasiswa = Mahasiswa(mahasiswaId,newNama,newNim,newJurusan)
            db.updatemahasiswa(updateMahasiswa)

            Toast.makeText(this,"Data mahasiswa diperbarui", Toast.LENGTH_SHORT).show()
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}