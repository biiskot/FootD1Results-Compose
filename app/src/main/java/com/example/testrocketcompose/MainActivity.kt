@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.testrocketcompose

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import com.example.testrocketcompose.data.RetrofitAPI
import com.example.testrocketcompose.objets.AppData
import com.example.testrocketcompose.objets.Parametres
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


var isLoggedIn = false
var dataIsLoaded = false

class MainActivity : ComponentActivity(), CoroutineScope by MainScope() {

    override fun onDestroy() {
        super.onDestroy()

        //Annule la coroutine
        cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("Create main")
        //On lance une coroutine pour s'affranchir de la fonction enqueue()
        // et réaliser les requêtes de manière asynchrone quand même
        GlobalScope.async {
            //Check si les données ont déjà été récup depuis l'API
            if(!dataIsLoaded){
            getAPIData()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({
            setContent {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Parametres.colors[3])
                ) {
                    if(!isLoggedIn){
                        switchToLogin()
                    }
                    //On affiche le composant Display()
                    Display()
                }
            }
        }, 1300)
    }

    private fun getAPIData(){

        println("getAPIData()")

        val baseUrl = "https://www.thesportsdb.com/"
        val retrofitBuilder = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(RetrofitAPI::class.java)

        //Remplissage de la liste contenant les objets match
        //On ajoute les listes récup par Retrofit
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

//Composant si l'utilisateur lance l'appli sans être connecté
@Composable
fun switchToLogin(){
    var context = LocalContext.current
    Toast.makeText(
        context,
        "Connectez vous",
        Toast.LENGTH_SHORT
    ).show()
    context.startActivity(Intent(context, LoginActivity::class.java))
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AffichageParam()
}
