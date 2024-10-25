package com.example.projetfinalpendu

import HangmanDatabaseHelper
import WordDAO
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class AjouterMot : AppCompatActivity() {
    lateinit var motFr : EditText
    lateinit var motEng: EditText
    lateinit var radioFacile : RadioButton
    lateinit var radioMoyen : RadioButton
    lateinit var radioDifficile : RadioButton
    lateinit var langue : String
    lateinit var niveau : String
    lateinit var btnSave: Button
    lateinit var btnSupprimer : Button
    lateinit var btnCancel : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_word)
        motFr = findViewById(R.id.motAddFr)
        motEng = findViewById(R.id.motAddEng)
        radioFacile = findViewById(R.id.radioFacile)
        radioMoyen = findViewById(R.id.radioMoyen)
        radioDifficile = findViewById(R.id.radioDifficile)
        btnSave = findViewById(R.id.btnSaving)
        btnSupprimer = findViewById(R.id.btnSupprimer)
        btnCancel = findViewById(R.id.btnCancel)

        if(DictionnaireActivity.word!=null){
            motFr.setText(DictionnaireActivity.word?.wordFr.toString())
            motEng.setText(DictionnaireActivity.word?.wordEng.toString())
            disable()
            when (DictionnaireActivity.word!!.nvDiff) {
                "facile" -> radioFacile.setChecked(true)
                "moyen" -> radioMoyen.setChecked(true)
                else -> radioDifficile.setChecked(true)
            }
        }
        btnSave.setOnClickListener{
            if(DictionnaireActivity.word == null) {
                setCheck()
                var intention = Intent()
                var nomFr = motFr.text.toString()
                var nomEng = motEng.text.toString()
                intention.putExtra("wordFrInfo", nomFr)
                intention.putExtra("wordEngInfo", nomEng)
                intention.putExtra("niveauInfo", niveau)
                DictionnaireActivity.word = null
                setResult(RESULT_OK, intention)
                finish()
            }
        }
        btnCancel.setOnClickListener{
                DictionnaireActivity.word=null
                finish()
        }
        btnSupprimer.setOnClickListener{
            if(DictionnaireActivity.word!=null){
                var dao = WordDAO(HangmanDatabaseHelper(this))
                dao.deleteWord(DictionnaireActivity.word!!)
                finish()
            }
        }
    }
    fun setCheck(){
        niveau = if(radioFacile.isChecked) "facile" else if(radioMoyen.isChecked) "moyen" else "difficile"
    }
    fun disable(){
        motFr.setFocusable(false);
        motFr.setEnabled(false);
        motFr.setCursorVisible(false);
        btnSave.setEnabled(false);
        motEng.setFocusable(false);
        motEng.setEnabled(false);
        motEng.setCursorVisible(false);
        radioDifficile.isEnabled = false;
        radioMoyen.isEnabled=false;
        radioFacile.isEnabled=false;
    }
}