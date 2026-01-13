package com.example.myicecream.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myicecream.data.database.UserEntity
import com.example.myicecream.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _results = MutableStateFlow<List<UserEntity>>(emptyList())
    val result = _results.asStateFlow()

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        search()
    }

    private fun search() {
        viewModelScope.launch {
            _results.value = userRepository.searchUsersByNickname(_query.value)
        }
    }
}