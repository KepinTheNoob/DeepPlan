package com.example.deepplan.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProjectViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(
        ProjectUiState()
    )
    val uiState: StateFlow<ProjectUiState> = _uiState.asStateFlow()

    fun loadProjects(
        goToDashboardAfterPredict: Boolean = false,
        navigateToDashboard: () -> Unit = {},
    ) {
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

                        val tasksList = mutableListOf<Task>()
                        val rawTasks = document.get("tasks")

                        if (rawTasks is List<*>) {
                            val tasksArray = rawTasks as? List<HashMap<String, Any>>
                            if (tasksArray != null) {
                                for (taskMap in tasksArray) {
                                    val task = Task(
                                        id = taskMap["id"]?.toString() ?: "",
                                        name = taskMap["name"]?.toString() ?: "",
                                        start = taskMap["start"]?.toString() ?: "",
                                        end = taskMap["end"]?.toString() ?: "",
                                        dependencies = taskMap["dependencies"]?.toString() ?: "",
                                        finished = taskMap["finished"]?.toString() ?: "no"
                                    )
                                    tasksList.add(task)
                                }
                            }
                        }

                        Log.d("Loading Projects", "Progess = " + document.data.get("progress").toString())

                        projects += Project(
                            id = document.id,
                            progress = document.data.get("progress").toString().toFloat(),
                            tasks = tasksList,

                            projectName = document.data.get("project_name").toString(),
                            projectType = document.data.get("project_type").toString(),
                            clientType = document.data.get("client_type").toString(),
                            contractType = document.data.get("contract_type").toString(),
                            isItDesignAndBuild = document.data.get("is_design_and_build").toString().toBoolean(),
                            numberOfMainTasks = document.data.get("jumlah_item_pekerjaan_utama").toString().toInt(),

                            initialContractValue = document.data.get("nilai_kontrak_awal_miliar_rp").toString().toFloat(),
                            estimatedTotalManHours = document.data.get("total_jam_kerja_estimasi").toString().toFloat(),
                            earthworkVolume = document.data.get("volume_pekerjaan_tanah_m3").toString().toFloat(),
                            concreteVolume = document.data.get("volume_beton_m3").toString().toFloat(),
                            structuralSteelWeight = document.data.get("berat_baja_struktural_ton").toString().toFloat(),
                            mainInstallationLength = document.data.get("panjang_instalasi_utama_km").toString().toFloat(),
                            numberOfInstallationEndpoint = document.data.get("jumlah_titik_akhir_instalasi").toString().toInt(),

                            location = document.data.get("lokasi_provinsi").toString(),
                            areaType = document.data.get("lokasi_urban_rural").toString(),
                            season = document.data.get("musim_pelaksanaan").toString(),
                            geotechnicalRiskLevel = document.data.get("tingkat_risiko_geoteknik").toString().toInt(),
                            commodityPriceIndex = document.data.get("indeks_harga_komoditas_saat_mulai").toString().toFloat(),
                            numberOfTenderCompetitor = document.data.get("jumlah_kompetitor_saat_tender").toString().toInt(),

                            projectManagerExperienceYears = document.data.get("pengalaman_pm_tahun").toString().toInt(),
                            coreTeamSize = document.data.get("jumlah_sdm_inti").toString().toInt(),
                            subcontractorPercentage = document.data.get("persentase_subkontraktor").toString().toFloat(),

                            predictionCompleted = document.data.get("predictionCompleted").toString().toBoolean(),
                            goodPrediction = document.data.get("goodPrediction").toString().toBoolean(),
                            biaya_akhir_riil_miliar_rp = document.data.get("biaya_akhir_riil_miliar_rp").toString().toDouble(),
                            durasi_akhir_riil_hari = document.data.get("durasi_akhir_riil_hari").toString().toDouble(),
                            profit_margin_riil_persen = document.data.get("profit_margin_riil_persen").toString().toDouble(),
                            terjadi_keterlambatan_signifikan = document.data.get("terjadi_keterlambatan_signifikan").toString(),
                            terjadi_pembengkakan_biaya_signifikan = document.data.get("terjadi_pembengkakan_biaya_signifikan").toString()
                        )
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Loading Projects", "Error getting documents: ", exception)
                }
                .await()

            _uiState.update { currentState ->
                currentState.copy(
                    needToLoadProjects = false,
                    projects = projects
                )
            }
            Log.d("Loading Projects", "Projects: ${_uiState.value.projects}")

            if (goToDashboardAfterPredict) {
                navigateToDashboard()
            }
        }
    }

    fun updateProjectProgress(
        projectId: String,
        progress: Float
    ) {
        val currentProjects = _uiState.value.projects

        val updatedProjects = currentProjects.map {
            if (it.id == projectId) {
                it.copy(progress = progress)
            } else {
                it
            }
        }

        _uiState.update { currentState ->
            currentState.copy(
                projects = updatedProjects
            )
        }

        Log.d("Update Project", "Projects: ${_uiState.value.projects}")
    }
}