package com.example.projetfinalpendu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PartieRecyclerAdapter(var partieList: List<Partie>) :
    RecyclerView.Adapter<PartieRecyclerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var wordH: TextView
        var niveauH: TextView
        var timeH: TextView
        init {
            wordH = itemView.findViewById(R.id.wordH)
            niveauH = itemView.findViewById(R.id.niveauH)
            timeH = itemView.findViewById(R.id.timeH)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_parties,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var wordTemp : String  = partieList[position].word
        var niveauH : String  = partieList[position].nvDiff
        var timeH : String  = partieList[position].time
        holder.wordH.text = wordTemp
        holder.niveauH.text = niveauH
        holder.timeH.text = timeH
    }
    override fun getItemCount(): Int {
        return partieList.size
    }

}
