import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.projetfinalpendu.Word

class HangmanDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        public const val DATABASE_VERSION = 1
        public const val DATABASE_NAME = "HangmanWords.db"
        public const val TABLE_NAME_WORDS = "words"
        public const val TABLE_NAME_PREFERENCE = "preference"
        public const val TABLE_NAME_HISTORIC = "history"
        public const val COLUMN_ID = "id"
        public const val COLUMN_LANGUAGE = "language"
        public const val COLUMN_WORDFR = "wordFr"
        public const val COLUMN_WORDENG = "wordEng"
        public const val COLUMN_NIVDIFF = "nv"
        public const val COLUMN_WORD = "word"
        public const val COLUMN_TIME = "time"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME_WORDS($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_WORDFR TEXT, $COLUMN_WORDENG TEXT, $COLUMN_NIVDIFF TEXT)"
        db.execSQL(createTableQuery)
        insertWords(db,wordsListInitial)

        val createTableQuery3 = "CREATE TABLE $TABLE_NAME_PREFERENCE($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_LANGUAGE TEXT, $COLUMN_NIVDIFF TEXT)"
        db.execSQL(createTableQuery3)

        val prefInitial = ContentValues()
        prefInitial.put(COLUMN_LANGUAGE, "français")
        prefInitial.put(COLUMN_NIVDIFF, "facile")
        db.insert(TABLE_NAME_PREFERENCE, null, prefInitial)

        val createTableQuery4 = "CREATE TABLE $TABLE_NAME_HISTORIC($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_WORD TEXT,$COLUMN_NIVDIFF TEXT, $COLUMN_TIME TEXT)"
        db.execSQL(createTableQuery4)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_WORDS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_PREFERENCE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_HISTORIC")

        onCreate(db)
    }
    //insertion wordsList initiale
    fun insertWords(db: SQLiteDatabase,words: List<Word>) {
        for (word in words) {
            val wordValues = ContentValues().apply {
                put(COLUMN_WORDFR, word.wordFr)
                put(COLUMN_WORDENG, word.wordEng)
                put(COLUMN_NIVDIFF, word.nvDiff)
            }
            db.insert(TABLE_NAME_WORDS, null, wordValues)
        }
    }
    //Création liste de mot
    var wordsListInitial = listOf(
        Word("soleil","sun","facile"),
        Word("poulet","chicken","facile"),
        Word("avion","plane","facile"),

        Word("mercure","mercury","moyen"),
        Word("lune","moon","moyen"),
    )

}