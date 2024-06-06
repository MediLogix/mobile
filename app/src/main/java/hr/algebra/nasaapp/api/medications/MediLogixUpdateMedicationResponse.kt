package hr.algebra.nasaapp.api.medications

import com.google.gson.annotations.SerializedName

data class MediLogixUpdateMedicationResponse(
    @SerializedName("MedicationID") val _id: Int?,
    @SerializedName("UserID") val user: Int?,
    @SerializedName("Name") val name: String?,
    @SerializedName("Dosage") val dosage: String?,
    @SerializedName("Unit") val unit: String?,
    @SerializedName("Stash") val stash: String?,
    @SerializedName("Note") val note: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("error") val error: String?,
    @SerializedName("statusCode") val statusCode: Int?,
    )
