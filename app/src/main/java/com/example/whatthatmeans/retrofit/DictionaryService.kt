package com.example.whatthatmeans.retrofit

import com.example.whatthatmeans.model.BaseModelClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryService {

    @GET("/api/v2/entries/en/{word}")
    suspend fun getMeaning(@Path("word") word: String): Response<List<BaseModelClass>>
}