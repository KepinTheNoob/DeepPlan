package com.example.deepplan.ui.screen.newProject

data class NewProjectUiState (
    // General Information
    val projectName: String = "",
    val projectType: String = "",
    val clientType: String = "",
    val contractType: String = "",
    val isItDesignAndBuild: Boolean = true,
    val numberOfMainTasks: Int = 0,

    // Technical Scope
    val initialContractValue: Float = 0f,
    val estimatedTotalManHours: Float = 0f,
    val earthworkVolume: Float = 0f,
    val concreteVolume: Float = 0f,
    val structuralSteelWeight: Float = 0f,
    val mainInstallationLength: Float = 0f,
    val numberOfInstallationEndpoint: Int = 0,

    // External Context
    val location: String = "",
    val areaType: String = "",
    val season: String = "",
    val geotechnicalRiskLevel: Int = 1,
    val commodityPriceIndex: Float = 0f,
    val numberOfTenderCompetitor: Int = 0,

    // Internal Factors
    val projectManagerExperienceYears: Int = 1,
    val coreTeamSize: Int = 1,
    val subcontractorPercentage: Float = 0f,
)