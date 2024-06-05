package hr.algebra.nasaapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val PI_URL = "https://api.nasa.gov/planetary/"

interface NasaApi {

    @GET("apod?api_key=DEMO_KEY")
    fun fetchItems(@Query("count") count: Int = 10)
        : Call<List<NasaItem>>

}