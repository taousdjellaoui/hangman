package com.example.projetfinalpendu

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class JeuTest() {
    val listeRemplie = arrayListOf(
        Word("papier", "paper", "facile", 1),
    )
    var listeVide = arrayListOf<Word>()
    var jeu: Jeu = Jeu(listeRemplie, "Français")

    @Test
    fun constructeurMissingArgument() {
        println("constructeur avec liste vide")
        assertThrows(IllegalArgumentException::class.java) {
            Jeu(listeVide, "Francais")
        }
    }

    @Test
    fun constructeurAvecArguments() {
        println("contructeur avec liste non vide")
        val lettre = 'P'
        val expResult = mutableListOf(0, 2)
        val occurence = jeu.essayerUneLettre(lettre)

        val result = occurence
        assertEquals(expResult, result)
    }
    @Test
    fun estRéussiOui() {
        println("essayerRéussi : quand true")
        var mot = "papier"
        jeu.motADeviner = "papier"

        println(jeu.motADeviner)

        val result = jeu.estRéussi(mot)
        val expResult = true
        assertEquals(expResult, result)
    }
    @Test
    fun estRéussiNon() {
        println("essayerRéussi : quand false")
        var mot = "papote"
        jeu.motADeviner = "papier"

        println(jeu.motADeviner)

        val result = jeu.estRéussi(mot)
        val expResult = false
        assertEquals(expResult, result)
    }
    @Test
    fun essayerUneLettreOccurence() {
        println("essayerUneLettre 'P' ")
        val letter = 'p'
        jeu.motADeviner = "papier"
        val occurence = jeu.essayerUneLettre(letter)

        println(jeu.motADeviner)

        val result = occurence
        val expResult = mutableListOf(0, 2)
        assertEquals(expResult, result)
    }

    @Test
    fun essayerUneLettreErreur() {
        println("essayerUneLettreErreur 'o'")
        val letter = 'o'
        jeu.motADeviner = "papier"
        val occurrence = jeu.essayerUneLettre(letter)

        val expectedOccurrence = mutableListOf<Int>()
        assertEquals(expectedOccurrence, occurrence)
    }
    
    @Test
    fun getPointageAvecBonneLettre()
    {
       println("getPointageAvecBonneLettre")
        val letter = 'p'
        jeu.motADeviner = "papier"
        jeu.essayerUneLettre(letter)

        val result2 = jeu.pointage
        val expResult2 = 2
        assertEquals(expResult2, result2)
    }

    @Test
    fun getNbErreursAvecBonneLettre()
    {
        println("getNbErreursAvecBonneLettre")
        val letter = 'p'
        jeu.motADeviner = "papier"
        jeu.essayerUneLettre(letter)

        val result1 = jeu.nbErreurs
        val expResult1 = 0
        assertEquals(expResult1, result1)
    }
    @Test
    fun getPointageAvecMauvaiseLettre()
    {
        println("getPointageAvecMauvaiseLettre")
        val letter = 'o'
        jeu.motADeviner = "papier"
        jeu.essayerUneLettre(letter)

        val expResult = 0
        val result = jeu.pointage
        assertEquals(expResult, result)

    }

    @Test
    fun getNbErreursAvecMauvaiseLettre()
    {
        println("getNbErreursAvecMauvaiseLettre")
        val letter = 'o'
        jeu.motADeviner = "papier"
        jeu.essayerUneLettre(letter)

        val expResult = 1
        val result = jeu.nbErreurs
        assertEquals(expResult, result)

    }
}
