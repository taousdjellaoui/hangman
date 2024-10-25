package com.example.projetfinalpendu

import HangmanDatabaseHelper
import PreferenceDAO
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PreferenceActivity : AppCompatActivity() {
    lateinit var pref: Preference
    lateinit var prefDAO: PreferenceDAO
    lateinit var radioButtonFrancais: RadioButton
    lateinit var radioButtonEnglish: RadioButton
    lateinit var radioButtonFacile: RadioButton
    lateinit var radioButtonMoyen: RadioButton
    lateinit var radioButtonDifficile: RadioButton
    lateinit var btnDictionnaire: Button
    lateinit var btnSave: Button
    lateinit var btnRetourner: Button

    companion object {
        lateinit var lang: String
        lateinit var niv: String
    }

    lateinit var langue: String
    lateinit var niveau: String
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)
        val helper = HangmanDatabaseHelper(this)
        //ajout du helper explicitement
        prefDAO = PreferenceDAO(helper)

        //prefDAO.updateDB() //pour refaire la db
        pref = prefDAO.getPref()!!

        radioButtonFrancais = findViewById(R.id.radioButtonFrancais)
        radioButtonEnglish = findViewById(R.id.radioButtonEnglish)
        radioButtonFacile = findViewById(R.id.radioButtonFacile)
        radioButtonMoyen = findViewById(R.id.radioButtonMoyen)
        radioButtonDifficile = findViewById(R.id.radioButtonDifficile)
        btnSave = findViewById(R.id.btnSave)
        btnRetourner = findViewById(R.id.btnRetourner)
        btnDictionnaire = findViewById(R.id.btnDictionnaire)
        btnDictionnaire.setOnClickListener {
            var intention: Intent = Intent(this, DictionnaireActivity::class.java)
            startActivity(intention)
        }
        setInfoAdapter()
        setPref()
        btnSave.setOnClickListener {
            setCheck()
            pref.language = langue
            pref.nvDiff = niveau
            prefDAO.updatePref(pref)
            setPref()
            Toast.makeText(
                this, "Paramètre langue changé à $langue et " +
                        "difficulté à $niveau", Toast.LENGTH_LONG
            ).show()
        }
        btnRetourner.setOnClickListener {
            finish()
        }
    }

    fun setInfoAdapter() {
        if (pref.language == "français") radioButtonFrancais.setChecked(true) else radioButtonEnglish.setChecked(
            true
        )
        when (pref.nvDiff) {
            "facile" -> radioButtonFacile.setChecked(true)
            "moyen" -> radioButtonMoyen.setChecked(true)
            else -> radioButtonDifficile.setChecked(true)
        }
    }

    fun setCheck() {
        langue = if (radioButtonFrancais.isChecked) "français" else "english"
        niveau =
            if (radioButtonFacile.isChecked) "facile" else if (radioButtonMoyen.isChecked) "moyen" else "difficile"
    }

    fun setPref(){
        lang = if (pref.language == "français") "français" else "english"
        niv = if (pref.nvDiff == "facile") "facile"
        else if (pref.nvDiff == "moyen") "moyen"
        else "difficile"
    }
}