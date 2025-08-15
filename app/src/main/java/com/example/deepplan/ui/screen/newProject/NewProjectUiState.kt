package com.example.deepplan.ui.screen.newProject

data class NewProjectUiState (
    // General Information
    val projectName: String = "asdasd",
    val projectType: String = "Jalan Tol",
    val clientType: String = "BUMN",
    val contractType: String = "Lump Sum",
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
    val location: String = "Kalimantan Timur",
    val areaType: String = "Urban",
    val season: String = "Hujan",
    val geotechnicalRiskLevel: Int = 1,
    val commodityPriceIndex: Float = 0f,
    val numberOfTenderCompetitor: Int = 0,

    // Internal Factors
    val projectManagerExperienceYears: Int = 1,
    val coreTeamSize: Int = 1,
    val subcontractorPercentage: Float = 0f,

    // Prediction Results
    val predictionCompleted: Boolean = false,
    val goodPrediction: Boolean = true,
    val biaya_akhir_riil_miliar_rp: Double = 0.0,
    val durasi_akhir_riil_hari: Double = 0.0,
    val profit_margin_riil_persen: Double = 0.0,
    val terjadi_keterlambatan_signifikan: String = "No",
    val terjadi_pembengkakan_biaya_signifikan: String = "No",
    val reason: String = "",
)