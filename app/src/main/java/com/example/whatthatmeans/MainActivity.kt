package com.example.whatthatmeans

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.whatthatmeans.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

private const val CAPTURE_IMAGE = 0
private const val GET_IMAGE = 1

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCaptureImage.setOnClickListener {
            captureImage()
        }

        btnGetImage.setOnClickListener {
            getImage()
        }

        btnSearchWithWord.setOnClickListener {
            searchMeaningWithWordEntered()
        }
    }

    private fun captureImage(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            try {
                startActivityForResult(it, CAPTURE_IMAGE)
            }catch (e: Exception){
                Log.d("MainActivity", e.message.toString())
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getImage(){
        Intent(Intent.ACTION_GET_CONTENT).also{
            it.type = "image/*"
            startActivityForResult(it, GET_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK){
            val image = data?.extras?.get("data") as Bitmap
            ivImage.setImageBitmap(image)
            viewModel.createInputImageUsingBitmap(image)
            goToWordsListActivity()
        }else if (requestCode == GET_IMAGE && resultCode == RESULT_OK){
            val image = data?.data
            ivImage.setImageURI(image)
            viewModel.createInputImageUsingUri(image)
            goToWordsListActivity()
        }
    }

    private fun goToWordsListActivity(){
        val intent = Intent(this, WordsListActivity::class.java)
        startActivity(intent)
    }

    private fun searchMeaningWithWordEntered(){
        val word = etWordSearch.text.toString()
        if (word.isNotEmpty()){
            val intent = Intent(this, WordMeaning::class.java).also {
                it.putExtra("Word", word)
            }
            startActivity(intent)
        }else{
            etWordSearch.error = "Please enter a word"
        }

    }
}