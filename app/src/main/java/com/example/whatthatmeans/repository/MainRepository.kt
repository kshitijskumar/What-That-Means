package com.example.whatthatmeans.repository

import android.util.Log
import com.example.whatthatmeans.model.BaseModelClass
import com.example.whatthatmeans.retrofit.DictionaryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val dictionaryService: DictionaryService
) {

    private var meaning: BaseModelClass? = null

    suspend fun getMeaningFromApi(word: String) {
        withContext(Dispatchers.IO){
            meaning =  try {
                val response = dictionaryService.getMeaning(word)
                if (response.isSuccessful) {
                    Log.d("Response", "Word meaning is: ${response.body()?.get(0)?.meanings?.get(0)?.definitions?.get(0)?.definition}")
                    response.body()?.get(0)
                } else {
                    Log.d("Response", "Else block")
                    null
                }
            }catch (e: Exception){
                Log.d("Catch", e.message.toString())
                null
            }
        }
    }

    fun returnMeaning() = meaning
}