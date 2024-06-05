package hr.algebra.nasaapp.api.medications

import com.google.gson.annotations.SerializedName

data class MediLogixCreateMedicationResponse(
    @SerializedName("medicationID") val _id: Int?,
    @SerializedName("userID") val user: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("dosage") val dosage: Double?,
    @SerializedName("unit") val unit: String?,
    @SerializedName("stash") val stash: String?,
    @SerializedName("note") val note: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("error") val error: String?,
    @SerializedName("statusCode") val statusCode: Int?,
    )
