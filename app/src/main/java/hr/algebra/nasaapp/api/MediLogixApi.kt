package hr.algebra.nasaapp.api

import hr.algebra.nasaapp.api.auth.MediLogixLoginRequest
import hr.algebra.nasaapp.api.auth.MediLogixLoginResponse
import hr.algebra.nasaapp.api.auth.MediLogixRegistrationRequest
import hr.algebra.nasaapp.api.auth.MediLogixRegistrationResponse
import hr.algebra.nasaapp.api.medications.MediLogixCreateMedicationRequest
import hr.algebra.nasaapp.api.medications.MediLogixCreateMedicationResponse
import hr.algebra.nasaapp.api.medications.MediLogixDeleteMedicationResponse
import hr.algebra.nasaapp.api.medications.MediLogixUpdateMedicationRequest
import hr.algebra.nasaapp.api.medications.MediLogixUpdateMedicationResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

const val API_URL = "http://10.0.2.2:3000/"

interface MediLogixApi {
    @POST("/auth/register")
    fun register(
        @Body request: MediLogixRegistrationRequest
    ): Call<MediLogixRegistrationResponse>

    @POST("/auth/login")
    fun login(
        @Body request: MediLogixLoginRequest
    ): Call<MediLogixLoginResponse>

    @POST("/medications")
    fun create_medication(
        @Body request: MediLogixCreateMedicationRequest
    ): Call<MediLogixCreateMedicationResponse>

    @DELETE("/medications/{id}")
    fun delete_medication(
        @Path("id") medicationId: Int
    ): Call<MediLogixDeleteMedicationResponse>
    @PATCH("/medications/{id}")
    fun update_medication(
        @Body request: MediLogixUpdateMedicationRequest,
        @Path("id") medicationId: Int
    ): Call<MediLogixUpdateMedicationResponse>

    @GET("/medications")
    fun get_medications(
    ): Call<ResponseBody>

}