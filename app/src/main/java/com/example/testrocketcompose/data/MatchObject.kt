package com.example.testrocketcompose

import com.google.gson.annotations.SerializedName

//Structure d'un match (mêmes nom que l'API pour Gson)
data class MatchObject(
        val strHomeTeam:String,
        val strAwayTeam:String,
        val strLeague:String,
        val intHomeScore: Int,
        val intAwayScore: Int,
        val strPoster : String,
        val strTime:String
)

//Format de retour de Retrofit
public class resultAPI(
        @SerializedName("events")
        val liste: List<MatchObject>
)