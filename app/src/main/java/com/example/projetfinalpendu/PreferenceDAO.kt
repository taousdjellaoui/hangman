import android.content.ContentValues
import android.database.Cursor
import com.example.projetfinalpendu.Preference

class PreferenceDAO(private val helper:HangmanDatabaseHelper){
        //context: Context, dbName: String, dbVersion: Int) {
    fun insertPref(pref : Preference) {
        val db = helper.writableDatabase
        val values = ContentValues().apply {
            put(HangmanDatabaseHelper.COLUMN_LANGUAGE, pref.language)
            put(HangmanDatabaseHelper.COLUMN_NIVDIFF, pref.nvDiff)
        }
        db.insert(HangmanDatabaseHelper.TABLE_NAME_PREFERENCE, null, values)
        //db.close()
    }
    fun getPref(): Preference? {
        var pref : Preference? = null
        val db = helper.readableDatabase
        val query = "SELECT * FROM ${HangmanDatabaseHelper.TABLE_NAME_PREFERENCE}"
        val cursor: Cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val langue = cursor.getString(cursor.getColumnIndexOrThrow(HangmanDatabaseHelper.COLUMN_LANGUAGE))
            val niv = cursor.getString(cursor.getColumnIndexOrThrow(HangmanDatabaseHelper.COLUMN_NIVDIFF))
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(HangmanDatabaseHelper.COLUMN_ID))
            pref = Preference(langue,niv,id)
        }
        cursor.close()
        //db.close()
        return pref
    }
    fun updatePref(pref: Preference){
        val db = helper.writableDatabase
        val values = ContentValues().apply {
            put(HangmanDatabaseHelper.COLUMN_LANGUAGE, pref.language)
            put(HangmanDatabaseHelper.COLUMN_NIVDIFF, pref.nvDiff)
        }
        val selection = "${HangmanDatabaseHelper.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(pref.id.toString())
        db.update(HangmanDatabaseHelper.TABLE_NAME_PREFERENCE,values, selection,selectionArgs)
        //db.close()
    }
    fun updateDB(){
        helper.onUpgrade(helper.writableDatabase,1,2)
    }
}
