package hr.algebra.nasaapp.api

import hr.algebra.nasaapp.api.auth.MediLogixLoginRequest
import hr.algebra.nasaapp.api.auth.MediLogixLoginResponse
import hr.algebra.nasaapp.api.auth.MediLogixRegistrationRequest
import hr.algebra.nasaapp.api.auth.MediLogixRegistrationResponse
import hr.algebra.nasaapp.api.measurements.MediLogixCreateMeasurementRequest
import hr.algebra.nasaapp.api.measurements.MediLogixCreateMeasurementResponse
import hr.algebra.nasaapp.api.measurements.MediLogixUpdateMeasurementRequest
import hr.algebra.nasaapp.api.medications.MediLogixCreateMedicationRequest
import hr.algebra.nasaapp.api.medications.MediLogixCreateMedicationResponse
import hr.algebra.nasaapp.api.medications.MediLogixDeleteMedicationResponse
import hr.algebra.nasaapp.api.medications.MediLogixUpdateMedicationRequest
import hr.algebra.nasaapp.api.medications.MediLogixUpdateMedicationResponse
import hr.algebra.nasaapp.api.parameters.MediLogixCreateParameterRequest
import hr.algebra.nasaapp.api.parameters.MediLogixCreateParameterResponse
import hr.algebra.nasaapp.api.parameters.MediLogixUpdateParameterRequest
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
    @GET("/medications/{id}")
    fun get_medication(
        @Path("id") medicationId: Int
    ): Call<ResponseBody>

    // Measurement endpoints
    @POST("measurements")
    fun createMeasurement(@Body request: MediLogixCreateMeasurementRequest): Call<MediLogixCreateMeasurementResponse>

    @GET("measurements")
    fun getMeasurements(): Call<ResponseBody>

    @GET("measurements/{id}")
    fun getMeasurement(@Path("id") measurementId: Int): Call<ResponseBody>

    @PATCH("measurements/{id}")
    fun updateMeasurement(@Path("id") measurementId: Int, @Body request: MediLogixUpdateMeasurementRequest): Call<ResponseBody>

    @DELETE("measurements/{id}")
    fun deleteMeasurement(@Path("id") measurementId: Int): Call<ResponseBody>

    // Parameter endpoints
    @POST("parameters")
    fun createParameter(@Body request: MediLogixCreateParameterRequest): Call<MediLogixCreateParameterResponse>

    @GET("parameters")
    fun getParameters(): Call<ResponseBody>

    @GET("parameters/{id}")
    fun getParameter(@Path("id") parameterId: Int): Call<ResponseBody>

    @PATCH("parameters/{id}")
    fun updateParameter(@Path("id") parameterId: Int, @Body request: MediLogixUpdateParameterRequest): Call<ResponseBody>

    @DELETE("parameters/{id}")
    fun deleteParameter(@Path("id") parameterId: Int): Call<ResponseBody>

}