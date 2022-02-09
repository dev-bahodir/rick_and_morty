package dev.bahodir.rickandmorty.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.bahodir.rickandmorty.model.Model
import dev.bahodir.rickandmorty.model.Result
import dev.bahodir.rickandmorty.repository.Repository
import dev.bahodir.rickandmorty.retrofit.ApiClient
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class ApiSource : PagingSource<Int, Result>() {
    private val repository = Repository(ApiClient.apiService)

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        try {
            val currentPage = params.key ?: 1
            var loadResult: LoadResult.Page<Int, Result>? = null

            if (params.key ?: 1 >= 1) {
                repository.getApi(currentPage)
                    .catch {
                        loadResult = LoadResult.Page(
                            emptyList(),
                            currentPage - 1,
                            currentPage + 1
                        )
                    }
                    .collect {
                        loadResult = LoadResult.Page(
                            it.results,
                            currentPage - 1,
                            currentPage + 1
                        )
                    }
            } else {
                loadResult = LoadResult.Page(
                    emptyList(),
                    null,
                    currentPage + 1
                )
            }
            return loadResult!!
        } catch (e: Exception) {
            return LoadResult.Error(e.fillInStackTrace())
        }
    }
}