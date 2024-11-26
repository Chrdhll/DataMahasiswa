package com.fadhil.datamahasiswa.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fadhil.datamahasiswa.DetailMahasiswaActivity
import com.fadhil.datamahasiswa.R
import com.fadhil.datamahasiswa.UpdateMahasiswaActivity
import com.fadhil.datamahasiswa.database.MahasiswaDatabaseHelper
import com.fadhil.datamahasiswa.model.Mahasiswa

class mahasiswaAdapter(private var mahasiswa: List<Mahasiswa> ,context: Context) : RecyclerView.Adapter<mahasiswaAdapter.MahasiswaViewHolder>() {
    private val db: MahasiswaDatabaseHelper = MahasiswaDatabaseHelper(context)
    class MahasiswaViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val nama = itemView.findViewById<TextView>(R.id.txtnama)
        val nim = itemView.findViewById<TextView>(R.id.txtnim)
        val jurusan = itemView.findViewById<TextView>(R.id.txtjurusan)
        val updatebutton = itemView.findViewById<ImageView>(R.id.updatebutton)
        val deletebutton = itemView.findViewById<ImageView>(R.id.deleteButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MahasiswaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mahasiswa, parent, false)
        return MahasiswaViewHolder(view)
    }

    override fun getItemCount(): Int = mahasiswa.size

    override fun onBindViewHolder(holder: MahasiswaViewHolder, position: Int) {
        val mahasiswa = mahasiswa[position]
        holder.nama.text = mahasiswa.nama
        holder.nim.text = mahasiswa.nim
        holder.jurusan.text = mahasiswa.jurusan

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailMahasiswaActivity::class.java).apply {
                // Mengirim data melalui intent
                putExtra("nama", mahasiswa.nama)
                putExtra("nim",mahasiswa.nim)
                putExtra("jurusan",mahasiswa.jurusan)
            }
            context.startActivity(intent)
        }

        holder.updatebutton.setOnClickListener(){
            val intent = Intent(holder.itemView.context, UpdateMahasiswaActivity::class.java).apply {
                putExtra("mahasiswa_id",mahasiswa.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deletebutton.setOnClickListener(){
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Konfirmasi")
                setMessage("Apakah anda ingin melanjutkan?")

                setPositiveButton("Yakin"){dialogInterface, i ->
                    db.deleteMahasiswa(mahasiswa.id) // Hapus catatan berdasarkan ID
                    refreshData(db.getAllMahasiswa()) // Perbarui daftar catatan
                }

                setNegativeButton("Batal"){dialogInterface, i->
                    dialogInterface.dismiss()
                }
            }.show()
        }

    }

    //fungsi untuk auto refresh list
    fun refreshData(newMahasiswa :List<Mahasiswa>){
        mahasiswa = newMahasiswa
        notifyDataSetChanged()
    }
}