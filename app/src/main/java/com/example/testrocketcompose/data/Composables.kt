package com.example.testrocketcompose

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.testrocketcompose.objets.AppData
import com.example.testrocketcompose.objets.Parametres

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle

//Composant sous forme de Card qui affiche un match
@Composable
fun MatchComposable (match: MatchObject) {
    Card(
        modifier = Modifier.fillMaxWidth().height(130.dp).padding(horizontal = 5.dp, vertical = 5.dp),
        colors= CardDefaults.cardColors(Parametres.colors[1]),
        shape = RoundedCornerShape(16.dp),
        ) {
            Text(modifier = Modifier.padding(vertical = 3.dp,horizontal = 12.dp),text = match.strLeague,style = typography.titleMedium)
            Row(
               modifier = Modifier.padding(vertical = 2.dp,horizontal = 2.dp)
            ) {

                    Image(
                        painter = rememberAsyncImagePainter(match.strPoster),
                        contentDescription = null,
                        modifier = Modifier.size(85.dp).padding(vertical = 6.dp,horizontal = 6.dp)
                    )

                        Column (modifier = Modifier.padding(vertical = 6.dp,horizontal = 10.dp)){
                            Text(text = match.strHomeTeam,style = typography.bodyLarge)
                            Text(text = match.intHomeScore.toString(),style = typography.bodyLarge,modifier = Modifier.padding(horizontal = 20.dp))
                        }
                        Text(text = " Vs. ",style = typography.titleMedium)
                        Column(modifier = Modifier.padding(vertical = 6.dp,horizontal = 10.dp)){
                            Text(text = match.strAwayTeam,style = typography.bodyLarge)
                            Text(text = match.intAwayScore.toString(),style = typography.bodyLarge,modifier = Modifier.padding(horizontal = 20.dp))
                        }

            }
        }
    }

///Composant contenant e recyclerview jetpack compose LazyColumn
@SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Display() {

    //  var listematchs by remember { mutableStateOf(AppData.listMatchsToDisplay) }
    var listematchs = AppData.listMatchsToDisplay
    println("display()")
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Parametres.colors[0]),
                title = {
                    Text(
                        "FootD1Results",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = typography.titleLarge
                    )
                },
                navigationIcon = {
                    BoutonParam()
                },
                actions = {
                    BoutonProfile()
                }
            )
        },
        content = {
            Column() {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 75.dp)
                ) {
                    items(
                        items = listematchs,
                        itemContent = {
                            //On affiche un composant match pour chaque match de la liste
                            MatchComposable(match = it)
                        }
                    )

                }
            }
        }
    )
}


@Composable
fun BoutonParam(){
    val contexte = LocalContext.current
    Button(
        modifier = Modifier.padding(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Parametres.colors[2]),
        onClick = {
        contexte.startActivity(Intent(contexte, ParametresActivity::class.java))
    }) {
        Image(painter = painterResource(id = R.drawable.ic_baseline_settings_24), contentDescription = "bouton parametres")
    }
}

@Composable
fun BoutonProfile(){
    val contexte = LocalContext.current
    Button(
        modifier = Modifier.padding(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Parametres.colors[2]),
        onClick = {
        contexte.startActivity(Intent(contexte, LoginActivity::class.java))
    }) {
        Image(painter = painterResource(id = R.drawable.ic_baseline_person_24), contentDescription = "bouton parametres")
    }
}
@Composable
fun BoutonAccueil(){
    val contexte = LocalContext.current
    Button(
        modifier = Modifier.padding(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Parametres.colors[2])
        ,onClick = {
        contexte.startActivity(Intent(contexte, MainActivity::class.java))
    }) {
        Image(painter = painterResource(id = R.drawable.ic_baseline_home_24), contentDescription = "bouton parametres")
    }
}
