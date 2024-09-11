package com.example.appcatphotos.API


import com.jamiltondamasceno.aulatesteempregoandroid.api.model.Resultado
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface imgurAPI {
    @GET("gallery/search/")
    suspend fun  pesquisarImagensGaleria(
        @Query("q") q: String,
    ): Response<Resultado>
}