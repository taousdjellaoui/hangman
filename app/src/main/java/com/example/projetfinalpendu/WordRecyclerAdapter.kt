package com.example.projetfinalpendu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordRecyclerAdapter(var wordList : ArrayList<Word>,var langue : String):
    RecyclerView.Adapter<WordRecyclerAdapter.MyViewHolder>(){
    var onItemClick:((Word)->Unit)?=null
    class MyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        var word : TextView
        init {
            word = itemView.findViewById(R.id.word)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_word,parent,false)
        return MyViewHolder(itemView)
    }
    override fun getItemCount(): Int {
        return wordList.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var wordtemp = ""
        if(langue=="français")  wordtemp = wordList[position].wordFr
        else wordtemp = wordList[position].wordEng

        holder.word.text = wordtemp

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(wordList[position])
        }
    }
    }