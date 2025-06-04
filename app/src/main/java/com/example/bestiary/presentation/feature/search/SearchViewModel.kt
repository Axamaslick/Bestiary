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

    private val _monsters = MutableStateFlow<Resource<List<Monster>>>(Resource.Loading())
    val monsters: StateFlow<Resource<List<Monster>>> = _monsters.asStateFlow()

    private val _filteredMonsters = MutableStateFlow<List<Monster>>(emptyList())
    val filteredMonsters: StateFlow<List<Monster>> = _filteredMonsters.asStateFlow()

    fun loadAllMonsters() {
        viewModelScope.launch {
            _monsters.value = Resource.Loading()
            repository.getAllMonsters()
                .onSuccess { monsters ->
                    _monsters.value = Resource.Success(monsters)
                    _filteredMonsters.value = monsters
                }
                .onFailure {
                    _monsters.value = Resource.Error(it.message ?: "Unknown error")
                }
        }
    }

    fun filterMonsters(query: String) {
        val currentMonsters = (_monsters.value as? Resource.Success)?.data ?: return
        _filteredMonsters.value = if (query.isEmpty()) {
            currentMonsters
        } else {
            currentMonsters.filter { monster ->
                monster.name.contains(query, ignoreCase = true)
            }
        }
    }

    // presentation/feature/search/SearchViewModel.kt
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<Resource<List<Monster>>>(Resource.Success(emptyList()))
    val searchResults: StateFlow<Resource<List<Monster>>> = _searchResults.asStateFlow()

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.length >= 2) {
                _searchResults.value = Resource.Loading()
                try {
                    delay(300)
                    repository.searchMonsters(query)
                        .collect { results ->
                            _searchResults.value = Resource.Success(results)
                        }
                } catch (e: Exception) {
                    _searchResults.value = Resource.Error(e.message ?: "Search failed")
                }
            } else {
                _searchResults.value = Resource.Success(emptyList())
            }
        }
    }
}