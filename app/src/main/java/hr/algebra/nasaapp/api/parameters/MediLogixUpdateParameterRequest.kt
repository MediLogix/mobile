package hr.algebra.nasaapp.api.parameters

import com.google.gson.annotations.SerializedName

data class MediLogixUpdateParameterRequest(
    val parameterID: Int,
    val name: String,
    val unit: String,
    )
