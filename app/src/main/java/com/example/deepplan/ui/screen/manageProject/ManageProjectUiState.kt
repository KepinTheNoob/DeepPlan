package com.example.deepplan.ui.screen.manageProject

import com.example.deepplan.data.Project

data class ManageProjectUiState(
    val finishedLoadingProjects: Boolean = false,
    val projects: List<Project> = emptyList(),
)
