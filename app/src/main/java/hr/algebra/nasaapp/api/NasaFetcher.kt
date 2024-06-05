package hr.algebra.nasaapp.api

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import hr.algebra.nasaapp.NASA_PROVIDER_CONTENT_URI
import hr.algebra.nasaapp.NasaReceiver
import hr.algebra.nasaapp.framework.sendBroadcast
import hr.algebra.nasaapp.handler.downloadAndStore
import hr.algebra.nasaapp.model.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NasaFetcher(private val context: Context) {

    private val nasaApi: NasaApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        nasaApi = retrofit.create(NasaApi::class.java)
    }

    fun fetchItems(count: Int) {

        val request = nasaApi.fetchItems(count = 10)

        request.enqueue(object: Callback<List<NasaItem>> {
            override fun onResponse(
                call: Call<List<NasaItem>>,
                response: Response<List<NasaItem>>
            ) {
                response.body()?.let { populateItems(it) }
            }

            override fun onFailure(call: Call<List<NasaItem>>, t: Throwable) {
                Log.e(javaClass.name, t.toString(), t)
            }

        })



    }

    private fun populateItems(nasaItems: List<NasaItem>) {
        // FOREGROUND - do not go to internet!!!
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            //BACKROUND
            nasaItems.forEach {
                val picturePath = downloadAndStore(context, it.url)

                // prepare ContentValues and insert into CP

                val values = ContentValues().apply {
                    put(Item::title.name, it.title)
                    put(Item::explanation.name, it.explanation)
                    put(Item::picturePath.name, picturePath?:"")
                    put(Item::date.name, it.date)
                    put(Item::read.name, false)
                }

                context.contentResolver.insert(NASA_PROVIDER_CONTENT_URI, values)


            }

            context.sendBroadcast<NasaReceiver>()
        }
    }
}