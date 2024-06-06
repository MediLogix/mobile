package hr.algebra.nasaapp.api

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hr.algebra.nasaapp.api.auth.MediLogixLoginRequest
import hr.algebra.nasaapp.api.auth.MediLogixLoginResponse
import hr.algebra.nasaapp.api.auth.MediLogixRegistrationRequest
import hr.algebra.nasaapp.api.auth.MediLogixRegistrationResponse
import hr.algebra.nasaapp.api.measurements.MediLogixCreateMeasurementRequest
import hr.algebra.nasaapp.api.measurements.MediLogixCreateMeasurementResponse
import hr.algebra.nasaapp.api.measurements.MediLogixGetMeasurementResponse
import hr.algebra.nasaapp.api.measurements.MediLogixUpdateMeasurementRequest
import hr.algebra.nasaapp.api.medications.MediLogixCreateMedicationRequest
import hr.algebra.nasaapp.api.medications.MediLogixCreateMedicationResponse
import hr.algebra.nasaapp.api.medications.MediLogixDeleteMedicationResponse
import hr.algebra.nasaapp.api.medications.MediLogixUpdateMedicationRequest
import hr.algebra.nasaapp.api.medications.MediLogixUpdateMedicationResponse
import hr.algebra.nasaapp.api.parameters.MediLogixCreateParameterRequest
import hr.algebra.nasaapp.api.parameters.MediLogixCreateParameterResponse
import hr.algebra.nasaapp.api.parameters.MediLogixGetParameterResponse
import hr.algebra.nasaapp.api.parameters.MediLogixUpdateParameterRequest
import hr.algebra.nasaapp.handler.SharedPreferencesHelper
import hr.algebra.nasaapp.model.Measurement
import hr.algebra.nasaapp.model.Medication
import hr.algebra.nasaapp.model.Parameter
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MediLogixService {
    private val mediLogixApi: MediLogixApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mediLogixApi = retrofit.create(MediLogixApi::class.java)
    }

    fun register(context: Context, email: String, displayName: String, password: String, onResult: (Boolean, String?) -> Unit) {

        val request = MediLogixRegistrationRequest(email, displayName, password)

        mediLogixApi.register(request).enqueue(object : Callback<MediLogixRegistrationResponse> {
                    override fun onResponse(call: Call<MediLogixRegistrationResponse>, response: Response<MediLogixRegistrationResponse>) {
                        if (response.isSuccessful) {
                            // Handle successful registration
                            val registrationResponse = response.body()
                            // Extract tokens or relevant data from the response
                            val accessToken = registrationResponse?.accessToken
                            val refreshToken = registrationResponse?.refreshToken
                            if (accessToken != null && refreshToken != null) {
                                SharedPreferencesHelper(context).saveTokens(accessToken, refreshToken)
                            }
                            onResult(true, null)
                        } else {
                            // Handle unsuccessful registration
                            // Extract error message from response if available
                            val errorBody = response.errorBody()?.string()
                            // For example: parseErrorMessage(errorBody)
                            // Call the callback with the error message
                            onResult(false, errorBody)
                        }
                    }

                    override fun onFailure(call: Call<MediLogixRegistrationResponse>, t: Throwable) {
                        // Handle network failures or other errors
                        Log.e("MediLogixService", "Registration failed", t)
                        // Call the callback with a generic error message
                        onResult(false, "Network error: ${t.message}. Please try again.")
                    }
                })
    }

    fun login(context: Context, email: String, password: String, onResult: (Boolean, String?) -> Unit) {

        val request = MediLogixLoginRequest(email, password)

        mediLogixApi.login(request).enqueue(object : Callback<MediLogixLoginResponse> {
            override fun onResponse(call: Call<MediLogixLoginResponse>, response: Response<MediLogixLoginResponse>) {
                if (response.isSuccessful) {
                    // Handle successful registration
                    val registrationResponse = response.body()
                    // Extract tokens or relevant data from the response
                    val accessToken = registrationResponse?.accessToken
                    val refreshToken = registrationResponse?.refreshToken
                    if (accessToken != null && refreshToken != null) {
                        SharedPreferencesHelper(context).saveTokens(accessToken, refreshToken)
                    }
                    onResult(true, null)
                } else {
                    // Handle unsuccessful registration
                    // Extract error message from response if available
                    val errorBody = response.errorBody()?.string()
                    // For example: parseErrorMessage(errorBody)
                    // Call the callback with the error message
                    onResult(false, errorBody)
                }
            }

            override fun onFailure(call: Call<MediLogixLoginResponse>, t: Throwable) {
                // Handle network failures or other errors
                Log.e("MediLogixService", "Registration failed", t)
                // Call the callback with a generic error message
                onResult(false, "Network error: ${t.message}. Please try again.")
            }
        })
    }

    fun delete_medication(medicationId: Int, onResult: (Boolean, String?) -> Unit) {
        mediLogixApi.delete_medication(medicationId).enqueue(object : Callback<MediLogixDeleteMedicationResponse> {
            override fun onResponse(call: Call<MediLogixDeleteMedicationResponse>, response: Response<MediLogixDeleteMedicationResponse>) {
                if (response.isSuccessful) {
                    onResult(true, null)
                } else {
                    val errorBody = response.errorBody()?.string()
                    onResult(false, errorBody)
                }
            }

            override fun onFailure(call: Call<MediLogixDeleteMedicationResponse>, t: Throwable) {
                Log.e("MediLogixService", "Deletion failed", t)
                onResult(false, "Network error: ${t.message}. Please try again.")
            }
        })
    }

    fun get_medications(user_id: Int, onResult: (Boolean, MutableList<Medication>?, String?) -> Unit) {
        mediLogixApi.get_medications().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        val gson = Gson()
                        val medications: MutableList<Medication> = gson.fromJson(responseBody.charStream(), object : TypeToken<MutableList<Medication>>() {}.type)
                        // Handle the mutable list of medications
                        onResult(true, medications, null)

                    }
                } else {
                    // Handle unsuccessful registration
                    // Extract error message from response if available
                    val errorBody = response.errorBody()?.string()
                    // For example: parseErrorMessage(errorBody)
                    // Call the callback with the error message
                    onResult(false, null, errorBody)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle network failures or other errors
                Log.e("MediLogixService", "Retrival failed", t)
                // Call the callback with a generic error message
                onResult(false, null, "Network error: ${t.message}. Please try again.")
            }
        })
    }

    fun create_medication(user_id: Int, name: String, dosage: Double, unit: String, stash: String, note: String, onResult: (Boolean, String?) -> Unit) {

        val request = MediLogixCreateMedicationRequest(user_id, name, dosage, unit, stash, note)

        mediLogixApi.create_medication(request).enqueue(object : Callback<MediLogixCreateMedicationResponse> {
            override fun onResponse(call: Call<MediLogixCreateMedicationResponse>, response: Response<MediLogixCreateMedicationResponse>) {
                if (response.isSuccessful) {
                    // Handle successful registration
                    val updateResponse = response.body()

                    onResult(true, null)
                } else {
                    // Handle unsuccessful registration
                    // Extract error message from response if available
                    val errorBody = response.errorBody()?.string()
                    // For example: parseErrorMessage(errorBody)
                    // Call the callback with the error message
                    onResult(false, errorBody)
                }
            }

            override fun onFailure(call: Call<MediLogixCreateMedicationResponse>, t: Throwable) {
                // Handle network failures or other errors
                Log.e("MediLogixService", "Updating failed", t)
                // Call the callback with a generic error message
                onResult(false, "Network error: ${t.message}. Please try again.")
            }
        })
    }
    fun update_medication(medication_id: Int, name: String?, dosage: Double?, unit: String?, stash: String?, note: String?, onResult: (Boolean, String?) -> Unit) {

        val request = MediLogixUpdateMedicationRequest(medication_id, name, dosage, unit, stash, note)

        mediLogixApi.update_medication(request, medication_id).enqueue(object : Callback<MediLogixUpdateMedicationResponse> {
            override fun onResponse(call: Call<MediLogixUpdateMedicationResponse>, response: Response<MediLogixUpdateMedicationResponse>) {
                if (response.isSuccessful) {
                    // Handle successful registration
                    val updateResponse = response.body()

                    onResult(true, null)
                } else {
                    // Handle unsuccessful registration
                    // Extract error message from response if available
                    val errorBody = response.errorBody()?.string()
                    // For example: parseErrorMessage(errorBody)
                    // Call the callback with the error message
                    onResult(false, errorBody)
                }
            }

            override fun onFailure(call: Call<MediLogixUpdateMedicationResponse>, t: Throwable) {
                // Handle network failures or other errors
                Log.e("MediLogixService", "Updating failed", t)
                // Call the callback with a generic error message
                onResult(false, "Network error: ${t.message}. Please try again.")
            }
        })
    }
    fun get_medication(medicationId: Int, onResult: (Boolean, Medication?, String?) -> Unit) {
        mediLogixApi.get_medication(medicationId).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val gson = Gson()
                        val medication = gson.fromJson(responseBody.charStream(), Medication::class.java)
                        onResult(true, medication, null)
                    } else {
                        onResult(false, null, "Response body is null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    onResult(false, null, errorBody)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("MediLogixService", "Get medication by ID failed", t)
                onResult(false, null, "Network error: ${t.message}. Please try again.")
            }
        })
    }

    // Measurement CRUD operations
    fun createMeasurement(
        measurementName: String,
        note: String,
        onResult: (Boolean, Int?, String?) -> Unit
    ) {
        val request = MediLogixCreateMeasurementRequest(1, measurementName, note)
        mediLogixApi.createMeasurement(request).enqueue(object : Callback<MediLogixCreateMeasurementResponse> {
            override fun onResponse(call: Call<MediLogixCreateMeasurementResponse>, response: Response<MediLogixCreateMeasurementResponse>) {
                if (response.isSuccessful) {
                    val measurementId = response.body()?._id
                    if (measurementId != null) {
                        onResult(true, measurementId.toInt(), null)
                    } else {
                        onResult(false, null, "Measurement ID is null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    onResult(false, null, errorBody)
                }
            }

            override fun onFailure(call: Call<MediLogixCreateMeasurementResponse>, t: Throwable) {
                onResult(false, null, "Network error: ${t.message}. Please try again.")
            }
        })
    }
    fun createMeasurementWithParameter(
        measurementName: String,
        parameterName: String,
        parameterUnit: String,
        note: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        // First, create the measurement
        createMeasurement(measurementName, note) { success, measurementId, errorMessage ->
            if (success && measurementId != null) {
                // Measurement created successfully, now create the parameter
                createParameter(measurementId, parameterName, parameterUnit) { parameterSuccess, parameterErrorMessage ->
                    if (parameterSuccess) {
                        // Parameter created successfully
                        onResult(true, null)
                    } else {
                        // Failed to create parameter
                        onResult(false, parameterErrorMessage ?: "Failed to create parameter")
                    }
                }
            } else {
                // Failed to create measurement
                onResult(false, errorMessage ?: "Failed to create measurement")
            }
        }
    }

    fun getMeasurements(onResult: (Boolean, List<MediLogixGetMeasurementResponse>?, String?) -> Unit) {
        mediLogixApi.getMeasurements().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val gson = Gson()
                    val measurements: List<MediLogixGetMeasurementResponse> = gson.fromJson(response.body()?.charStream(), object : TypeToken<List<MediLogixGetMeasurementResponse>>() {}.type)
                    onResult(true, measurements, null)
                } else {
                    val errorBody = response.errorBody()?.string()
                    onResult(false, null, errorBody)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResult(false, null, "Network error: ${t.message}. Please try again.")
            }
        })
    }

    fun getMeasurement(measurementId: Int, onResult: (Boolean, MediLogixGetMeasurementResponse?, String?) -> Unit) {
        mediLogixApi.getMeasurement(measurementId).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val gson = Gson()
                    val measurement = gson.fromJson(response.body()?.charStream(), MediLogixGetMeasurementResponse::class.java)
                    onResult(true, measurement, null)
                } else {
                    val errorBody = response.errorBody()?.string()
                    onResult(false, null, errorBody)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResult(false, null, "Network error: ${t.message}. Please try again.")
            }
        })
    }

    fun updateMeasurement(measurementId: Int, name: String?, note: String?, onResult: (Boolean, String?) -> Unit) {
        val request = MediLogixUpdateMeasurementRequest(measurementId, name, note)
        mediLogixApi.updateMeasurement(measurementId, request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    onResult(true, null)
                } else {
                    val errorBody = response.errorBody()?.string()
                    onResult(false, errorBody)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResult(false, "Network error: ${t.message}. Please try again.")
            }
        })
    }

    fun deleteMeasurement(measurementId: Int, onResult: (Boolean, String?) -> Unit) {
        // First, delete parameters associated with the measurement
        deleteParametersForMeasurement(measurementId) { parameterSuccess, parameterErrorMessage ->
            if (parameterSuccess) {
                // Parameters deleted successfully, now delete the measurement
                mediLogixApi.deleteMeasurement(measurementId)
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.isSuccessful) {
                                onResult(true, null)
                            } else {
                                val errorBody = response.errorBody()?.string()
                                onResult(false, errorBody)
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            onResult(
                                false,
                                "Network error: ${t.message}. Please try again."
                            )
                        }
                    })
            } else {
                // Failed to delete parameters associated with the measurement
                onResult(false, parameterErrorMessage ?: "Failed to delete parameters")
            }
        }
    }

    private fun deleteParametersForMeasurement(
        measurementId: Int,
        onResult: (Boolean, String?) -> Unit
    ) {
        mediLogixApi.getParameters().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val gson = Gson()
                        val parameters: List<MediLogixGetParameterResponse> =
                            gson.fromJson(
                                responseBody.charStream(),
                                object : TypeToken<List<MediLogixGetParameterResponse>>() {}.type
                            )
                        // Filter parameters associated with the measurement
                        val parametersToDelete = parameters.filter { it.measurement == measurementId }
                        // Delete each parameter
                        parametersToDelete.forEach { parameter ->
                            mediLogixApi.deleteParameter(parameter._id!!)
                                .enqueue(object : Callback<ResponseBody> {
                                    override fun onResponse(
                                        call: Call<ResponseBody>,
                                        response: Response<ResponseBody>
                                    ) {
                                        if (!response.isSuccessful) {
                                            val errorBody = response.errorBody()?.string()
                                            onResult(false, errorBody)
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<ResponseBody>,
                                        t: Throwable
                                    ) {
                                        onResult(
                                            false,
                                            "Network error: ${t.message}. Please try again."
                                        )
                                    }
                                })
                        }
                        // All parameters associated with the measurement deleted successfully
                        onResult(true, null)
                    } else {
                        onResult(false, "Response body is null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    onResult(false, errorBody)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResult(
                    false,
                    "Network error: ${t.message}. Please try again."
                )
            }
        })
    }
    fun updateMeasurementWithParameter(
        measurementId: Int,
        measurementName: String,
        parameterName: String,
        parameterUnit: String,
        note: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        // First, update the measurement
        updateMeasurement(measurementId, measurementName, note) { success, errorMessage ->
            if (success) {
                // Measurement updated successfully, now update the parameter
                updateParameterForMeasurement(measurementId, parameterName, parameterUnit) { parameterSuccess, parameterErrorMessage ->
                    if (parameterSuccess) {
                        // Parameter updated successfully
                        onResult(true, null)
                    } else {
                        // Failed to update parameter
                        onResult(false, parameterErrorMessage ?: "Failed to update parameter")
                    }
                }
            } else {
                // Failed to update measurement
                onResult(false, errorMessage ?: "Failed to update measurement")
            }
        }
    }
    private fun updateParameterForMeasurement(
        measurementId: Int,
        parameterName: String,
        parameterUnit: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        // Retrieve parameters for the given measurement ID
        getParametersForMeasurement(measurementId) { isSuccess, parameters, error ->
            if (isSuccess && parameters != null) {
                // Find the parameter associated with the measurement ID
                val parameter = parameters.firstOrNull()

                if (parameter != null) {
                    // Update the parameter with the new name and unit
                    updateParameter(parameter._id!!, parameterName, parameterUnit) { success, errorMessage ->
                        if (success) {
                            onResult(true, null)
                        } else {
                            onResult(false, errorMessage ?: "Failed to update parameter")
                        }
                    }
                } else {
                    onResult(false, "Parameter not found for measurement ID: $measurementId")
                }
            } else {
                onResult(false, error ?: "Failed to retrieve parameters")
            }
        }
    }
    fun createParameter( measurementId: Int, name: String, unit: String, onResult: (Boolean, String?) -> Unit) {
        val request = MediLogixCreateParameterRequest( measurementId, name, unit)
        mediLogixApi.createParameter(request).enqueue(object : Callback<MediLogixCreateParameterResponse> {
            override fun onResponse(call: Call<MediLogixCreateParameterResponse>, response: Response<MediLogixCreateParameterResponse>) {
                if (response.isSuccessful) {
                    onResult(true, null)
                } else {
                    val errorBody = response.errorBody()?.string()
                    onResult(false, errorBody)
                }
            }

            override fun onFailure(call: Call<MediLogixCreateParameterResponse>, t: Throwable) {
                onResult(false, "Network error: ${t.message}. Please try again.")
            }
        })
    }

    fun getParameters(onResult: (Boolean, List<MediLogixGetParameterResponse>?, String?) -> Unit) {
        mediLogixApi.getParameters().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val gson = Gson()
                    val parameters: List<MediLogixGetParameterResponse> = gson.fromJson(response.body()?.charStream(), object : TypeToken<List<MediLogixGetParameterResponse>>() {}.type)
                    onResult(true, parameters, null)
                } else {
                    val errorBody = response.errorBody()?.string()
                    onResult(false, null, errorBody)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResult(false, null, "Network error: ${t.message}. Please try again.")
            }
        })
    }

    fun getParameter(parameterId: Int, onResult: (Boolean, MediLogixGetParameterResponse?, String?) -> Unit) {
        mediLogixApi.getParameter(parameterId).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val gson = Gson()
                    val parameter = gson.fromJson(response.body()?.charStream(), MediLogixGetParameterResponse::class.java)
                    onResult(true, parameter, null)
                } else {
                    val errorBody = response.errorBody()?.string()
                    onResult(false, null, errorBody)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResult(false, null, "Network error: ${t.message}. Please try again.")
            }
        })
    }

    fun updateParameter(parameterId: Int, name: String, unit: String, onResult: (Boolean, String?) -> Unit) {
        val request = MediLogixUpdateParameterRequest(parameterId, name, unit)
        mediLogixApi.updateParameter(parameterId, request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    onResult(true, null)
                } else {
                    val errorBody = response.errorBody()?.string()
                    onResult(false, errorBody)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResult(false, "Network error: ${t.message}. Please try again.")
            }
        })
    }

    fun deleteParameter(parameterId: Int, onResult: (Boolean, String?) -> Unit) {
        mediLogixApi.deleteParameter(parameterId).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    onResult(true, null)
                } else {
                    val errorBody = response.errorBody()?.string()
                    onResult(false, errorBody)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResult(false, "Network error: ${t.message}. Please try again.")
            }
        })
    }
    fun getMeasurementsWithParameters(onResult: (Boolean, List<Pair<MediLogixGetMeasurementResponse, List<MediLogixGetParameterResponse>>>?, String?) -> Unit) {
        mediLogixApi.getMeasurements().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val gson = Gson()
                    val measurements: List<MediLogixGetMeasurementResponse> = gson.fromJson(response.body()?.charStream(), object : TypeToken<List<MediLogixGetMeasurementResponse>>() {}.type)

                    // List to hold measurement-parameter pairs
                    val measurementParameterPairs = mutableListOf<Pair<MediLogixGetMeasurementResponse, List<MediLogixGetParameterResponse>>>()

                    // Iterate over measurements to fetch parameters for each measurement
                    for (measurement in measurements) {
                        getParametersForMeasurement(measurement._id!!) { isSuccess, parameters, error ->
                            if (isSuccess && parameters != null) {
                                // Filter parameters based on measurement ID
                                val filteredParameters = parameters.filter { it.measurement == measurement._id }
                                // Add measurement-parameter pair to the list
                                measurementParameterPairs.add(Pair(measurement, filteredParameters))
                                // Check if all measurements have been processed
                                if (measurementParameterPairs.size == measurements.size) {
                                    // Call the callback function with the result
                                    onResult(true, measurementParameterPairs, null)
                                }
                            } else {
                                // Handle error scenario
                                onResult(false, null, error)
                                return@getParametersForMeasurement
                            }
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    onResult(false, null, errorBody)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResult(false, null, "Network error: ${t.message}. Please try again.")
            }
        })
    }

    private fun getParametersForMeasurement(measurementId: Int, onResult: (Boolean, List<MediLogixGetParameterResponse>?, String?) -> Unit) {
        mediLogixApi.getParameters().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val gson = Gson()
                        val parameters: List<MediLogixGetParameterResponse> = gson.fromJson(responseBody.charStream(), object : TypeToken<List<MediLogixGetParameterResponse>>() {}.type)
                        onResult(true, parameters, null)
                    } else {
                        onResult(false, null, "Response body is null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    onResult(false, null, errorBody)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResult(false, null, "Network error: ${t.message}. Please try again.")
            }
        })
    }

    fun getMeasurementWithParameters(measurementId: Int, onResult: (Boolean, Measurement?, List<Parameter>?, String?) -> Unit) {
        mediLogixApi.getMeasurement(measurementId).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val gson = Gson()
                    val body = response.body()?.string()
                    if (body != null) {
                        val measurementResponse: MediLogixGetMeasurementResponse? = gson.fromJson(body, MediLogixGetMeasurementResponse::class.java)
                        if (measurementResponse != null) {
                            // Convert MediLogixGetMeasurementResponse to Measurement
                            val measurement = Measurement(
                                measurementResponse._id ?: -1,
                                measurementResponse.user ?: -1,
                                measurementResponse.name ?: "",
                                measurementResponse.note ?: "",
                                emptyList() // Parameters will be set later
                            )

                            // Fetch parameters for the measurement
                            getParametersForMeasurement(measurement.id) { isSuccess, parameterResponses, error ->
                                if (isSuccess && parameterResponses != null) {
                                    // Convert MediLogixGetParameterResponse to Parameter
                                    val parameters = parameterResponses.map { parameterResponse ->
                                        Parameter(
                                            parameterResponse._id ?: -1,
                                            parameterResponse.measurement ?: -1,
                                            parameterResponse.name ?: "",
                                            parameterResponse.unit ?: ""
                                        )
                                    }
                                    onResult(true, measurement, parameters, null)
                                } else {
                                    onResult(false, null, null, error ?: "Failed to fetch parameters")
                                }
                            }
                            return // Exit onResponse after processing successful response
                        }
                    }
                    // Handle null or invalid response
                    onResult(false, null, null, "Failed to parse measurement data")
                } else {
                    val errorBody = response.errorBody()?.string()
                    onResult(false, null, null, errorBody ?: "Failed to fetch measurement")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResult(false, null, null, "Network error: ${t.message}. Please try again.")
            }
        })
    }

}