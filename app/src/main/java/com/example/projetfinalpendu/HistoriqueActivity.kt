package com.example.projetfinalpendu

import HangmanDatabaseHelper
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoriqueActivity : AppCompatActivity() {
    lateinit var partieList: ArrayList<Partie>
    lateinit var partieDAO: PartieDAO
    lateinit var recycler: RecyclerView
    lateinit var titre: TextView
    lateinit var adapter: PartieRecyclerAdapter
    lateinit var btnRetourH: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historique)
        val helper = HangmanDatabaseHelper(this)
        partieDAO = PartieDAO(helper)
        recycler = findViewById(R.id.recyclerHistoric)
        partieList = ArrayList()
        titre = findViewById(R.id.titreH)
        btnRetourH = findViewById(R.id.btnRetourH)

        partieList = partieDAO.getAllParties() as ArrayList<Partie>
        setInfoAdapter()

        btnRetourH.setOnClickListener {
            finish()
        }
    }
    fun setInfoAdapter(){
        adapter = PartieRecyclerAdapter(partieList)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = adapter
        titre.text = "Historique: (parties: "+adapter.itemCount.toString()+ ")"
    }
}