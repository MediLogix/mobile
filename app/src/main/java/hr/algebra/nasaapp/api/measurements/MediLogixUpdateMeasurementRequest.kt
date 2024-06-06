package hr.algebra.nasaapp.api.measurements

import com.google.gson.annotations.SerializedName

data class MediLogixUpdateMeasurementRequest(
    val measurementID: Int,
    val name: String?,
    val note: String?
    )
