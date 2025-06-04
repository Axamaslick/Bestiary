// presentation/feature/search/SearchViewModel.kt
package com.example.bestiary.presentation.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestiary.domain.model.Monster
import com.example.bestiary.domain.repository.MonsterRepository
import com.example.bestiary.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MonsterRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchState = MutableStateFlow<Resource<List<Monster>>>(Resource.Loading())
    val searchState: StateFlow<Resource<List<Monster>>> = _searchState.asStateFlow()

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        viewModelScope.launch {
            _searchState.value = Resource.Loading()
            try {
                val result = repository.getAllMonsters()
                if (result.isSuccess) {
                    _searchState.value = Resource.Success(result.getOrThrow())
                } else {
                    _searchState.value = Resource.Error(result.exceptionOrNull()?.message ?: "Error loading data")
                }
            } catch (e: Exception) {
                _searchState.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.length >= 2) {
                _searchState.value = Resource.Loading()
                try {
                    repository.searchMonsters(query)
                        .collect { results ->
                            _searchState.value = Resource.Success(results)
                        }
                } catch (e: Exception) {
                    _searchState.value = Resource.Error(e.message ?: "Search failed")
                }
            } else {
                // При пустом запросе или коротком (<2 символов) показываем все монстры
                loadInitialData()
            }
        }
    }
}