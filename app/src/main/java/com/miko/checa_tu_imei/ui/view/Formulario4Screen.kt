package com.miko.checa_tu_imei.ui.view

//import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.miko.checa_tu_imei.R
import com.miko.checa_tu_imei.ui.navigation.AppScreens
import com.miko.checa_tu_imei.ui.view.components.DatePickerExample
import com.miko.checa_tu_imei.ui.view.components.TimePickerExample
import com.miko.checa_tu_imei.ui.viewmodel.ImeiViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Formulario4Screen(
    //Parámetro NavHostController
    navHostController: NavHostController,
    viewModel: ImeiViewModel,

    //Parametro Switch
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){
    Formulario4(navHostController = navHostController,viewModel=viewModel, isDarkTheme = isDarkTheme, icon = icon)
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario4(
    //Parámetro NavHostController
    navHostController: NavHostController,

    viewModel: ImeiViewModel,
    //Parametro Switch
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){

    //variables para scroll de column
    val scrollState = rememberScrollState()

    //variables para el campo comentario
    var comentarios by rememberSaveable { mutableStateOf("") }

    //Variable para Capturar archivo
    val result = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        result.value = it
    }

    MaterialTheme() {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                "Reporte IMEI",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navHostController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Localized description"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "Localized description"
                                )
                            }
                        }
                    )
                },
                content = { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding),

                        ) {
                        ElevatedCard(modifier=Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.height(5.dp))


                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                                    .verticalScroll(scrollState),

                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center

                                ) {
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        Box(modifier = Modifier
                                            .fillMaxWidth()
                                            .background(color = MaterialTheme.colorScheme.primary)) {
                                            Text(
                                                modifier = Modifier.padding(top = 10.dp, bottom = 15.dp, start = 16.dp, end = 16.dp),
                                                text = "Finalizar Reporte: ",
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                style = TextStyle(fontSize = 20.sp)
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(20.dp))

                                Image(
                                    painter = painterResource(id = R.drawable.smartphone),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(150.dp)
                                )
                                Spacer(modifier = Modifier.height(24.dp))

                                //OutLinedTextField
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = comentarios,
                                    onValueChange = { comentarios = it },
                                    label = { Text("Comentarios *") } ,
                                    placeholder = { Text("Comentarios * ") },
                                    maxLines = 5,
                                )
                                TextField(
                                    value = comentarios,
                                    onValueChange ={ comentarios = it } )
                                Spacer(modifier = Modifier.height(24.dp))

                                Row(modifier = Modifier.fillMaxWidth()) {
                                    OutlinedCard(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        Box(modifier = Modifier
                                            .fillMaxWidth()
                                        ) {
                                            Column() {
                                                Text(
                                                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                                                    text = "Adjuntar Archivo (PDF o imagenes)",
                                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                    textAlign = TextAlign.Justify
                                                )
                                                Row() {
                                                    ElevatedButton(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .padding(16.dp),
                                                        //onClick = { pickFileLauncher.launch("image/*") },
                                                        onClick = { launcher.launch("image/*") },
                                                        colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.primary)
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Filled.Info,
                                                            contentDescription = "Icono Info",
                                                            modifier = Modifier.align(Alignment.CenterVertically)
                                                        )
                                                        Text(
                                                            modifier = Modifier.padding(start = 10.dp),
                                                            text = "Buscar archivo",
                                                            color = MaterialTheme.colorScheme.onPrimary,
                                                            style = TextStyle(fontSize = 18.sp)
                                                        )
                                                    }

                                                }
                                                Column() {
                                                    result.value?.let { uri ->
                                                        Text("Archivo seleccionado: $uri")
                                                    }
                                                    IconButton(onClick = { result.value=null }) {
                                                        Icon(Icons.Outlined.Lock, contentDescription = "Localized description")
                                                    }

                                                }
                                                /*
                                                fileUri?.let { uri ->
                                                    TextButton(onClick = { }) {
                                                        Text(uri.toString())
                                                    }
                                                }

                                                 */
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(30.dp))
                                Row() {
                                    ElevatedButton(
                                        modifier = Modifier.weight(1f),
                                        onClick = {  },
                                        colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.primary,
                                        )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.List,
                                            contentDescription = "Favorite Icon",
                                            modifier = Modifier.align(Alignment.CenterVertically)
                                        )

                                        Text(
                                            modifier = Modifier.padding(start = 10.dp),
                                            text = "Finalizar",
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            style = TextStyle(fontSize = 18.sp)
                                        )
                                    }
                                }
                            }
                        }

                    }
                }
            )
        }
    }
}