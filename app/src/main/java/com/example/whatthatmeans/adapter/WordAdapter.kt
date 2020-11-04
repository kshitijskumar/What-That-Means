package com.example.whatthatmeans.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatthatmeans.R
import kotlinx.android.synthetic.main.word_extracted.view.*

class WordAdapter(var wordList: List<String>, val listener: WordSelected) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    class WordViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_extracted, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val currentWord = wordList[position]
        holder.itemView.tvWord.text = currentWord

        holder.itemView.setOnClickListener {
            listener.wordSelected(currentWord)
        }
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    interface WordSelected{
        fun wordSelected(word: String)
    }
}