package com.example.deepplan.data

data class ProjectUiState(
    val needToLoadProjects: Boolean = true,
    val projects: List<Project> = emptyList(),
)
