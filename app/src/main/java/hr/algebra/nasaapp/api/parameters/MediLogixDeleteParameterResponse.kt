package hr.algebra.nasaapp.api.parameters

import com.google.gson.annotations.SerializedName

data class MediLogixDeleteParameterResponse(
    @SerializedName("ParameterID") val _id: Int?,
    @SerializedName("MeasurementID") val measurement: Int?, @SerializedName("Name") val name: String?,
    @SerializedName("Note") val dosage: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("error") val error: String?,
    @SerializedName("statusCode") val statusCode: Int?,
    )
