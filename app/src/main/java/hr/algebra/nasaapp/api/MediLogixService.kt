package hr.algebra.nasaapp.api

import android.content.Context
import android.util.Log
import hr.algebra.nasaapp.api.auth.MediLogixLoginRequest
import hr.algebra.nasaapp.api.auth.MediLogixLoginResponse
import hr.algebra.nasaapp.api.auth.MediLogixRegistrationRequest
import hr.algebra.nasaapp.api.auth.MediLogixRegistrationResponse
import hr.algebra.nasaapp.api.medications.MediLogixCreateMedicationRequest
import hr.algebra.nasaapp.api.medications.MediLogixCreateMedicationResponse
import hr.algebra.nasaapp.api.medications.MediLogixDeleteMedicationResponse
import hr.algebra.nasaapp.api.medications.MediLogixUpdateMedicationRequest
import hr.algebra.nasaapp.api.medications.MediLogixUpdateMedicationResponse
import hr.algebra.nasaapp.handler.SharedPreferencesHelper
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

    fun delete_medication(user_id: Int, onResult: (Boolean, String?) -> Unit) {
        mediLogixApi.delete_medication(user_id).enqueue(object : Callback<MediLogixDeleteMedicationResponse> {
            override fun onResponse(call: Call<MediLogixDeleteMedicationResponse>, response: Response<MediLogixDeleteMedicationResponse>) {
                if (response.isSuccessful) {
                    // Handle successful registration
                    val deleteResponse = response.body()

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

            override fun onFailure(call: Call<MediLogixDeleteMedicationResponse>, t: Throwable) {
                // Handle network failures or other errors
                Log.e("MediLogixService", "Deletion failed", t)
                // Call the callback with a generic error message
                onResult(false, "Network error: ${t.message}. Please try again.")
            }
        })
    }

    fun get_medications(user_id: Int, onResult: (Boolean, String?) -> Unit) {
        mediLogixApi.get_medications().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val getResponse = response.body()

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

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle network failures or other errors
                Log.e("MediLogixService", "Retrival failed", t)
                // Call the callback with a generic error message
                onResult(false, "Network error: ${t.message}. Please try again.")
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


}