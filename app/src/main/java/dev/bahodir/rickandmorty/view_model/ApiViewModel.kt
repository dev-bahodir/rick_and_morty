package dev.bahodir.rickandmorty.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dev.bahodir.rickandmorty.source.ApiSource

class ApiViewModel : ViewModel() {
    val flow = Pager(PagingConfig(10)) {
        ApiSource()
    }.flow.cachedIn(viewModelScope)
}