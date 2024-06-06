package hr.algebra.nasaapp.api.measurements

import com.google.gson.annotations.SerializedName

data class MediLogixCreateMeasurementRequest(
    val userID: Int,
    val name: String,
    val note: String
    )
