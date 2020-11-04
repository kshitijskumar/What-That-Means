package com.example.whatthatmeans.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.whatthatmeans.model.BaseModelClass
import com.example.whatthatmeans.repository.MainRepository
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel @ViewModelInject constructor(
    application: Application,
    private val repository: MainRepository) : AndroidViewModel(application) {

    companion object{
        val textExtractedLiveData = MutableLiveData<List<String>>(listOf())
    }
    val wordMeaning = MutableLiveData<BaseModelClass?>()

    fun getMeaningFromRepository(word: String) = viewModelScope.launch {
        repository.getMeaningFromApi(word)
        val meaning = repository.returnMeaning()
        Log.d("ViewModelResponse", "Word meaning is: ${meaning?.meanings?.get(0)?.definitions?.get(0)?.definition}")
        wordMeaning.postValue(meaning)
    }

    fun createInputImageUsingUri(image: Uri?){
        val inputImage: InputImage
        try {
            image?.let {
                inputImage = InputImage.fromFilePath(getApplication(), image)
                imageProcessing(inputImage)
            }
        }catch (e: Exception){
            Log.d("Order", "Get image input image: ${e.message}")
        }
    }

    fun createInputImageUsingBitmap(image: Bitmap){
        val inputImage: InputImage
        try {
            inputImage = InputImage.fromBitmap(image, 0)
            imageProcessing(inputImage)
        }catch (e: Exception){
            Log.d("ViewModel", "Capture image input image ${e.message}")
        }
    }

    private fun imageProcessing(inputImage: InputImage){
        val recognizer = TextRecognition.getClient()
        recognizer.process(inputImage)
            .addOnSuccessListener {
                textExtracted(it)
            }
            .addOnFailureListener {
                Log.d("Order", "ImageProcessing: ${it.message}")
            }
    }

    private fun textExtracted(text: Text){
        val listOfWordsExtracted = mutableListOf<String>()
        val resultText = text.text
        Log.d("ViewModel", "Text Extracted: $resultText")
        for (blocks in text.textBlocks){
            for (lines in blocks.lines){
                for (elements in lines.elements){
                    val words = elements.text
                    Log.d("ViewModel", "Words Extracted: $words")
                    listOfWordsExtracted.add(words)
                    Log.d("ViewModel", listOfWordsExtracted.last())
                }
            }
        }
        Log.d("Order", "List Size: ${listOfWordsExtracted.size}")
        textExtractedLiveData.value = listOfWordsExtracted.distinct()
    }
}