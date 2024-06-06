package hr.algebra.nasaapp.api.parameters

import com.google.gson.annotations.SerializedName

data class MediLogixGetParameterResponse(
    @SerializedName("ParameterID") val _id: Int?,
    @SerializedName("MeasurementID") val measurement: Int?,
    @SerializedName("Name") val name: String?,
    @SerializedName("Note") val dosage: String?,
    )
