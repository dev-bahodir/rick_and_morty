package dev.bahodir.rickandmorty.retrofit

import dev.bahodir.rickandmorty.model.Model
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/character?")
    suspend fun getPhotos(
        @Query("page") page: Int
    ): Model
}