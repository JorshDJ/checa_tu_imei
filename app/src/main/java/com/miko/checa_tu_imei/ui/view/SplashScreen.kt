package com.miko.checa_tu_imei.ui.view

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.miko.checa_tu_imei.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController){
    Splash()
    LaunchedEffect(key1 = true){
        delay(5000)
        navHostController.navigate("home_screen")
    }
}

@Composable
fun Splash (
    //isDarkTheme: MutableState<Boolean>,
    //icon: @Composable() (() -> Unit)?
){
    MaterialTheme() {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 40.dp,
                    vertical = 150.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                ElevatedCard(modifier= Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    /*
                    //PARA EL SWITCH
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Switch(
                            modifier = Modifier.semantics { contentDescription = "Demo with icon" },
                            checked = isDarkTheme.value,
                            onCheckedChange = { isDarkTheme.value = it },
                            thumbContent = icon
                        )
                    }
                     */
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.logo_osiptel),
                            contentDescription = "Logo",
                            modifier = Modifier.size(200.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Checa tu IMEI",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        /*

                         //BUTTON
                        Button(
                            //onClick = { navHostController.navigate(route = AppScreens.ConsultaScreen.route) },
                            onClick = { /*navHostController.navigate(route = AppScreens.ConsultaImeiView.route)*/ },
                            colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.primary)) {
                            /*
                            Icon(
                                imageVector = Icons.Filled.Send,
                                contentDescription = "Favorite Icon",
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                             */
                            Text(text = "Empezar", color = MaterialTheme.colorScheme.onPrimary)
                        }
                         */
                    }
                }
            }
        }
    }
}

