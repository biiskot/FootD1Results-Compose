package com.example.testrocketcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.testrocketcompose.data.leagueDay
import com.example.testrocketcompose.data.newAPIDay
import com.example.testrocketcompose.objets.AppData
import com.example.testrocketcompose.objets.Parametres


class ParametresActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Create param")
        setContent {
        AffichageParam()
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AffichageParam() {
    var tri_sorts = listOf<String>("Date", "Pays A-Z (eng)","Equipe domicile A-Z","Equipe extérieure A-Z")
    var expanded by remember { mutableStateOf(false) }
    var expanded1 by remember { mutableStateOf(false) }
    var selectedItem by remember {
        mutableStateOf(Parametres.newleagueDay)
    }
    var selectedTri by remember { mutableStateOf(Parametres.tri)}
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Parametres.colors[0]),
                title = {
                    Text(
                        "FootD1Results",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    BoutonAccueil()
                },
                actions = {
                    BoutonProfile()
                }
            )
        },
        content = {
            Column(Modifier.padding(0.dp, 100.dp)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(horizontal = 6.dp, vertical = 6.dp),
                    colors = CardDefaults.cardColors(Parametres.colors[1]),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(text = "Page paramètres",style = typography.titleSmall, modifier = Modifier.padding(horizontal = 13.dp,vertical = 5.dp))
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(horizontal = 6.dp, vertical = 6.dp),
                    colors = CardDefaults.cardColors(Parametres.colors[1]),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(text = "Choisissez le jour de ligue" ,style = typography.titleMedium, modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp))
                    TextButton(onClick = { expanded = true }) {
                        Text(selectedItem)
                    }
                    //Menu sélection jour de ligue
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        for (i in 1..15) {
                            //On crée un item dans le menu pour les différents jours de match
                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                    Parametres.newleagueDay = i.toString()
                                    selectedItem = i.toString()
                                    println("clique sur " + i.toString())
                                    newAPIDay()
                                },
                                text = { Text(text = i.toString()) },
                            )
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(horizontal = 6.dp, vertical = 6.dp),
                    colors = CardDefaults.cardColors(Parametres.colors[1]),
                    shape = RoundedCornerShape(16.dp),
                ) {
                        Text(text = "Choisissez l'ordre de tri des matchs" ,style = typography.titleMedium, modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp))
                        TextButton(onClick = { expanded1 = true }) {
                            Text(selectedTri)
                        }
                    //Menu sélection tri
                        DropdownMenu(

                            expanded = expanded1,
                            onDismissRequest = { expanded1 = false }

                        ) {
                            tri_sorts.forEachIndexed { index, value ->
                                DropdownMenuItem(
                                    onClick = {
                                        expanded1 = false
                                        Parametres.tri = value
                                        println("Tri sélectionné : " + Parametres.tri)
                                        newDisplayMatch()
                                    },
                                    text = { Text(text = value) },
                                )
                            }
                        }
                }
            }
        }
    )
}

//Tri de la liste des objets match en fonction de ce qu'a choisi l'utilisateur
fun newDisplayMatch(){
    println(AppData.listMatchsToDisplay)
    when(Parametres.tri){
        "Date" -> {
        AppData.listMatchsToDisplay.sortBy { it.strTime.substring(0,2).toInt() }
        }
        "Pays A-Z (eng)" -> {
            AppData.listMatchsToDisplay.sortBy { it.strTime.substring(0,1) }
        }
        "Equipe domicile A-Z" -> {
            AppData.listMatchsToDisplay.sortBy { it.strHomeTeam.substring(0,1) }
        }
        "Equipe extérieure A-Z" -> {
            AppData.listMatchsToDisplay.sortBy { it.strAwayTeam.substring(0,1) }
        }
        else -> println("Erreur newDisplayMatch")
    }
    println(AppData.listMatchsToDisplay)
}
