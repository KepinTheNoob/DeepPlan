package com.example.deepplan.ui.screen.projectDashboardScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deepplan.BuildConfig
import com.example.deepplan.data.Project
import com.example.deepplan.data.Task
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
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.Boolean

class ProjectDashboardViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(
        ProjectDashboardUiState()
    )
    val uiState: StateFlow<ProjectDashboardUiState> = _uiState.asStateFlow()

    fun initDashboard(
        projectId: String,
        projects: List<Project>
    ) {
        val db = Firebase.firestore
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        viewModelScope.launch {
            db.collection("users")
                .document(currentUserId.toString())
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                greetings = "How's it going, ${document.data?.get("username").toString()}"
                            )
                        }
                    } else {
                        Log.w("Loading Dashboard", "There is no such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Loading Dashboard", "Error getting documents: ", exception)
                }
                .await()

            setDashboardInformation(
                projectId,
                projects
            )

            _uiState.update { currentState ->
                currentState.copy(
                    isFetchingDataCompleted = true
                )
            }
        }

    }

    fun restartState() {
        _uiState.update { currentState ->
            currentState.copy(
                isFetchingDataCompleted = false,
                isGeneratePlanningCompleted = false,
                isButtonGeneratePlanningClicked = false,
                isButtonRegeneratePlanningClicked = false,
            )
        }
    }

    fun setProjectId(
        projectId: String
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                projectId = projectId
            )
        }
    }

    fun setGreetings() {
        val db = Firebase.firestore
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        viewModelScope.launch {
            db.collection("users")
                .document(currentUserId.toString())
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                greetings = "How's it going, ${document.data?.get("username").toString()}"
                            )
                        }
                    } else {
                        Log.w("Loading Dashboard", "There is no such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Loading Dashboard", "Error getting documents: ", exception)
                }
                .await()
        }
    }

    fun setDashboardInformation(
        projectId: String,
        projects: List<Project>,
    ) {

        val project = projects.find { it.id == projectId }

        if (project != null) {
            _uiState.update { currentState ->
                currentState.copy(
                    tasks = project.tasks,
                    progress = project.progress,
                    projectName = project.projectName,
                    projectType = project.projectType,
                    clientType = project.clientType,
                    contractType = project.contractType,
                    isItDesignAndBuild = project.isItDesignAndBuild,
                    numberOfMainTasks = project.numberOfMainTasks,
                    initialContractValue = project.initialContractValue,
                    estimatedTotalManHours = project.estimatedTotalManHours,
                    earthworkVolume = project.earthworkVolume,
                    concreteVolume = project.concreteVolume,
                    structuralSteelWeight = project.structuralSteelWeight,
                    mainInstallationLength = project.mainInstallationLength,
                    numberOfInstallationEndpoint = project.numberOfInstallationEndpoint,
                    location = project.location,
                    areaType = project.areaType,
                    season = project.season,
                    geotechnicalRiskLevel = project.geotechnicalRiskLevel,
                    commodityPriceIndex = project.commodityPriceIndex,
                    numberOfTenderCompetitor = project.numberOfTenderCompetitor,
                    projectManagerExperienceYears = project.projectManagerExperienceYears,
                    coreTeamSize = project.coreTeamSize,
                    subcontractorPercentage = project.subcontractorPercentage,
                    predictionCompleted = project.predictionCompleted,
                    goodPrediction = project.goodPrediction,
                    biaya_akhir_riil_miliar_rp = project.biaya_akhir_riil_miliar_rp,
                    durasi_akhir_riil_hari = project.durasi_akhir_riil_hari,
                    profit_margin_riil_persen = project.profit_margin_riil_persen,
                    terjadi_keterlambatan_signifikan = project.terjadi_keterlambatan_signifikan,
                    terjadi_pembengkakan_biaya_signifikan = project.terjadi_pembengkakan_biaya_signifikan
                )
            }
        }
    }

    fun extractJson(rawText: String): String {
        val jsonObjectRegex = Regex("""\{.*\}""")

        val allTaskObjects = jsonObjectRegex.findAll(rawText)
            .map { it.value }
            .joinToString(separator = ",")

        return "[$allTaskObjects]"
    }

    fun parseProjectPlanJson(jsonString: String): List<Task> {
        val jsonText = extractJson(jsonString)
        Log.d("ProjectDashboardScreen", "Json Text extracted: "+ jsonText)

        val json = Json { ignoreUnknownKeys = true }

        return try {
            json.decodeFromString<List<Task>>(jsonText)
        } catch (e: Exception) {
            Log.d("ProjectDashboardScreen", "Error parsing JSON: ${e.message}")
            emptyList()
        }
    }

    fun generateTasks(
        userPrompt: String,
        regenerate: Boolean = false,
    ) {
        if (regenerate) {
            Log.d("Regenerate", "Regenerate being generated")

            _uiState.update { currentState ->
                currentState.copy(
                    isButtonGeneratePlanningClicked = true,
                    isGeneratePlanningCompleted = false,
                    isButtonRegeneratePlanningClicked = true,
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isButtonGeneratePlanningClicked = true,
                    isButtonRegeneratePlanningClicked = false,
                )
            }
        }

        val prompt = """
            ROLE:
            You are a master project planner and civil engineer with extensive experience in large-scale infrastructure projects, specifically "Design and Build" toll roads for government-owned enterprises ("BUMN") in tropical climates like Indonesia.
            TASK:
            Analyze the detailed project data provided below. Based on this data, create a comprehensive and logical project plan suitable for a Gantt chart. The entire output must be a single, raw JSON array of task objects.
            INPUT PROJECT DATA:
            
            {
              "general_information": {
                "project_name": "${_uiState.value.projectName}",
                "project_type": "${_uiState.value.projectType}",
                "client_type": "${_uiState.value.clientType}",
                "contract_type": "${_uiState.value.contractType}",
                "is_design_and_build": ${_uiState.value.isItDesignAndBuild}
              },
              "technical_scope": {
                "earthwork_volume_m3": ${_uiState.value.earthworkVolume},
                "concrete_volume_m3": ${_uiState.value.concreteVolume},
                "structural_steel_weight_ton": ${_uiState.value.structuralSteelWeight},
                "main_installation_length_km": ${_uiState.value.mainInstallationLength}
              },
              "external_context": {
                "location": "${_uiState.value.location}",
                "area_type": "${_uiState.value.areaType}",
                "season": "${_uiState.value.season}",
                "geotechnical_risk_level": ${_uiState.value.geotechnicalRiskLevel}
              },
              "internal_factors": {
                "project_manager_experience_years": ${_uiState.value.projectManagerExperienceYears},
                "core_team_size": ${_uiState.value.coreTeamSize}
              },
              "prediction_results": {
                "durasi_akhir_riil_hari": ${_uiState.value.durasi_akhir_riil_hari}
              }
            }
            
            CURRENT DATE:
            ${SimpleDateFormat("yyyy-MM-dd").format(Date())}
            
            USER'S CUSTOM REQUEST (Prioritize this):
            ${userPrompt}
            
            PLAN STRUCTURE:
            The project plan must be logically structured for a "Design and Build" toll road. Organize tasks under the following major phases, breaking each down into specific sub-tasks:
            
            Detailed Engineering Design (DED) & Permitting: (Geotechnical Surveys, Structural Design, Alignment Design, Environmental Permits, etc.)
            Site Preparation & Mobilization: (Site Clearing, Access Road Construction, Temporary Facilities, etc.)
            Earthworks: (Cut and Fill, Soil Compaction, Subgrade Preparation, etc.)
            Drainage & Substructure Works: (Culvert Installation, Bridge Foundations, Retaining Walls, etc.)
            Superstructure Works: (Bridge Girders, Overpass Construction, Pavement Layers - Subbase, Base, Asphalt, etc.)
            MEP & Tolling Infrastructure: (Lighting Installation, Fiber Optics, Toll Plaza Construction, Signage, etc.)
            Finishing & Landscaping: (Road Markings, Guardrails, Slope Protection, Planting, etc.)
            Commissioning & Handover: (System Testing, Final Inspections, As-Built Drawings, Project Closure, etc.)
            OUTPUT FORMAT:
            Generate the output as a single, raw JSON array of task objects. The entire response must start with [ and end with ]. Do NOT add any introductory text, explanations, or markdown formatting. Each object in the array must be a valid JSON object with the following keys and string values:
            
            "id"
            "name"
            "start"
            "end"
            "dependencies"
            "finished"
            Example of a single task object:{"id": "task1", "name": "Detailed Geotechnical Soil Investigation", "start": "2025-08-20", "end": "2025-09-30", "dependencies": "", "finished": "no"}
            CRITICAL INSTRUCTIONS:
            
            Primary Constraint: The entire project schedule must be planned to fit within the durasi_akhir_riil_hari (730 days). The start date for the first task should be today's date.
            Factor Integration: Your plan's task durations and sequencing must logically reflect the input data. For example:
            The "season": "Hujan" (Rainy) means you must allocate more time or add buffers for earthworks and foundation tasks.
            The "is_design_and_build": true means design tasks should run in parallel with early site work.
            The large earthwork_volume_m3 and concrete_volume_m3 require long durations for those phases.
            JSON Only: The response must be a valid JSON array and nothing else.
            Logical Dependencies: Ensure the task dependencies are correct and reflect a real-world construction sequence.
            "finished" field: all of the value of "finished" must be "no"
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
                    val projectTasks: List<Task> = parseProjectPlanJson(outputText)
                    _uiState.update { currentState ->
                        currentState.copy(
                            isGeneratePlanningCompleted = true,
                            progress = 0f,
                            isButtonRegeneratePlanningClicked = false,
                            tasks = projectTasks
                        )
                    }
                    saveTasksToFirestore()

                    Log.d("ProjectDashboardScreen", projectTasks.toString())
                } else {
                    Log.d("ProjectDashboardScreen", "Error: The response was empty.")
                    _uiState.update { currentState ->
                        currentState.copy(
                            isButtonRegeneratePlanningClicked = false,
                            isGeneratePlanningCompleted = true,
                        )
                    }
                }

            } catch (e: ServerException) {
                Log.d("ProjectDashboardScreen", "API Error: ${e.localizedMessage}")
                _uiState.update { currentState ->
                    currentState.copy(
                        predictionCompleted = true,
                    )
                }
            }
        }
    }

    fun setFinishedTask(
        index: Int,
        finished: Boolean
    ) {
        val currentTasks = _uiState.value.tasks

        val newTasks = currentTasks.toMutableList().apply {
            val taskToUpdate = this[index]

            this[index] = taskToUpdate.copy(finished = if (finished) "yes" else "no")
        }

        var progress = 0f
        for (task in newTasks) {
            if (task.finished == "yes") progress += 1
        }

        _uiState.update { currentState ->
            currentState.copy(
                tasks = newTasks,
                progress = progress / newTasks.size
            )
        }
        Log.d("Update Project", "Tasks" + _uiState.value.progress)
        saveTasksToFirestore()
    }

    fun getProgress(): Float {
        return _uiState.value.progress
    }

    fun saveTasksToFirestore() {
        val newValue = hashMapOf(
            "tasks" to _uiState.value.tasks,
            "progress" to _uiState.value.progress
        )

        val db = Firebase.firestore
        db.collection("projects").document(_uiState.value.projectId)
            .update(newValue)
            .addOnSuccessListener {
                Log.d("ProjectDashboardScreen", "Successfully save tasks.")
            }
            .addOnFailureListener { e ->
                Log.d("ProjectDashboardScreen", "Error: " + e.toString())
            }
    }

}