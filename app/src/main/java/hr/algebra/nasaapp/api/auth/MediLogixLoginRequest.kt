package hr.algebra.nasaapp.api.auth

import com.google.gson.annotations.SerializedName

data class MediLogixLoginRequest(
    val email: String,
    val password: String
    )
