package com.example.testrocketcompose.data

import com.example.testrocketcompose.resultAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

const val leagueDay = "14"

interface RetrofitAPI {

    @GET("api/v1/json/2/eventsround.php?id=4334&r=$leagueDay&s=2022-2023")
    fun getMatchsFra() : Call<resultAPI>

    @GET("api/v1/json/2/eventsround.php?id=4328&r=$leagueDay&s=2022-2023")
    fun getMatchsAng() : Call<resultAPI>

    @GET("api/v1/json/2/eventsround.php?id=4331&r=$leagueDay&s=2022-2023")
    fun getMatchsAll() : Call<resultAPI>

    @GET("api/v1/json/2/eventsround.php?id=4335&r=$leagueDay&s=2022-2023")
    fun getMatchsEsp() : Call<resultAPI>

    @GET("api/v1/json/2/eventsround.php?id=4332&r=$leagueDay&s=2022-2023")
    fun getMatchsIta() : Call<resultAPI>

}