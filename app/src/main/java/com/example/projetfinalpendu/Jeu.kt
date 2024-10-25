package com.example.projetfinalpendu

class Jeu(var listeMots: ArrayList<Word>,var langue:String) {
        var pointage: Int = 0
        var nbErreurs: Int = 0
        var motADeviner: String = ""

        init {
            if (listeMots.isEmpty()) throw IllegalArgumentException("La liste de mot " +
                    "ne peut pas être vide, ajouter un mot dans la liste!")
            else if (langue=="français") motADeviner = listeMots.random().wordFr.uppercase()
            else motADeviner = listeMots.random().wordEng.uppercase()
        }

        fun essayerUneLettre(lettre: Char): MutableList<Int> {
            var occurence = mutableListOf<Int>()
            var position: Int = 0
            if (motADeviner.contains(lettre)) {
                for (char in motADeviner) {
                        if (char == lettre){
                        occurence.add(position)
                        pointage++
                    }
                    position++
                }
            }
            else {
                nbErreurs++
            }
            return occurence
        }

        fun estRéussi(mot : String) : Boolean{
            return mot == motADeviner
        }
    }


