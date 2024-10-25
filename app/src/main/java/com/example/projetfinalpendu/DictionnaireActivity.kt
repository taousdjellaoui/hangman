package com.example.projetfinalpendu

import HangmanDatabaseHelper
import WordDAO
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DictionnaireActivity : AppCompatActivity(){
    lateinit var wordList : ArrayList<Word>
    lateinit var wordDAO: WordDAO
    lateinit var recycler : RecyclerView
    lateinit var btnAjouter : Button
    lateinit var btnRetour: Button
    lateinit var adapter: WordRecyclerAdapter
    lateinit var langue: String
    lateinit var niveau :String
    lateinit var spinnerLang : Spinner
    lateinit var spinnerNv : Spinner

    val CODE_ADD = 300
    companion object{
        var word:Word? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionnaire)
        setSpinnerAdapter()
        setInfoSpinner()

        val helper = HangmanDatabaseHelper(this)
        wordDAO = WordDAO(helper)

        recycler = findViewById(R.id.recyclerHistoric)
        wordList = ArrayList()
        btnAjouter = findViewById(R.id.btnAjouter)
        btnRetour = findViewById(R.id.btnRetour)
        btnAjouter.setOnClickListener{
            var intention : Intent = Intent(this,AjouterMot::class.java)
            startActivityForResult(intention,CODE_ADD)
        }
        btnRetour.setOnClickListener{
            finish()
        }

        langue =  spinnerLang.selectedItem.toString()
        niveau = spinnerNv.selectedItem.toString()
        wordList = wordDAO.getAllWordsLangNiv(niveau) as ArrayList<Word>
        setInfoAdapter()
    }
    @SuppressLint("SetTextI18n")
    fun setInfoAdapter(){
        adapter = WordRecyclerAdapter(wordList,langue)
        var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        adapter.onItemClick={word ->
            DictionnaireActivity.word = word
            var intention = Intent(this,AjouterMot::class.java)
            startActivity(intention)
        }
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = adapter
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CODE_ADD->{
                if(resultCode== RESULT_OK){
                    //wordDAO.updateDB() //pour refaire la db
                    var wordFrInfo = data?.extras?.getString("wordFrInfo")?:""
                    var wordEngInfo = data?.extras?.getString("wordEngInfo")?:""
                    var niveauInfo = data?.extras?.getString("niveauInfo")?:""
                    var word = Word(wordFrInfo,wordEngInfo,niveauInfo)

                    wordDAO.insertWord(word)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(
                        this, "Mots $wordFrInfo/$wordEngInfo ajoutés avec succès ($niveauInfo)", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        langue =  spinnerLang.selectedItem.toString()
        niveau = spinnerNv.selectedItem.toString()
        word=null
        langue = when (langue) {
            "Français", "French" -> "français"
            else -> "english"
        }
        niveau = when (niveau) {
            "Easy", "Facile" -> "facile"
            "Medium", "Moyen" -> "moyen"
            else -> "difficile"
        }
        wordList.clear()
        wordList.addAll(wordDAO.getAllWordsLangNiv(niveau) as ArrayList<Word>)
        //adapter = WordRecyclerAdapter(wordList,langue)
        setInfoAdapter()
        //adapter.notifyDataSetChanged()
    }

    fun setSpinnerAdapter(){
        spinnerLang = findViewById(R.id.spinnerLang)
        spinnerNv = findViewById(R.id.spinnerNv)
        ArrayAdapter.createFromResource(this, R.array.langueSpinner,
            android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerLang.adapter = adapter
        }
        ArrayAdapter.createFromResource(this, R.array.niveauSpinner,
            android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerNv.adapter = adapter
        }
    }
    fun setInfoSpinner(){
        spinnerLang.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                var tempLang = parent.getItemAtPosition(position).toString()
                Toast.makeText(this@DictionnaireActivity, "Selected: $tempLang", Toast.LENGTH_LONG).show()
                if(tempLang=="Français" || tempLang=="French"){
                    tempLang="français"
                }
                else tempLang = "english"
                langue = tempLang
                wordList = wordDAO.getAllWordsLangNiv(niveau) as ArrayList<Word>
                setInfoAdapter()
                //adapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
        spinnerNv.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                var tempNiv = parent.getItemAtPosition(position).toString()
                Toast.makeText(this@DictionnaireActivity, "Selected: $tempNiv", Toast.LENGTH_LONG).show()
                if (tempNiv =="Easy" || tempNiv == "Facile"){
                    tempNiv ="facile"
                } else if (tempNiv =="Medium" || tempNiv == "Moyen") {
                    tempNiv = "moyen"
                }
                else tempNiv = "difficile"
                niveau = tempNiv

                wordList = wordDAO.getAllWordsLangNiv(niveau) as ArrayList<Word>
                setInfoAdapter()
                //adapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }


}