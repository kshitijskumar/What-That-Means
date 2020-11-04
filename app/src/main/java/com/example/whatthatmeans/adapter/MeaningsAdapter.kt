package com.example.whatthatmeans.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatthatmeans.R
import com.example.whatthatmeans.model.Definitions
import kotlinx.android.synthetic.main.meaning_list.view.*
import java.lang.StringBuilder

class MeaningsAdapter(var meaningsList: List<Definitions>) : RecyclerView.Adapter<MeaningsAdapter.MeaningsViewHolder>() {

    class MeaningsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meaning_list, parent, false)
        return MeaningsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeaningsViewHolder, position: Int) {
        val currentMeaning = meaningsList[position]
        val stringBuilder = StringBuilder()
        currentMeaning.synonyms?.let {
            for (synonym in it){
                stringBuilder.append("$synonym, ")
            }
        }
        holder.itemView.tvId.text = (position+1).toString()
        holder.itemView.tvDefinition.text = currentMeaning.definition
        holder.itemView.tvExample.text = currentMeaning.example
        holder.itemView.tvSynonyms.text = stringBuilder.toString()
    }

    override fun getItemCount(): Int {
        return meaningsList.size
    }
}