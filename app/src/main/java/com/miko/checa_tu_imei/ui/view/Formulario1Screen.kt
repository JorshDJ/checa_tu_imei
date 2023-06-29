package com.miko.checa_tu_imei.ui.view

//import android.graphics.Color
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.ui.graphics.Color

//package com.miko.checa_tu_imei.ui.view

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.miko.checa_tu_imei.R
import com.miko.checa_tu_imei.ui.navigation.AppScreens
import com.miko.checa_tu_imei.ui.viewmodel.ImeiViewModel

@Composable
fun Formulario1Screen(
    //Parámetro NavHostController
    navHostController: NavHostController,
    viewModel: ImeiViewModel,

    //Parametro Switch
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){
    Formulario1(navHostController = navHostController,viewModel=viewModel, isDarkTheme = isDarkTheme, icon = icon)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario1(
    //Parámetro NavHostController
    navHostController: NavHostController,

    viewModel: ImeiViewModel,
    //Parametro Switch
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){

    //variable para el scroll
    val scrollState = rememberScrollState()

    //Variables para los text field
    val nombre = remember { mutableStateOf("") }
    val apellidos = remember { mutableStateOf("") }
    val tipoDocumento = remember { mutableStateOf("") }
    val numeroDocumento = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }

    //variable para el dropdown TipoDocumento
    var expandedTipoDocumento by remember { mutableStateOf(false) }
    data class OpcionesTipoDocumento(var id: String, var nombreDoc: String, val charLimit:Int, val onlyNumbers: Boolean)
    val itemsTipoDocumento = listOf(
        OpcionesTipoDocumento("1", "DNI",8, true),
        OpcionesTipoDocumento("2", "Passaporte",12, false),
        OpcionesTipoDocumento("3", "Carnet de extranjería",12, false),
        OpcionesTipoDocumento("4", "RUC",11, true),
        OpcionesTipoDocumento("5", "PTP",9, false),
    )
    var selectedOptionTipoDocumento = remember { mutableStateOf(itemsTipoDocumento[0]) }

    //para validacion de Número de documento
    //var tipoDoc by rememberSaveable { mutableStateOf("") }
    var isErrorTipoDoc by rememberSaveable { mutableStateOf(false) }
    //val charLimitTipoDoc = 16
    fun validateTipoDoc(text2: String) {
        isErrorTipoDoc = selectedOptionTipoDocumento.value.nombreDoc.length > selectedOptionTipoDocumento.value.charLimit
    }

    //Variable para validación de NumeroDoc
    var textNumDoc by remember { mutableStateOf("") }
    var isErrorNumDoc by remember { mutableStateOf(false) }

    //Variables para validación de email
    var textEmail by remember { mutableStateOf("") }
    var isErrorEmail by remember { mutableStateOf(false) }
    fun validateEmail(str: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
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
                        ElevatedCard(modifier= Modifier.fillMaxWidth()) {

                            Spacer(modifier = Modifier.height(5.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                //verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp, bottom = 10.dp, start = 30.dp, end = 30.dp)
                                        .wrapContentSize(align = Alignment.Center),
                                    text = "Ingresa tus datos personales para el formulario de consulta",

                                    fontWeight = FontWeight.Bold,
                                    color = Color(android.graphics.Color.parseColor("#599BCC")),
                                    style = TextStyle(fontSize = 18.sp),
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                                    .verticalScroll(scrollState),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.height(30.dp))

                                Image(
                                    painter = painterResource(id = R.drawable.contract),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(100.dp)
                                )
                                Spacer(modifier = Modifier.height(24.dp))

                                //Nombre
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = nombre.value,
                                    onValueChange = { nombre.value = it },
                                    label = { Text("Nombre *") } ,
                                    placeholder = { Text("Nombre * ") },
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                //Apellidos
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = apellidos.value,
                                    onValueChange = { apellidos.value = it },
                                    label = { Text("Apellidos *") } ,
                                    placeholder = { Text("Apellidos *") },
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                ExposedDropdownMenuBox(
                                    modifier = Modifier.fillMaxWidth(),
                                    expanded = expandedTipoDocumento,
                                    onExpandedChange = { expandedTipoDocumento = it },
                                ) {
                                    OutlinedTextField(
                                        // The `menuAnchor` modifier must be passed to the text field for correctness.
                                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                                        readOnly = true,
                                        value = selectedOptionTipoDocumento.value.nombreDoc,
                                        onValueChange = {},
                                        label = { Text("Tipo de documento legal *") },
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipoDocumento) },
                                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expandedTipoDocumento,
                                        onDismissRequest = { expandedTipoDocumento = false },
                                    ) {
                                        itemsTipoDocumento.forEach { selectionOption ->
                                            DropdownMenuItem(
                                                text = { Text(selectionOption.nombreDoc) },
                                                onClick = {
                                                    selectedOptionTipoDocumento.value = selectionOption
                                                    textNumDoc = "" // Limpiamos el TextField cada vez que se selecciona una nueva opción
                                                    isErrorNumDoc = false // También reseteamos el estado de error
                                                    expandedTipoDocumento = false
                                                },
                                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))

                                //OutlinedTextField CON VALIDACIONES PARA NUMDOC
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = textNumDoc,
                                    label = { Text("Numero de documento legal *") } ,
                                    placeholder = { Text("Numero de documento legal *") },
                                    onValueChange = { input ->
                                        val option = selectedOptionTipoDocumento.value
                                        val regex = if (option.onlyNumbers) "[^0-9]".toRegex() else "[^a-zA-Z0-9]".toRegex()
                                        if (input.length <= option.charLimit && !input.contains(regex)) {
                                            textNumDoc = input
                                            isErrorNumDoc = false
                                        } else {
                                            isErrorNumDoc = true
                                        }
                                        if (input.length < option.charLimit) {
                                            isErrorNumDoc = true
                                        }
                                    },
                                    singleLine = true,
                                    isError = isErrorNumDoc,
                                    supportingText = {
                                        val remainingCharacters = selectedOptionTipoDocumento.value.charLimit - textNumDoc.length
                                        when {
                                            isErrorNumDoc && remainingCharacters > 0 -> {
                                                Text(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    text = "Faltan $remainingCharacters caracteres",
                                                    color = MaterialTheme.colorScheme.error
                                                )
                                            }
                                            isErrorNumDoc && remainingCharacters <= 0 -> {
                                                Text(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    text = "La entrada es inválida",
                                                    color = MaterialTheme.colorScheme.error
                                                )
                                            }
                                        }
                                    },
                                    trailingIcon = {
                                        if (isErrorNumDoc)
                                            Icon(Icons.Filled.Favorite, "error", tint = MaterialTheme.colorScheme.error)
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = if (selectedOptionTipoDocumento.value.onlyNumbers) KeyboardType.Number else KeyboardType.Text)
                                )
                                Spacer(modifier = Modifier.height(10.dp))


                                //OutlinedTextField CON VALIDACIÓN DE CORREO ELECTRÓNICO
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = textEmail,
                                    onValueChange = {
                                        textEmail = it
                                        println(textEmail)
                                        isErrorEmail = !validateEmail(textEmail)
                                        println(validateEmail(textEmail))
                                    },
                                    label = { Text("Correo electrónico *") } ,
                                    placeholder = { Text("Correo electrónico *") },
                                    singleLine = true,
                                    isError =  isErrorEmail ,
                                    supportingText = {
                                        if (isErrorEmail) {
                                            Text(
                                                modifier = Modifier.fillMaxWidth(),
                                                text = "El correo electrónico es inválido",
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    },
                                    trailingIcon = {
                                        if (isErrorEmail)
                                            Icon(Icons.Filled.Favorite,"error", tint = MaterialTheme.colorScheme.error)
                                    },
                                    //keyboardActions = KeyboardActions(onDone = { isError = !validateEmail(text) })
                                )
                                //
                                Spacer(modifier = Modifier.height(30.dp))
                                Row() {

                                    ElevatedButton(
                                        modifier = Modifier.weight(1f),

                                        onClick = {
                                            navHostController.navigate( AppScreens.Formulario2Screen.route )
                                            /*
                                            //PARA VALIDAR
                                            if (nombre.value.isBlank() ||
                                                apellidos.value.isBlank() ||
                                                tipoDocumento.value.isBlank() ||
                                                numeroDocumento.value.isBlank() ||
                                                correo.value.isBlank()
                                                // Mostrar un mensaje al usuario indicando que todos los campos son obligatorios
                                            ) {

                                            } else {
                                                // Procesar los datos del formulario
                                                navHostController.navigate(AppScreens.FormularioSegundoScreen.route)
                                            }
                                             */

                                        },
                                        colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.primary)) {

                                        Icon(
                                            imageVector = Icons.Filled.Send,
                                            contentDescription = "Favorite Icon",
                                            modifier = Modifier.align(Alignment.CenterVertically)
                                        )
                                        Text(
                                            modifier = Modifier.padding(start = 10.dp),
                                            text = "Continuar",
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

