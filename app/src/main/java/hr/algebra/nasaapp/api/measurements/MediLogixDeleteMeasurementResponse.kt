package hr.algebra.nasaapp.api.measurements

import com.google.gson.annotations.SerializedName

data class MediLogixDeleteMeasurementResponse(
    @SerializedName("MeasurementID") val _id: Int?,
    @SerializedName("UserID") val user: Int?,
    @SerializedName("Name") val name: String?,
    @SerializedName("Note") val dosage: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("error") val error: String?,
    @SerializedName("statusCode") val statusCode: Int?,
    )
