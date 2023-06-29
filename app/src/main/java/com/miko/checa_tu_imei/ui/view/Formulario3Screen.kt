package com.miko.checa_tu_imei.ui.view

//import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
fun Formulario3Screen(
    //Parámetro NavHostController
    navHostController: NavHostController,
    viewModel: ImeiViewModel,

    //Parametro Switch
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){
    Formulario3(navHostController = navHostController,viewModel=viewModel, isDarkTheme = isDarkTheme, icon = icon)
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario3(
    //Parámetro NavHostController
    navHostController: NavHostController,

    viewModel: ImeiViewModel,
    //Parametro Switch
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){

    //variable para el scroll de la columna principal
    val scrollState = rememberScrollState()

    //para validacion de CodigoBloqueo
    var codigoBloqueo by rememberSaveable { mutableStateOf("") }
    var isErrorCodigoBloqueo by rememberSaveable { mutableStateOf(false) }
    val charLimitCodigoBloqueo = 20
    fun validateCodigoBloqueo(CodigoBloqueo: String) {
        isErrorCodigoBloqueo = CodigoBloqueo.length > charLimitCodigoBloqueo
    }

    //Variable para el dropdown MotivoConsulta
    val motivoConsulta by viewModel.problemasDetectados.observeAsState(initial = emptyList())
    var expandedMotivoConsulta by remember { mutableStateOf(false) }
    data class OpcionesMotivoConsulta(var id : String , val description: String)
    val itemsMotivoConsulta =  motivoConsulta.map { OpcionesMotivoConsulta(it.id, it.descripcion) }
    var selectedMotivoConsulta = remember { mutableStateOf(itemsMotivoConsulta.firstOrNull() ?: OpcionesMotivoConsulta("", "")) }

    //validacion para número de teléfono contacto
    var NumCellContact by rememberSaveable { mutableStateOf("") }
    var isErrorNumCellContact by rememberSaveable { mutableStateOf(false) }
    val charLimitNumCellContact = 9
    fun validateNumCellContact(NumCell: String) {
        isErrorNumCellContact = NumCell.length > charLimitNumCellContact
    }

    @Composable
    fun colorito(selectionOption: OpcionesMotivoConsulta): Color {
        return if (selectionOption.id.toInt() % 2 == 0) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.error
        }
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
                            Spacer(modifier = Modifier.height(10.dp))

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
                                    text = "Datos complementarios",
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

                                //CODIGO DE BLOQUEO
                                OutlinedTextField(
                                    value = codigoBloqueo,
                                    onValueChange = { input ->
                                        if (input.all { it.isDigit() } && input.length <= charLimitCodigoBloqueo) {
                                            codigoBloqueo = input
                                        }
                                        isErrorCodigoBloqueo = codigoBloqueo.length < charLimitCodigoBloqueo
                                    },
                                    label = { Text("Código de bloqueo *") },
                                    placeholder = { Text("Código de bloqueo") },
                                    supportingText = {
                                        val remainingCharactersCodigoBloqueo = charLimitCodigoBloqueo - codigoBloqueo.length
                                        if (!codigoBloqueo.isEmpty() && remainingCharactersCodigoBloqueo > 0) {
                                            Text(
                                                modifier = Modifier.fillMaxWidth(),
                                                text = "Faltan $remainingCharactersCodigoBloqueo caracteres",
                                                textAlign = TextAlign.End,
                                            )
                                        }
                                    },
                                    isError = isErrorCodigoBloqueo,
                                    keyboardActions = KeyboardActions { validateCodigoBloqueo(codigoBloqueo) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .semantics {
                                            //if (isErrorCodigoBloqueo) error(errorMessageCodigoBloqueo)
                                        },
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                ///dropdown MotivoConsulta
                                ///
                                ExposedDropdownMenuBox(
                                    modifier=Modifier.fillMaxWidth(),
                                    expanded = expandedMotivoConsulta,
                                    onExpandedChange = { expandedMotivoConsulta = !expandedMotivoConsulta },
                                ) {
                                    OutlinedTextField(
                                        // The `menuAnchor` modifier must be passed to the text field for correctness.
                                        modifier = Modifier
                                            .menuAnchor()
                                            .fillMaxWidth(),
                                        readOnly = true,
                                        value = selectedMotivoConsulta.value.description,
                                        onValueChange = {},
                                        label = { Text("Motivo Consulta *") },
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMotivoConsulta) },
                                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expandedMotivoConsulta,
                                        onDismissRequest = { expandedMotivoConsulta = false },
                                    ) {
                                        itemsMotivoConsulta.forEach { selectionOption ->

                                            DropdownMenuItem(
                                                text = { Text(modifier = Modifier.padding(10.dp),  text= selectionOption.description) },
                                                colors = MenuDefaults.itemColors(leadingIconColor = Color.Red),
                                                onClick = {
                                                    selectedMotivoConsulta.value = selectionOption
                                                    expandedMotivoConsulta = false
                                                },
                                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                            )

                                            //OPCIONES CON COLORES
                                            /*
                                            DropdownMenuItem(
                                                onClick = {
                                                    selectedMotivoConsulta.value = selectionOption
                                                    expandedMotivoConsulta = false
                                                },
                                                text = {
                                                       Box(
                                                           modifier = Modifier
                                                               .fillMaxWidth()
                                                               .wrapContentSize()
                                                               .background(
                                                                   color = colorito(selectionOption = selectionOption)
                                                               ),
                                                       ) {
                                                           Text(text = selectionOption.description, modifier = Modifier.fillMaxWidth().padding(10.dp),color = MaterialTheme.colorScheme.onPrimary)
                                                       }
                                                       /*TODO*/ 
                                                       },
                                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                            
                                            )
                                             */
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(modifier = Modifier.fillMaxWidth()) {
                                    DatePickerExample()
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                TimePickerExample()

                                Spacer(modifier = Modifier.height(10.dp))

                                ///NUMERO DE TELEFONO DE CONTACTO
                                OutlinedTextField(
                                    value = NumCellContact,
                                    onValueChange = { input ->
                                        if (input.all { it.isDigit() } && input.length <= charLimitNumCellContact && (input.isEmpty() || input.startsWith('9'))) {
                                            NumCellContact = input
                                        }
                                        isErrorNumCellContact = NumCellContact.length < charLimitNumCellContact || (!NumCellContact.isEmpty() && !NumCellContact.startsWith('9'))
                                    },
                                    label = { Text("Número de teléfono contacto *") },
                                    placeholder = { Text("Número de teléfono contacto") },
                                    supportingText = {
                                        val remainingCharactersNumCellContact = charLimitNumCellContact - NumCellContact.length
                                        if (!NumCellContact.isEmpty() && remainingCharactersNumCellContact > 0) {
                                            Text(
                                                modifier = Modifier.fillMaxWidth(),
                                                text = "Faltan $remainingCharactersNumCellContact caracteres",
                                                textAlign = TextAlign.End,
                                            )
                                        }
                                    },
                                    isError = isErrorNumCellContact,
                                    keyboardActions = KeyboardActions { validateNumCellContact(NumCellContact) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .semantics {
                                            //if (isErrorNumCellContact) error(errorMessageNumCellContact)
                                        },
                                )

                                Spacer(modifier = Modifier.height(30.dp))

                                Row() {
                                    ElevatedButton(
                                        modifier = Modifier.weight(1f),

                                        onClick = {
                                            navHostController.navigate(AppScreens.Formulario4Screen.route)
                                            /*
                                            if (
                                                imei.isBlank() ||
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

/*
@Composable
fun colorito(selectionOption: OpcionesMotivoConsulta): Color {
    return if (selectionOption.id.toInt() % 2 == 0) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.error
    }
}

 */
