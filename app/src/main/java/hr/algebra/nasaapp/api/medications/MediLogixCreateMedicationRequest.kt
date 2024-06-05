package hr.algebra.nasaapp.api.medications

import com.google.gson.annotations.SerializedName

data class MediLogixCreateMedicationRequest(
    val userID: Int,
    val name: String,
    val dosage: Double,
    val unit: String,
    val stash: String,
    val note: String
    )
