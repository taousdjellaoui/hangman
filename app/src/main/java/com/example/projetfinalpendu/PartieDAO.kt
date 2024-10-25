package com.example.projetfinalpendu

import HangmanDatabaseHelper
import android.content.ContentValues
import android.database.Cursor

class PartieDAO(private val helper:HangmanDatabaseHelper) {
    fun insertPartie(partie: Partie){
        val db = helper.writableDatabase
        val values = ContentValues().apply{
            put(HangmanDatabaseHelper.COLUMN_WORD, partie.word)
            put(HangmanDatabaseHelper.COLUMN_NIVDIFF, partie.nvDiff)
            put(HangmanDatabaseHelper.COLUMN_TIME, partie.time)
        }
        db.insert(HangmanDatabaseHelper.TABLE_NAME_HISTORIC, null, values)
        //db.close()
    }

    fun getAllParties(): List<Partie> {
        val partieList = mutableListOf<Partie>()
        val db = helper.readableDatabase
        val query = "SELECT * FROM ${HangmanDatabaseHelper.TABLE_NAME_HISTORIC}"
        val cursor: Cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val word = cursor.getString(cursor.getColumnIndexOrThrow(HangmanDatabaseHelper.COLUMN_WORD))
            val niv = cursor.getString(cursor.getColumnIndexOrThrow(HangmanDatabaseHelper.COLUMN_NIVDIFF))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(HangmanDatabaseHelper.COLUMN_TIME))
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(HangmanDatabaseHelper.COLUMN_ID))
            partieList.add(Partie(word, niv, time,id))
        }
        cursor.close()
        //db.close()
        return partieList
}
    fun deleteAllParties() {
        val db = helper.writableDatabase
        db.delete(HangmanDatabaseHelper.TABLE_NAME_HISTORIC, null, null)
        //db.close()
    }

}