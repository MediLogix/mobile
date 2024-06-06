package hr.algebra.nasaapp.api.medications

import com.google.gson.annotations.SerializedName

data class MediLogixGetMedicationResponse(
    @SerializedName("MedicationID") val _id: Int?,
    @SerializedName("UserID") val user: Int?,
    @SerializedName("Name") val name: String?,
    @SerializedName("Dosage") val dosage: String?,
    @SerializedName("Unit") val unit: String?,
    @SerializedName("Stash") val stash: String?,
    @SerializedName("Note") val note: String?,
    )
