package com.example.whatthatmeans

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatthatmeans.adapter.MeaningsAdapter
import com.example.whatthatmeans.model.BaseModelClass
import com.example.whatthatmeans.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_word_meaning.*

@AndroidEntryPoint
class WordMeaning : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val adapter by lazy { MeaningsAdapter(listOf()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_meaning)

        val word = intent.getStringExtra("Word")
        word?.let {
            viewModel.getMeaningFromRepository(it)
            tvWordChecked.text = it
        }

        rvMeaning.layoutManager = LinearLayoutManager(this)
        rvMeaning.adapter = adapter

        viewModel.wordMeaning.observe(this, {
            if (it != null){
                displayContent(it)
            }else{
                Log.d("WordMeaningActivity", "Else BLock null it")
            }
        })
    }

    private fun displayContent(meaning: BaseModelClass){
        meaning.word?.let {
            tvWordChecked.text = it
        }
        meaning.phonetics?.let {
            it[0].text?.let {phonetic ->
                tvPhonetics.text = phonetic
            }
        }
        meaning.meanings?.let {
            it[0].definitions?.let {definition ->
                adapter.meaningsList = definition
                adapter.notifyDataSetChanged()
            }
        }
    }
}