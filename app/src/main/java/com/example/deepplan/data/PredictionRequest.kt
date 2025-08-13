package com.example.deepplan.data

import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deepplan.ui.screen.newProject.NewProjectViewModel
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class Predictor {
    fun predictionRequest(
        newProjectViewModel: NewProjectViewModel,
        project_type: String,
        client_type: String,
        contract_type: String,
        is_design_and_build: Int,
        nilai_kontrak_awal_miliar_rp: Double,
        total_jam_kerja_estimasi: Int,
        volume_pekerjaan_tanah_m3: Int,
        volume_beton_m3: Int,
        berat_baja_struktural_ton: Int,
        panjang_instalasi_utama_km: Int,
        jumlah_titik_akhir_instalasi: Int,
        jumlah_item_pekerjaan_utama: Int,
        tingkat_risiko_geoteknik: Int,
        lokasi_provinsi: String,
        lokasi_urban_rural: String,
        musim_pelaksanaan: String,
        indeks_harga_komoditas_saat_mulai: Double,
        jumlah_kompetitor_saat_tender: Int,
        pengalaman_pm_tahun: Int,
        jumlah_sdm_inti: Int,
        persentase_subkontraktor: Int

    ) {
        val client = OkHttpClient()
        val mediaType = "application/json".toMediaType()
        val jsonBody = Gson().toJson(
            mapOf(
                "project_type" to listOf(project_type),
                "client_type" to listOf(client_type),
                "contract_type" to listOf(contract_type),
                "is_design_and_build" to listOf(is_design_and_build),
                "nilai_kontrak_awal_miliar_rp" to listOf(nilai_kontrak_awal_miliar_rp),
                "total_jam_kerja_estimasi" to listOf(total_jam_kerja_estimasi),
                "volume_pekerjaan_tanah_m3" to listOf(volume_pekerjaan_tanah_m3),
                "volume_beton_m3" to listOf(volume_beton_m3),
                "berat_baja_struktural_ton" to listOf(berat_baja_struktural_ton),
                "panjang_instalasi_utama_km" to listOf(panjang_instalasi_utama_km),
                "jumlah_titik_akhir_instalasi" to listOf(jumlah_titik_akhir_instalasi),
                "jumlah_item_pekerjaan_utama" to listOf(jumlah_item_pekerjaan_utama),
                "tingkat_risiko_geoteknik" to listOf(tingkat_risiko_geoteknik),
                "lokasi_provinsi" to listOf(lokasi_provinsi),
                "lokasi_urban_rural" to listOf(lokasi_urban_rural),
                "musim_pelaksanaan" to listOf(musim_pelaksanaan),
                "indeks_harga_komoditas_saat_mulai" to listOf(indeks_harga_komoditas_saat_mulai),
                "jumlah_kompetitor_saat_tender" to listOf(jumlah_kompetitor_saat_tender),
                "pengalaman_pm_tahun" to listOf(pengalaman_pm_tahun),
                "jumlah_sdm_inti" to listOf(jumlah_sdm_inti),
                "persentase_subkontraktor" to listOf(persentase_subkontraktor)
            )
        )

        val requestBody = jsonBody.toRequestBody(mediaType)

        val request = Request.Builder()
            .url("https://deepplan-machinelearning-model.onrender.com/predict")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Predictor", "Request failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val jsonObject = JsonParser.parseString(responseBody).asJsonObject

                    val body = mapOf(
                        "biaya_akhir_riil_miliar_rp" to jsonObject.getAsJsonArray("biaya_akhir_riil_miliar_rp")[0].asDouble,
                        "durasi_akhir_riil_hari" to jsonObject.getAsJsonArray("durasi_akhir_riil_hari")[0].asDouble,
                        "profit_margin_riil_persen" to jsonObject.getAsJsonArray("profit_margin_riil_persen")[0].asDouble,
                        "terjadi_keterlambatan_signifikan" to jsonObject.getAsJsonArray("terjadi_keterlambatan_signifikan")[0].asInt,
                        "terjadi_pembengkakan_biaya_signifikan" to jsonObject.getAsJsonArray("terjadi_pembengkakan_biaya_signifikan")[0].asInt,
                    )

                } else {
                    Log.e("Predictor", "Request failed with code: ${response.code}")
                }
            }

        })

    }
}