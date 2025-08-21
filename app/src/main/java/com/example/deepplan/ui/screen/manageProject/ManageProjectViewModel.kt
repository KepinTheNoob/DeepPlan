package com.example.deepplan.ui.screen.manageProject

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deepplan.data.Project
import com.example.deepplan.data.ProjectOverview
import com.example.deepplan.data.ProjectUiState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ManageProjectViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(
        ManageProjectUiState()
    )
    val uiState: StateFlow<ManageProjectUiState> = _uiState.asStateFlow()

    fun loadProjects(
        projects: List<Project>
    ) {
        val projectsOverview = mutableListOf<ProjectOverview>()

        Log.d("Loading Projects",  "Projects: " + projects)

        for (project in projects) {
            projectsOverview += ProjectOverview(
                id = project.id,
                name = project.projectName,
                progress = project.progress
            )
        }

        _uiState.update { currentState ->
            currentState.copy(
                finishedLoadingProjects = true,
                projects = projectsOverview
            )
        }
    }
}