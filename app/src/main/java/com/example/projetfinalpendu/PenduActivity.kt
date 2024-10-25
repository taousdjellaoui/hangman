package com.example.projetfinalpendu

import HangmanDatabaseHelper
import PreferenceDAO
import WordDAO
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class PenduActivity : AppCompatActivity() {
    lateinit var jeu: Jeu;

    //DECLARATION DES BUTTONS DU CLAVIER
    lateinit var AButton: Button; lateinit var BButton: Button; lateinit var CButton: Button;
    lateinit var DButton: Button; lateinit var EButton: Button; lateinit var FButton: Button;
    lateinit var GButton: Button; lateinit var HButton: Button; lateinit var IButton: Button;
    lateinit var JButton: Button; lateinit var KButton: Button; lateinit var LButton: Button
    lateinit var MButton: Button; lateinit var NButton: Button; lateinit var OButton: Button;
    lateinit var PButton: Button; lateinit var QButton: Button; lateinit var RButton: Button;
    lateinit var SButton: Button; lateinit var TButton: Button; lateinit var UButton: Button;
    lateinit var VButton: Button; lateinit var WButton: Button; lateinit var XButton: Button
    lateinit var YButton: Button; lateinit var ZButton: Button

    //DECLARATION DE LA LIST DES BUTTONS
    lateinit var boutons: MutableList<Button>
    lateinit var image: ImageView

    //AUTRE DECLARATIONS
    lateinit var scoreText: TextView
    lateinit var motAtrouverText: TextView
    lateinit var dialog: Dialog
    lateinit var startButton: Button

    lateinit var wordDAO: WordDAO
    lateinit var prefDAO: PreferenceDAO
    lateinit var pref: Preference
    lateinit var langue: String
    lateinit var niveau: String
    lateinit var motADeviner : String
    //TIMER
    lateinit var timer : TextView
    var running = false
    var seconds = 0
    var time = ""

    lateinit var partie: Partie
    lateinit var partieDAO: PartieDAO
    //HISTORIC
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pendu)
        //RECUPARATION DES VIEWS DES BUTTONS DU CLAVIER
        //---------------------------------------------------------------------
        AButton = findViewById(R.id.Abutton);BButton = findViewById(R.id.Bbutton);CButton =findViewById(R.id.Cbutton)
        DButton = findViewById(R.id.Dbutton);EButton = findViewById(R.id.Ebutton);FButton = findViewById(R.id.Fbutton)
        GButton = findViewById(R.id.Gbutton);HButton = findViewById(R.id.Hbutton);IButton = findViewById(R.id.Ibutton)
        JButton = findViewById(R.id.Jbutton);KButton = findViewById(R.id.Kbutton);LButton = findViewById(R.id.Lbutton)
        MButton = findViewById(R.id.Mbutton);NButton = findViewById(R.id.Nbutton);OButton = findViewById(R.id.Obutton)
        PButton = findViewById(R.id.Pbutton);QButton = findViewById(R.id.Qbutton);RButton = findViewById(R.id.Rbutton)
        SButton = findViewById(R.id.Sbutton);TButton = findViewById(R.id.Tbutton);UButton = findViewById(R.id.Ubutton)
        VButton = findViewById(R.id.Vbutton);WButton = findViewById(R.id.Wbutton);XButton = findViewById(R.id.Xbutton)
        YButton = findViewById(R.id.Ybutton);ZButton = findViewById(R.id.Zbutton);
        //-------------------------------------------------------------------------
        //INITIALISER LES AFFICHAGES DES TEXTES VIEWS
        scoreText = findViewById(R.id.scoreText)
        motAtrouverText = findViewById(R.id.MotATrouverText)
        image = findViewById(R.id.PenduImage)
        dialog = Dialog(this@PenduActivity)
        startButton = findViewById(R.id.DebuterButton)
        timer = findViewById(R.id.timerTV)

        //AJOUT DES BUTTONS A UNE LIST BUTTONS
        initializeButtons()
        //---------------------------------------------------------------------------
        try {
            jeu = initialiser()
            runTimer()
        } catch (e: IllegalArgumentException) {
            Toast.makeText(
                this, e.message, Toast.LENGTH_LONG
            ).show()
            finish()
        }
        //----------------------------------------------------------------------------
        //ONCLICK LISTENER
        boutons.forEach { button ->
            button.setOnClickListener {
                val lettre = button.text[0]
                button.setEnabled(false)
                val occurences = jeu.essayerUneLettre(lettre)
                //JUST POUR TESTER ;)
                println("Occurrences pour la lettre '$lettre': $occurences")
                ChangerOccurence(occurences, button, jeu)
                //CHANGEMENT DE L'IMAGE
                ChangerImages(jeu.nbErreurs)
                if (jeu.nbErreurs > 6 || motAtrouverText.text.toString() == jeu.motADeviner) {
                    checkGameResult(jeu)
                }

            }
            startButton.setOnClickListener {
                dialog.setContentView(R.layout.confirmation_layout)
                dialog.show()
                val btn: Button = dialog.findViewById(R.id.OuiButton)
                val btn2: Button = dialog.findViewById(R.id.NonButton)
                btn.setOnClickListener {

                    this.jeu = initialiser()
                    dialog.dismiss()
                }
                btn2.setOnClickListener {
                    dialog.dismiss()
                }


            }


        }

    }

    //METHODE QUI PERMET D'INITIALISER LES DONNÉES DU JEU
    private fun initialiser(): Jeu {
        getPref()
        //AJOUT LISTE DE MOT DU DICTIONNAIRE EN FONCTION DES PRÉFÉRENCE
        val listeMots = wordDAO.getAllWordsLangNiv(niveau) as ArrayList<Word>
        var jeu = Jeu(listeMots,langue)
        motADeviner = jeu.motADeviner
        val lengthOfMot = motADeviner.length

        motAtrouverText.text = "*".repeat(lengthOfMot)
        scoreText.text = "0"
        jeu.pointage = 0
        jeu.nbErreurs = 0
        image.setBackgroundResource(R.drawable.first)

        boutons.forEach { button ->
            button.isEnabled = true
        }
        seconds = 0
        running = true
        return jeu
    }

    private fun initializeButtons() {
        // Initialize boutons list with button views
        boutons = mutableListOf(
            AButton, BButton, CButton, DButton, EButton,
            FButton, GButton, HButton, IButton, JButton,
            KButton, LButton, MButton, NButton, OButton,
            PButton, QButton, RButton, SButton, TButton,
            UButton, VButton, WButton, XButton, YButton,
            ZButton
        )
    }


    //MÉTHODE QUI PERMET DE CHANGER ET GÉERER LES OCCURANCE D'UNE LETTRE CHOISIE
    private fun ChangerOccurence(occurences: MutableList<Int>, button: Button, jeu: Jeu) {
        val motAtrouverStringBuilder = StringBuilder(motAtrouverText.text)

        for (i in occurences) {

            if (i >= 0 && i < motAtrouverStringBuilder.length) {

                motAtrouverStringBuilder[i] = button.text[0]
                //CHANGEMENT DE SCORE
                scoreText.text = jeu.pointage.toString()
                //JUST POUR TESTER ;)
                println(" nombre d'erreur '${jeu.nbErreurs}'")

            }
        }
        motAtrouverText.text = motAtrouverStringBuilder.toString()
        //return motAtrouverText.toString()

    }

    //MÉTHODE QUI PERMET DE CHANGE L'IMAGE LORS D'UNE TENTATIVE ÉRONNÉ
    private fun ChangerImages(erreur: Int) {
        //ON CHANGE D'IMAGE EN FONCTION DU NOMBRE D'ERREUR FAIT
        when (erreur) {

            1 -> image.setBackgroundResource(R.drawable.second)
            2 -> image.setBackgroundResource(R.drawable.third)
            3 -> image.setBackgroundResource(R.drawable.four)
            4 -> image.setBackgroundResource(R.drawable.five)
            5 -> image.setBackgroundResource(R.drawable.six)
            6 -> image.setBackgroundResource(R.drawable.seven)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun checkGameResult(jeu: Jeu) {
        // Set the content view based on game result
        if (jeu.estRéussi(motAtrouverText.text.toString())) {
            running = false
            partie = Partie(motADeviner,niveau,time)
            partieDAO.insertPartie(partie)
            dialog.setContentView(R.layout.victoire)
        } else {
            running = false
            partie = Partie(motADeviner,niveau,time)
            partieDAO.insertPartie(partie)
            dialog.setContentView(R.layout.defaite)
            val mot: TextView = dialog.findViewById(R.id.PerduTextView)
            mot.text = "Le mot à deviner était ${jeu.motADeviner}"
        }

        // Set up button click listener inside the dialog
        val btn: Button = dialog.findViewById(R.id.btn_rejouer)
        btn.setOnClickListener {
            // PERMET DE COMMENCER UNE NOUVELLE PARTIE
            //SI ERREUR FAIT:SURPRESS
            this.jeu = initialiser()
            dialog.dismiss()
        }
        // Show the dialog
        dialog.show()
    }

    private fun getPref() {
        val helper = HangmanDatabaseHelper(this)
        prefDAO = PreferenceDAO(helper)
        wordDAO = WordDAO(helper)
        partieDAO = PartieDAO(helper)

        pref = prefDAO.getPref()!!

        langue = if (pref.language == "français") "français" else "english"
        niveau = if (pref.nvDiff == "facile") "facile"
        else if (pref.nvDiff == "moyen") "moyen"
        else "difficile"
    }


    fun retour(view: View?) {
        finish()
    }

    fun runTimer(){
        val handler = Handler()
        handler.post(object:Runnable {
            override fun run(){
                val hours = seconds / 3600
                val minutes = seconds % 3600 / 60
                val secs = seconds % 60
                time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, secs)
                timer.text = time
                if(running){
                    seconds ++
                }
                handler.postDelayed(this,1000)
            }
        })
    }

}


