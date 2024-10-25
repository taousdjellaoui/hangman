import android.content.ContentValues
import android.database.Cursor
import com.example.projetfinalpendu.Word

class WordDAO(private val helper:HangmanDatabaseHelper){
        //context: Context, dbName: String, dbVersion: Int) {
    fun insertWord(word : Word) {
        val db = helper.writableDatabase
        val values = ContentValues().apply {
            put(HangmanDatabaseHelper.COLUMN_WORDFR, word.wordFr)
            put(HangmanDatabaseHelper.COLUMN_WORDENG, word.wordEng)
            put(HangmanDatabaseHelper.COLUMN_NIVDIFF, word.nvDiff)
        }
        db.insert(HangmanDatabaseHelper.TABLE_NAME_WORDS, null, values)
        //db.close()
    }
    fun getAllWords(): List<Word> {
        val wordList = mutableListOf<Word>()
        val db = helper.readableDatabase
        val query = "SELECT * FROM ${HangmanDatabaseHelper.TABLE_NAME_WORDS}"
        val cursor: Cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val wordFr = cursor.getString(cursor.getColumnIndexOrThrow(HangmanDatabaseHelper.COLUMN_WORDFR))
            val wordEng = cursor.getString(cursor.getColumnIndexOrThrow(HangmanDatabaseHelper.COLUMN_WORDENG))
            val niv = cursor.getString(cursor.getColumnIndexOrThrow(HangmanDatabaseHelper.COLUMN_NIVDIFF))
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(HangmanDatabaseHelper.COLUMN_ID))
            wordList.add(Word(wordFr, wordEng, niv,id))
        }
        cursor.close()
        //db.close()
        return wordList
    }

    fun getAllWordsLangNiv(niveau: String): List<Word> {
        val wordList = mutableListOf<Word>()
        val db = helper.readableDatabase
        val query = "SELECT * FROM ${HangmanDatabaseHelper.TABLE_NAME_WORDS} " +
                "WHERE ${HangmanDatabaseHelper.COLUMN_NIVDIFF} = ?"
        val selectionArgs = arrayOf(niveau)
        val cursor: Cursor = db.rawQuery(query, selectionArgs)
        while (cursor.moveToNext()) {
            val wordFr = cursor.getString(cursor.getColumnIndexOrThrow(HangmanDatabaseHelper.COLUMN_WORDFR))
            val wordEng = cursor.getString(cursor.getColumnIndexOrThrow(HangmanDatabaseHelper.COLUMN_WORDENG))
            val niv = cursor.getString(cursor.getColumnIndexOrThrow(HangmanDatabaseHelper.COLUMN_NIVDIFF))
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(HangmanDatabaseHelper.COLUMN_ID))
            wordList.add(Word(wordFr, wordEng, niv,id))
            }
        cursor.close()
        //db.close()
        return wordList
    }
    fun updateWord(word: Word){
        val db = helper.writableDatabase
        val values = ContentValues().apply {
            put(HangmanDatabaseHelper.COLUMN_WORDFR, word.wordFr)
            put(HangmanDatabaseHelper.COLUMN_WORDENG, word.wordEng)
            put(HangmanDatabaseHelper.COLUMN_NIVDIFF, word.nvDiff)
        }
        val selection = "${HangmanDatabaseHelper.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(word.id.toString())
        db.update(HangmanDatabaseHelper.TABLE_NAME_WORDS,values, selection,selectionArgs)
        //db.close()
    }
    fun deleteWord(word: Word){
        val db = helper.writableDatabase
        val selection = "${HangmanDatabaseHelper.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(word.id.toString())
        db.delete(HangmanDatabaseHelper.TABLE_NAME_WORDS, selection,selectionArgs)
        //db.close()
    }
    fun deleteAllWords() {
        val db = helper.writableDatabase
        db.delete(HangmanDatabaseHelper.TABLE_NAME_WORDS, null, null)
        //db.close()
    }
    fun updateDB(){
        helper.onUpgrade(helper.writableDatabase,1,2)
    }
}
