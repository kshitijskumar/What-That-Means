package com.example.whatthatmeans.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatthatmeans.R
import com.example.whatthatmeans.model.Definitions
import kotlinx.android.synthetic.main.meaning_list.view.*
import java.lang.Exception
import java.lang.StringBuilder

class MeaningsAdapter(var meaningsList: List<Definitions>) : RecyclerView.Adapter<MeaningsAdapter.MeaningsViewHolder>() {

    class MeaningsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meaning_list, parent, false)
        return MeaningsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeaningsViewHolder, position: Int) {
        try{
            val currentMeaning = meaningsList[position]
            val stringBuilder = StringBuilder()
            currentMeaning.synonyms?.let {
                for (synonym in it) {
                    stringBuilder.append("$synonym, ")
                }
            }
            val meaningId = "Meaning: ${position+1}"
            val meaningDefinition = "Definition: ${currentMeaning.definition}"
            val meaningExample = "Example: ${currentMeaning.example}"
            val meaningSynonym = "Synonyms: $stringBuilder"

            holder.itemView.tvId.text = meaningId
            holder.itemView.tvDefinition.text = meaningDefinition
            holder.itemView.tvExample.text = meaningExample
            holder.itemView.tvSynonyms.text = meaningSynonym
        }catch (e: Exception){
            Log.d("MeaningsAdapterCatch", e.message.toString())
        }
    }

    override fun getItemCount(): Int {
        return meaningsList.size
    }
}