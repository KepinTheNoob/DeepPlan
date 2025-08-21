package com.example.deepplan.data

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String,
    val name: String,
    val start: String, // Format: "YYYY-MM-DD"
    val end: String,   // Format: "YYYY-MM-DD"
    val dependencies: String = "", // Comma-separated IDs
    var finished: String = "", // yes or no
)
