package com.example.deepplan.ui.screen.newProject

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deepplan.BuildConfig
import com.example.deepplan.data.Predictor
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ServerException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
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

    fun setPredictionCompleted(
        predictionCompleted: Boolean
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                predictionCompleted = predictionCompleted
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
                biaya_akhir_riil_miliar_rp = jsonBody.get("biaya_akhir_riil_miliar_rp").toString().toDouble(),
                durasi_akhir_riil_hari = jsonBody.get("durasi_akhir_riil_hari").toString().toDouble(),
                profit_margin_riil_persen = jsonBody.get("profit_margin_riil_persen").toString().toDouble(),
                terjadi_keterlambatan_signifikan = if (jsonBody.get("terjadi_keterlambatan_signifikan").toString().toInt() == 1) "Yes" else "No",
                terjadi_pembengkakan_biaya_signifikan = if (jsonBody.get("terjadi_pembengkakan_biaya_signifikan").toString().toInt() == 1) "Yes" else "No"
            )
        }
        _uiState.update { currentState ->
            currentState.copy(
                goodPrediction = (
                        uiState.value.terjadi_keterlambatan_signifikan == "No" &&
                                uiState.value.terjadi_pembengkakan_biaya_signifikan == "No" &&
                                uiState.value.profit_margin_riil_persen >= 5
                        )
            )
        }
        Log.d("Loading Prediction", "biaya akhir: ${uiState.value.biaya_akhir_riil_miliar_rp.toString()}")
        Log.d("Loading Prediction", "setPrediction() prediction complete: ${uiState.value.predictionCompleted.toString()}")

        savePredictionResults()

        generatePredictionReason()
    }

    fun generatePredictionReason() {
        val prompt = """
        You are an expert construction project risk analyst. I will provide the features of a project and the outcomes predicted by our estimation model. Your task is to provide a concise, single-paragraph analysis.
    
        ## Project Features:
        * **Project Type:** ${uiState.value.projectType}
        * **Client Type:** ${uiState.value.clientType}
        * **Contract Type:** ${uiState.value.contractType}
        * **Design & Build:** ${if (uiState.value.isItDesignAndBuild) "Yes" else "No"}
        * **Location:** ${uiState.value.location} (${uiState.value.areaType})
        * **Season:** ${uiState.value.season}
        * **Geotechnical Risk:** Level ${uiState.value.geotechnicalRiskLevel}/5
        * **PM Experience:** ${uiState.value.projectManagerExperienceYears} years
        * **Core Team Size:** ${uiState.value.coreTeamSize} people
        * **Subcontractor Percentage:** ${uiState.value.subcontractorPercentage}%
    
        ## Predicted Outcomes:
        * **Final Cost:** ${uiState.value.biaya_akhir_riil_miliar_rp} billion IDR
        * **Final Duration:** ${uiState.value.durasi_akhir_riil_hari} days
        * **Final Profit Margin:** ${uiState.value.profit_margin_riil_persen}%
        * **Significant Cost Overrun:** ${uiState.value.terjadi_pembengkakan_biaya_signifikan}
        * **Significant Delay:** ${uiState.value.terjadi_keterlambatan_signifikan}
    
        ## Your Task:
        Analyze the data above.
        * **If the predicted outcome is considered good (goodPrediction = true):** Briefly state that the predicted outcome is logical based on the project's features.
        * **If the predicted outcome is considered bad (goodPrediction = false):** Explain the likely reasons for the negative outcome by linking specific project features to the predicted results, and suggest concrete improvements to the project plan.
        
        (Note: The current project's prediction status is **goodPrediction = ${uiState.value.goodPrediction}**)
    
        Your entire response must be a single paragraph. Also, do not absolutely comment on the prediction model and other element outside of the data provided
        """.trimIndent()

        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMINI_API_KEY
        )

        viewModelScope.launch {
            try {
                val response = generativeModel.generateContent(prompt)

                val outputText: String? = response.text

                if (outputText != null) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            predictionCompleted = true,
                            reason = outputText
                        )
                    }
                    Log.d("Loading Prediction", "Success.")
                } else {
                    Log.d("Loading Prediction", "Error: The response was empty.")
                    _uiState.update { currentState ->
                        currentState.copy(
                            predictionCompleted = true,
                            reason = "Sorry, we couldn't process the summary for the prediction at the moment."
                        )
                    }
                }

            } catch (e: ServerException) {
                Log.d("Loading Prediction", "API Error: ${e.localizedMessage}")
                _uiState.update { currentState ->
                    currentState.copy(
                        predictionCompleted = true,
                        reason = "Sorry, we couldn't process the summary for the prediction at the moment."
                    )
                }
            }
        }
    }

    fun savePredictionResults() {
        val db = Firebase.firestore
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        val predictionResults = hashMapOf(
            "user_id" to currentUserId,
            "progress" to 0.0,
            "project_name" to uiState.value.projectName,
            "project_type" to uiState.value.projectType,
            "client_type" to uiState.value.clientType,
            "contract_type" to uiState.value.contractType,
            "is_design_and_build" to uiState.value.isItDesignAndBuild,
            "nilai_kontrak_awal_miliar_rp" to uiState.value.initialContractValue,
            "total_jam_kerja_estimasi" to uiState.value.estimatedTotalManHours,
            "volume_pekerjaan_tanah_m3" to uiState.value.earthworkVolume,
            "volume_beton_m3" to uiState.value.concreteVolume,
            "berat_baja_struktural_ton" to uiState.value.structuralSteelWeight,
            "panjang_instalasi_utama_km" to uiState.value.mainInstallationLength,
            "jumlah_titik_akhir_instalasi" to uiState.value.numberOfInstallationEndpoint,
            "jumlah_item_pekerjaan_utama" to uiState.value.numberOfMainTasks,
            "tingkat_risiko_geoteknik" to uiState.value.geotechnicalRiskLevel,
            "lokasi_provinsi" to uiState.value.location,
            "lokasi_urban_rural" to uiState.value.areaType,
            "musim_pelaksanaan" to uiState.value.season,
            "indeks_harga_komoditas_saat_mulai" to uiState.value.commodityPriceIndex,
            "jumlah_kompetitor_saat_tender" to uiState.value.numberOfTenderCompetitor,
            "pengalaman_pm_tahun" to uiState.value.projectManagerExperienceYears,
            "jumlah_sdm_inti" to uiState.value.coreTeamSize,
            "persentase_subkontraktor" to uiState.value.subcontractorPercentage,
            "biaya_akhir_riil_miliar_rp" to uiState.value.biaya_akhir_riil_miliar_rp,
            "durasi_akhir_riil_hari" to uiState.value.durasi_akhir_riil_hari,
            "profit_margin_riil_persen" to uiState.value.profit_margin_riil_persen,
            "terjadi_keterlambatan_signifikan" to uiState.value.terjadi_keterlambatan_signifikan,
            "terjadi_pembengkakan_biaya_signifikan" to uiState.value.terjadi_pembengkakan_biaya_signifikan,
        )

        db.collection("projects")
            .add(predictionResults)
            .addOnSuccessListener { documentReference ->
                Log.d("Loading Prediction", "DocumentSnapshot added with ID: ${documentReference.id}")
                _uiState.update { currentState ->
                    currentState.copy(
                        savedProjectId = documentReference.id
                    )
                }
            }
            .addOnFailureListener { e ->
                Log.w("Loading Prediction", "Error adding document: ${e}")
            }
    }
}