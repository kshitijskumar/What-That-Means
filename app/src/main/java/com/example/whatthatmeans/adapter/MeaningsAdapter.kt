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
            holder.itemView.tvId.append((position + 1).toString())
            holder.itemView.tvDefinition.append(currentMeaning.definition)
            holder.itemView.tvExample.append(currentMeaning.example)
            holder.itemView.tvSynonyms.append(stringBuilder.toString())
        }catch (e: Exception){
            Log.d("MeaningsAdapterCatch", e.message.toString())
        }
    }

    override fun getItemCount(): Int {
        return meaningsList.size
    }
}