package com.example.deepplan.ui.screen.manageProject

import com.example.deepplan.data.ProjectOverview

data class ManageProjectUiState(
    val finishedLoadingProjects: Boolean = false,
    val projects: List<ProjectOverview> = emptyList(),
)
