package com.example.projetfinalpendu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AccueilActivity : AppCompatActivity() {
    lateinit var btnJouer : Button
    lateinit var btnPreference : Button
    lateinit var btnHistorique : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)
        btnJouer = findViewById(R.id.btnJouer)
        btnPreference = findViewById(R.id.btnPreference)
        btnHistorique = findViewById(R.id.btnHistorique)
        btnJouer.setOnClickListener{
            var intention  = Intent(this,PenduActivity::class.java)
            startActivity(intention)
        }
        btnPreference.setOnClickListener{
            var intention = Intent(this,PreferenceActivity::class.java)
            startActivity(intention)
        }
        btnHistorique.setOnClickListener{
            var intention = Intent(this,HistoriqueActivity::class.java)
            startActivity(intention)
        }
    }
}