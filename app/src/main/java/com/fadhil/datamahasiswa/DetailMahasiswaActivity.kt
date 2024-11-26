package com.fadhil.datamahasiswa

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailMahasiswaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_mahasiswa)

        val nama = intent.getStringExtra("nama")
        val nim = intent.getStringExtra("nim")
        val jurusan = intent.getStringExtra("jurusan")

        val txtnama = findViewById<TextView>(R.id.txtnama)
        val txtnim = findViewById<TextView>(R.id.txtnim)
        val txtjurusan = findViewById<TextView>(R.id.txtjurusan)

        txtnama.text=nama
        txtnim.text=nim
        txtjurusan.text=jurusan

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}