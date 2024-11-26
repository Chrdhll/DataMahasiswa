package com.fadhil.datamahasiswa.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.fadhil.datamahasiswa.model.Mahasiswa

class MahasiswaDatabaseHelper(context: Context):
    SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "datamahasiswa.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allmahasiswa"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAMA = "nama"
        private const val COLUMN_NIM = "nim"
        private const val COLUMN_JURUSAN = "jurusan"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CreateTableQuery =
            "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_NAMA TEXT,$COLUMN_NIM TEXT,$COLUMN_JURUSAN TEXT)"
        db?.execSQL(CreateTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertMahasiswa(mahasiswa: Mahasiswa) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAMA, mahasiswa.nama)
            put(COLUMN_NIM, mahasiswa.nim)
            put(COLUMN_JURUSAN, mahasiswa.jurusan)
        }

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllMahasiswa(): List<Mahasiswa> {
        val mahasiswaList = mutableListOf<Mahasiswa>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
                val nim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM))
                val jurusan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JURUSAN))
                mahasiswaList.add(Mahasiswa(id, nama, nim, jurusan))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return mahasiswaList
    }

    fun updatemahasiswa(mahasiswa: Mahasiswa) {
        val db = writableDatabase
        val value = ContentValues().apply {
            put(COLUMN_NAMA, mahasiswa.nama)
            put(COLUMN_NIM, mahasiswa.nim)
            put(COLUMN_JURUSAN, mahasiswa.jurusan)
        }

        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(mahasiswa.id.toString())
        db.update(TABLE_NAME, value, whereClause, whereArgs)
        db.close()
    }

    fun getMahasiswaById(mahasiswaId: Int):Mahasiswa{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=$mahasiswaId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
        val nim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM))
        val jurusan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JURUSAN))

        cursor.close()
        db.close()
        return Mahasiswa(id,nama,nim, jurusan)
    }

    fun deleteMahasiswa(mahasiswaId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(mahasiswaId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }

}
