package com.miko.checa_tu_imei.ui.view

//import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.miko.checa_tu_imei.ui.viewmodel.ImeiViewModel
import kotlinx.coroutines.launch

@Composable
fun Formulario2Screen(
    //Parámetro NavHostController
    navHostController: NavHostController,
    viewModel: ImeiViewModel,

    //Parametro Switch
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){
    Formulario2(navHostController = navHostController,viewModel=viewModel, isDarkTheme = isDarkTheme, icon = icon)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario2(
    //Parámetro NavHostController
    navHostController: NavHostController,

    viewModel: ImeiViewModel,
    //Parametro Switch
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){

    //variable para el scroll
    val scrollState = rememberScrollState()

    //val imei = remember { mutableStateOf("") }
    var imei by rememberSaveable { mutableStateOf("") }

    //para validacion de imei
    var isError by rememberSaveable { mutableStateOf(false) }
    val charLimitImei = 16
    fun validateImei(text2: String) {
        isError = imei.length > charLimitImei
    }

    //VARIABLES PARA EL DROPDOWN MARCAS
    val marcas by viewModel.marcas.observeAsState(initial = emptyList())
    var expandedMarcaMovil by remember { mutableStateOf(false) }
    data class OpcionesMarcaMovil(var name: String, val id: String)
    val itemsMarcaMovil = marcas.map { OpcionesMarcaMovil(it.nombre, it.id) }
    var selectedOptionMarcaMovil = remember { mutableStateOf(itemsMarcaMovil.firstOrNull() ?: OpcionesMarcaMovil("", "")) }


    //VARIABLES PARA EL DROPDOWN MODELO MOVIL
    val modelos by viewModel.modelos.observeAsState(initial = emptyList())
    var expandedModelMovil by remember { mutableStateOf(false) }
    data class OpcionesModelMovil(var name: String, val description: String)
    val itemsModelMovil = modelos.map { OpcionesModelMovil(it.descripcion, "Descripción") }
    var selectedOptionModelMovil = remember { mutableStateOf(itemsModelMovil.firstOrNull() ?: OpcionesModelMovil("", "")) }
    // Observar cambios en la marca seleccionada y cargar modelos

    //Se usa LaunchedEffect  para observar cambios en la marca seleccionada selectedOptionMarcaMovil
    LaunchedEffect(key1 = selectedOptionMarcaMovil.value) {
        // Limpia la selección del modelo
        selectedOptionModelMovil.value = OpcionesModelMovil("", "")

        // Carga los nuevos modelos
        selectedOptionMarcaMovil.value.id?.let {
            viewModel.loadModelosxMarca(it)
        }
    }

    //VARIABLE PARA EL CONCESIONARIO MOVIL
    //val empresas by viewModel.modelos.observeAsState(initial = emptyList())
    val empresas by viewModel.empresas.observeAsState(initial = emptyList())
    var expandedConcesionarioMovil by remember { mutableStateOf(false) }
    data class OpcionesConcesionarioMovil(var name: String, val id: String)
    val itemsConcesionarioMovil = empresas.map { OpcionesConcesionarioMovil(it.nombre, it.id) }
    var selectedConcesionarioMovil = remember { mutableStateOf(itemsConcesionarioMovil.firstOrNull() ?: OpcionesConcesionarioMovil("", "")) }




    //VALIDACIÓN PARA NUMERO DE CELULAR
    var NumCell by rememberSaveable { mutableStateOf("") }
    var isErrorNumCell by rememberSaveable { mutableStateOf(false) }
    val charLimitNumCell = 9
    fun validateNumCell(text2: String) {
        isErrorNumCell = NumCell.length > charLimitNumCell
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

                            Spacer(modifier = Modifier.height(5.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                //verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 10.dp,
                                            bottom = 10.dp,
                                            start = 30.dp,
                                            end = 30.dp
                                        )
                                        .wrapContentSize(align = Alignment.Center),
                                    text = "Ingresa los datos del equipo a reportar",
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
                                    painter = painterResource(id = R.drawable.smartphone),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(100.dp)
                                )
                                Spacer(modifier = Modifier.height(20.dp))

                                //IMEI
                                OutlinedTextField(
                                    value = imei,
                                    onValueChange = { input ->
                                        if (input.all { it.isDigit() } && input.length <= charLimitImei) {
                                            imei = input
                                        }
                                        isError = imei.length < charLimitImei
                                    },
                                    label = { Text("IMEI *") },
                                    placeholder = { Text("IMEI") },
                                    supportingText = {
                                        val remainingCharactersImei = charLimitImei - imei.length
                                        if (!imei.isEmpty() && remainingCharactersImei > 0) {
                                            Text(
                                                modifier = Modifier.fillMaxWidth(),
                                                text = "Faltan $remainingCharactersImei caracteres",
                                                textAlign = TextAlign.End,
                                            )
                                        }
                                    },
                                    isError = isError,
                                    keyboardActions = KeyboardActions { validateImei(imei) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .semantics {
                                            //if (isError2) error(errorMessage2)
                                        },
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                //DROPDOWN MARCA MOVIL
                                ExposedDropdownMenuBox(
                                    modifier=Modifier.fillMaxWidth(),
                                    expanded = expandedMarcaMovil,
                                    onExpandedChange = { expandedMarcaMovil = !expandedMarcaMovil },
                                ) {
                                    OutlinedTextField(
                                        // The `menuAnchor` modifier must be passed to the text field for correctness.
                                        modifier = Modifier
                                            .menuAnchor()
                                            .fillMaxWidth(),
                                        readOnly = true,
                                        value = selectedOptionMarcaMovil.value.name,
                                        onValueChange = {},
                                        label = { Text("Marca móvil *") },
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMarcaMovil) },
                                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expandedMarcaMovil,
                                        onDismissRequest = { expandedMarcaMovil = false },
                                    ) {
                                        itemsMarcaMovil.forEach { selectionOption ->
                                            DropdownMenuItem(
                                                text = { Text(selectionOption.name) },
                                                onClick = {
                                                    selectedOptionMarcaMovil.value = selectionOption
                                                    expandedMarcaMovil = false
                                                },
                                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))

                                ///DROP DOWN MODELO MÓVIL
                                ExposedDropdownMenuBox(
                                    modifier=Modifier.fillMaxWidth(),
                                    expanded = expandedModelMovil,
                                    onExpandedChange = { expandedModelMovil = !expandedModelMovil },
                                ) {
                                    OutlinedTextField(
                                        // The `menuAnchor` modifier must be passed to the text field for correctness.
                                        modifier = Modifier
                                            .menuAnchor()
                                            .fillMaxWidth(),
                                        readOnly = true,
                                        value = selectedOptionModelMovil.value.name,
                                        onValueChange = {},
                                        label = { Text("Modelo móvil *") },
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedModelMovil) },
                                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expandedModelMovil,
                                        onDismissRequest = { expandedModelMovil = false },
                                    ) {
                                        itemsModelMovil.forEach { selectionOption ->
                                            DropdownMenuItem(
                                                text = { Text(selectionOption.name) },
                                                onClick = {
                                                    selectedOptionModelMovil.value = selectionOption
                                                    expandedModelMovil = false
                                                },
                                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))

                                ///DROPDOWN CONCESIONARIO MOVIL
                                ExposedDropdownMenuBox(
                                    modifier=Modifier.fillMaxWidth(),
                                    expanded = expandedConcesionarioMovil,
                                    onExpandedChange = { expandedConcesionarioMovil = !expandedConcesionarioMovil },
                                ) {
                                    OutlinedTextField(
                                        // The `menuAnchor` modifier must be passed to the text field for correctness.
                                        modifier = Modifier
                                            .menuAnchor()
                                            .fillMaxWidth(),
                                        readOnly = true,
                                        value = selectedConcesionarioMovil.value.name,
                                        onValueChange = {},
                                        label = { Text("Concesionario movil *") },
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedConcesionarioMovil) },
                                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expandedConcesionarioMovil,
                                        onDismissRequest = { expandedConcesionarioMovil = false },
                                    ) {
                                        itemsConcesionarioMovil.forEach { selectionOption ->
                                            DropdownMenuItem(
                                                text = { Text(selectionOption.name) },
                                                onClick = {
                                                    selectedConcesionarioMovil.value = selectionOption
                                                    expandedConcesionarioMovil = false
                                                },
                                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                            )
                                        }
                                    }
                                }
                                ///
                                ///
                                Spacer(modifier = Modifier.height(10.dp))

                                ///NUMERO DE CELULAR UTILIZADO EN EL EQUIPO
                                OutlinedTextField(
                                    value = NumCell,
                                    onValueChange = { input ->
                                        if (input.all { it.isDigit() } && input.length <= charLimitNumCell && (input.isEmpty() || input.startsWith('9'))) {
                                            NumCell = input
                                        }
                                        isErrorNumCell = NumCell.length < charLimitNumCell || (!NumCell.isEmpty() && !NumCell.startsWith('9'))
                                    },
                                    label = { Text("Número de Móvil utilizado en el equipo *") },
                                    placeholder = { Text("Número de Móvil utilizado en el equipo") },
                                    supportingText = {
                                        val remainingCharactersNumCell = charLimitNumCell - NumCell.length
                                        if (!NumCell.isEmpty() && remainingCharactersNumCell > 0) {
                                            Text(
                                                modifier = Modifier.fillMaxWidth(),
                                                text = "Faltan $remainingCharactersNumCell caracteres",
                                                textAlign = TextAlign.End,
                                            )
                                        }
                                    },
                                    isError = isErrorNumCell,
                                    keyboardActions = KeyboardActions { validateNumCell(NumCell) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .semantics {
                                            //if (isError2) error(errorMessage2)
                                        },
                                )
                                ///

                                Spacer(modifier = Modifier.height(30.dp))
                                Row() {
                                    ElevatedButton(
                                        modifier = Modifier.weight(1f),

                                        onClick = {
                                            navHostController.navigate( AppScreens.Formulario3Screen.route)
                                            /*
                                            if (imei.isBlank() ||
                                                apellidos.value.isBlank() ||
                                                tipoDocumento.value.isBlank() ||
                                                numeroDocumento.value.isBlank() ||
                                                correo.value.isBlank()
                                                // Mostrar un mensaje al usuario indicando que todos los campos son obligatorios
                                            ) {

                                            } else {

                                                // Procesar los datos del formulario
                                            }
                                                  /*TODO*/

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
