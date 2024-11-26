package com.fadhil.datamahasiswa

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadhil.datamahasiswa.adapter.mahasiswaAdapter
import com.fadhil.datamahasiswa.database.MahasiswaDatabaseHelper
import com.fadhil.datamahasiswa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: MahasiswaDatabaseHelper
    private lateinit var mahasiswaAdapter : mahasiswaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MahasiswaDatabaseHelper(this)
        mahasiswaAdapter = mahasiswaAdapter(db.getAllMahasiswa(),this)

        binding.mahasiswaRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mahasiswaRecyclerView.adapter = mahasiswaAdapter

        binding.addButton.setOnClickListener(){
            val intent = Intent(this,AddMahasiswaActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume(){
        super.onResume()
        val mahasiswa = db.getAllMahasiswa()
        mahasiswaAdapter.refreshData(mahasiswa)

    }
}