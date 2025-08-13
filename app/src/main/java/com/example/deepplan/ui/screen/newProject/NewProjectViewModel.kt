package com.example.deepplan.ui.screen.newProject

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewProjectViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(
        NewProjectUiState()
    )
    val uiState: StateFlow<NewProjectUiState> = _uiState.asStateFlow()

    fun setGeneralInformationValues(
        projectName: String,
        projectType: String,
        clientType: String,
        contractType: String,
        isItDesignAndBuild: Boolean,
        numberOfMainTasks: Int
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                projectName = projectName,
                projectType = projectType,
                clientType = clientType,
                contractType = contractType,
                isItDesignAndBuild = isItDesignAndBuild,
                numberOfMainTasks = numberOfMainTasks
            )
        }
    }

    fun setTechnicalScopeValues(
        initialContractValue: Float,
        estimatedTotalManHours: Float,
        earthworkVolume: Float,
        concreteVolume: Float,
        structuralSteelWeight: Float,
        mainInstallationLength: Float,
        numberOfInstallationEndpoint: Int
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                initialContractValue = initialContractValue,
                estimatedTotalManHours = estimatedTotalManHours,
                earthworkVolume = earthworkVolume,
                concreteVolume = concreteVolume,
                structuralSteelWeight = structuralSteelWeight,
                mainInstallationLength = mainInstallationLength,
                numberOfInstallationEndpoint = numberOfInstallationEndpoint
            )
        }
    }

    fun setExternalContextValues(
        location: String,
        areaType: String,
        season: String,
        geotechnicalRiskLevel: Int,
        commodityPriceIndex: Float,
        numberOfTenderCompetitor: Int
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                location = location,
                areaType = areaType,
                season = season,
                geotechnicalRiskLevel = geotechnicalRiskLevel,
                commodityPriceIndex = commodityPriceIndex,
                numberOfTenderCompetitor = numberOfTenderCompetitor
            )
        }
    }

    fun setInternalContextValues(
        projectManagerExperienceYears: Int,
        coreTeamSize: Int,
        subcontractorPercentage: Float
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                projectManagerExperienceYears = projectManagerExperienceYears,
                coreTeamSize = coreTeamSize,
                subcontractorPercentage = subcontractorPercentage
            )
        }
    }

}