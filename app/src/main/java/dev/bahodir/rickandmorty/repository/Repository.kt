package dev.bahodir.rickandmorty.repository

import dev.bahodir.rickandmorty.retrofit.ApiService
import kotlinx.coroutines.flow.flow

class Repository(private val apiService: ApiService) {
    fun getApi(page: Int) = flow { emit(apiService.getPhotos(page)) }
}