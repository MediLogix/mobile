package hr.algebra.nasaapp.api.parameters

import com.google.gson.annotations.SerializedName

data class MediLogixCreateParameterRequest(
    val measurementID: Int,
    val name: String,
    val unit: String,
    )
