package hr.algebra.nasaapp.api.auth

import com.google.gson.annotations.SerializedName

data class MediLogixRegistrationRequest(
    val email: String,
    val name: String,
    val password: String
    )
