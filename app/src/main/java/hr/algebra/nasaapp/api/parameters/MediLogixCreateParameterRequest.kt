package hr.algebra.nasaapp.api.parameters

import com.google.gson.annotations.SerializedName

data class MediLogixCreateParameterRequest(
    val userID: Int,
    val parameterID: Int,
    val name: String,
    val unit: String,
    )
