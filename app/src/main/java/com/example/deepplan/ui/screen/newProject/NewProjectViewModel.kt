package com.example.deepplan.ui.screen.newProject

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewProjectViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(
        NewProjectUiState()
    )
    val uiState: StateFlow<NewProjectUiState> = _uiState.asStateFlow()
}