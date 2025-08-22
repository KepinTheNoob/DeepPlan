package com.example.deepplan.ui.screen.manageProject

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deepplan.data.Project
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

    fun deleteProject(project: Project) {
        val updatedProjects = _uiState.value.projects.toMutableList()
        updatedProjects.remove(project)

        _uiState.value = _uiState.value.copy(projects = updatedProjects)
    }
    fun loadProjects() {
        val db = Firebase.firestore
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        var projects = emptyList<Project>()

        viewModelScope.launch {
            db.collection("projects")
                .whereEqualTo("user_id", currentUserId)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d("Loading Projects", "${document.id} => ${document.data}")
                        projects += Project(
                            id = document.id,
                            projectName = document.data.get("project_name").toString(),
                            progress = document.data.get("progress").toString().toFloat()
                        )
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Loading Projects", "Error getting documents: ", exception)
                }
                .await()

            _uiState.update { currentState ->
                currentState.copy(
                    finishedLoadingProjects = true,
                    projects = projects
                )
            }
            Log.d("Loading Projects", "Projects: ${_uiState.value.projects}")
        }

    }
}