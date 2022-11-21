package com.example.testrocketcompose.data

import android.util.Log
import com.example.testrocketcompose.dataIsLoaded
import com.example.testrocketcompose.objets.AppData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Nouvelle requête API si on change le jour
fun newAPIDay(){
    AppData.listMatchsToDisplay.clear()
    println("newAPIDay , liste cleared")

    GlobalScope.async {
        val baseUrl = "https://www.thesportsdb.com/"
        val retrofitBuilder = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(newRequestRetrofitAPI::class.java)

        try{
            AppData.listMatchsToDisplay.addAll(
                retrofitBuilder.getMatchsFra().execute().body()!!.liste
            )
            AppData.listMatchsToDisplay.addAll(
                retrofitBuilder.getMatchsAll().execute().body()!!.liste
            )
            AppData.listMatchsToDisplay.addAll(
                retrofitBuilder.getMatchsAng().execute().body()!!.liste
            )
            AppData.listMatchsToDisplay.addAll(
                retrofitBuilder.getMatchsEsp().execute().body()!!.liste
            )
            AppData.listMatchsToDisplay.addAll(
                retrofitBuilder.getMatchsIta().execute().body()!!.liste
            )
            dataIsLoaded = true
        }
        catch (e:Exception ){
            Log.d("fail exception :", e.toString())
        }
        println(AppData.listMatchsToDisplay.size)
    }

}
