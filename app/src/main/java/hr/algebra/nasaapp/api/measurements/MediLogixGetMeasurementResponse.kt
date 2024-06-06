package hr.algebra.nasaapp.api.measurements

import com.google.gson.annotations.SerializedName

data class MediLogixGetMeasurementResponse(
    @SerializedName("MedicationID") val _id: Int?,
    @SerializedName("UserID") val user: Int?,
    @SerializedName("Name") val name: String?,
    @SerializedName("Dosage") val dosage: String?,
    @SerializedName("Unit") val unit: String?,
    @SerializedName("Stash") val stash: String?,
    @SerializedName("Note") val note: String?,
    )
