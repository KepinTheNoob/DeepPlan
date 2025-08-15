package com.example.deepplan.ui.screen.newProject

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.deepplan.data.Predictor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

    fun doPrediction() {
        val predictor = Predictor()
        predictor.predictionRequest(
            uiState.value.projectType,
            uiState.value.clientType,
            uiState.value.contractType,
            if (uiState.value.isItDesignAndBuild) 1 else 0,
            uiState.value.initialContractValue,
            uiState.value.estimatedTotalManHours,
            uiState.value.earthworkVolume,
            uiState.value.concreteVolume,
            uiState.value.structuralSteelWeight,
            uiState.value.mainInstallationLength,
            uiState.value.numberOfInstallationEndpoint,
            uiState.value.numberOfMainTasks,
            uiState.value.geotechnicalRiskLevel,
            uiState.value.location,
            uiState.value.areaType,
            uiState.value.season,
            uiState.value.commodityPriceIndex,
            uiState.value.numberOfTenderCompetitor,
            uiState.value.projectManagerExperienceYears,
            uiState.value.coreTeamSize,
            uiState.value.subcontractorPercentage,
            this
        )

        Log.d("Loading Prediction", "doPrediction() selesai")
    }

    fun setPrediction(
        jsonBody: Map<String, Any>
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                predictionCompleted = true,
                biaya_akhir_riil_miliar_rp = jsonBody.get("biaya_akhir_riil_miliar_rp").toString().toDouble(),
                durasi_akhir_riil_hari = jsonBody.get("durasi_akhir_riil_hari").toString().toDouble(),
                profit_margin_riil_persen = jsonBody.get("profit_margin_riil_persen").toString().toDouble(),
                terjadi_keterlambatan_signifikan = if (jsonBody.get("terjadi_keterlambatan_signifikan").toString().toInt() == 1) "Yes" else "No",
                terjadi_pembengkakan_biaya_signifikan = if (jsonBody.get("terjadi_pembengkakan_biaya_signifikan").toString().toInt() == 1) "Yes" else "No"
            )
        }
        Log.d("Loading Prediction", "biaya akhir: ${uiState.value.biaya_akhir_riil_miliar_rp.toString()}")
        Log.d("Loading Prediction", "setPrediction() prediction complete: ${uiState.value.predictionCompleted.toString()}")
        Log.d("Loading Prediction", "setPrediction() selesai")
    }


}