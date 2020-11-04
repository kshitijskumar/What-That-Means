package com.example.whatthatmeans

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.whatthatmeans.adapter.WordAdapter
import com.example.whatthatmeans.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_words_list.*

class WordsListActivity : AppCompatActivity(), WordAdapter.WordSelected {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words_list)

        Log.d("Order", "In WordsActivity")
        rvWordsList.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        val adapter = WordAdapter(listOf(),this)
        rvWordsList.adapter = adapter

        MainViewModel.textExtractedLiveData.observe(this, {
            Log.d("ObservingWords", "${it.size}")
            if (it.isNotEmpty()){
                adapter.wordList = it
                adapter.notifyDataSetChanged()
            }else{
                Log.d("WordsListActivity", "Empty list observed")
            }
        })
    }

    override fun wordSelected(word: String) {
        Intent(this, WordMeaning::class.java).also {
            it.putExtra("Word", word)
            startActivity(it)
        }
    }
}