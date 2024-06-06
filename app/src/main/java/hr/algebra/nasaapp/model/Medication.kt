package hr.algebra.nasaapp.model

import com.google.gson.annotations.SerializedName

data class Medication (
    @SerializedName("MedicationID") val _id: Int?,
    @SerializedName("UserID") val user: Int?,
    @SerializedName("Name") val name: String?,
    @SerializedName("Dosage") val dosage: Double?,
    @SerializedName("Unit") val unit: String?,
    @SerializedName("Stash") val stash: Int?,
    @SerializedName("Note") val note: String?,
)