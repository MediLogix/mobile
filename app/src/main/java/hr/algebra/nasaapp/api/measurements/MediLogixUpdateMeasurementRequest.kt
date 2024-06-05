package hr.algebra.nasaapp.api.measurements

import com.google.gson.annotations.SerializedName

data class MediLogixUpdateMeasurementRequest(
    val userID: Int,
    val name: String?,
    val dosage: String?,
    val unit: String?,
    val stash: String?,
    val note: String?
    )
