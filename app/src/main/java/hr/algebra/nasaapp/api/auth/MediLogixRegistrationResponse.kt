package hr.algebra.nasaapp.api.auth

import com.google.gson.annotations.SerializedName

data class MediLogixRegistrationResponse(
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("refresh_token") val refreshToken: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("statusCode") val statusCode: Int?,
    )
