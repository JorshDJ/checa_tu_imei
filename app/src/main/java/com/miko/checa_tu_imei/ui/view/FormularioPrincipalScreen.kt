package com.miko.checa_tu_imei.ui.view

//import android.graphics.Color
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
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
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
fun FormularioPrincipalScreen(
    //Parámetro NavHostController
    navHostController: NavHostController,
    viewModel: ImeiViewModel,

    //Parametro Switch
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){
    FormularioPrincipal(navHostController = navHostController,viewModel=viewModel, isDarkTheme = isDarkTheme, icon = icon)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioPrincipal(
    //Parámetro NavHostController
    navHostController: NavHostController,

    viewModel: ImeiViewModel,
    //Parametro Switch
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){

    //variable para el scroll
    val scrollState = rememberScrollState()

    //Variables para el drawer
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    data class IconItem(val icon: ImageVector, val description: String)
    val itemsOpciones = listOf(
        IconItem(Icons.Default.Home, "Home"),
        IconItem(Icons.Default.Info, "Preguntas frecuentes"),
        IconItem(Icons.Default.Search, "Empresas Renteseg"),
        IconItem(Icons.Default.List, "Formulario")
    )
    val selectedItem = remember { mutableStateOf(itemsOpciones[3]) }



    //variable para el dropdown TipoDocumento
    var expandedTipoDocumento by remember { mutableStateOf(false) }
    data class OpcionesTipoDocumento(var id: String, var nombreDoc: String, val charLimit:Int)
    val itemsTipoDocumento = listOf(
        OpcionesTipoDocumento("1", "DNI",8),
        OpcionesTipoDocumento("2", "Passaporte",12),
        OpcionesTipoDocumento("3", "Carnet de extranjería",12),
        OpcionesTipoDocumento("4", "RUC",11),
        OpcionesTipoDocumento("5", "PTP",9),
    )
    var selectedOptionTipoDocumento = remember { mutableStateOf(itemsTipoDocumento[0]) }

    //para validacion de Número de documento
    //var tipoDoc by rememberSaveable { mutableStateOf("") }
    var isErrorTipoDoc by rememberSaveable { mutableStateOf(false) }
    //val charLimitTipoDoc = 16
    fun validateTipoDoc(text2: String) {
        isErrorTipoDoc = selectedOptionTipoDocumento.value.nombreDoc.length > selectedOptionTipoDocumento.value.charLimit
    }

    MaterialTheme() {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Spacer(modifier = Modifier.height(20.dp))

                        //OPCIONES DEL DRAWER
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 30.dp, top = 10.dp),
                                text = "Modo Oscuro"
                            )
                            Switch(
                                modifier = Modifier.semantics { contentDescription = "Demo with icon" }.padding(end = 30.dp),
                                checked = isDarkTheme.value,
                                onCheckedChange = { isDarkTheme.value = it },
                                thumbContent = icon
                            )
                        }
                        Divider(modifier = Modifier.padding(start = 20.dp, end = 20.dp))
                        Spacer(modifier = Modifier.height(20.dp))

                        itemsOpciones.forEach { item ->
                            NavigationDrawerItem(
                                //icon = { Icon(item, contentDescription = null) },
                                icon={ Icon(imageVector = item.icon, contentDescription = null) },
                                label = { Text(item.description) },
                                //selected = item == selectedItem.value,
                                selected = item == selectedItem.value,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    selectedItem.value = item
                                    //Navegación en el drawer
                                    if (selectedItem.value==itemsOpciones[0]){
                                        navHostController.navigate(route = AppScreens.HomeScreen.route)
                                    }
                                    if (selectedItem.value==itemsOpciones[2]){
                                        navHostController.navigate(route = AppScreens.EmpresasRentesegScreen.route)
                                    }

                                    if (selectedItem.value==itemsOpciones[3]){
                                        navHostController.navigate(route = AppScreens.FormularioPrincipalScreen.route)
                                    }
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                        }//FIN OPCIONES DEL DRAWER
                    }
                },
                content = {
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
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(
                                            imageVector = Icons.Filled.Menu,
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
                                                        text = "Preguntas frecuentes : ",

                                                        fontWeight = FontWeight.Bold,
                                                        color = MaterialTheme.colorScheme.onPrimary,
                                                        style = TextStyle(fontSize = 20.sp)
                                                    )
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(20.dp))
                                        Spacer(modifier = Modifier.height(30.dp))

                                        Image(
                                            painter = painterResource(id = R.drawable.smartphone),
                                            contentDescription = "Logo",
                                            modifier = Modifier.size(150.dp)
                                        )
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

                                                            text = "Si el IMEI de su teléfono no figura en esta base de datos a pesar de haberlo reportado por " +
                                                                    "sustracción o perdida ante tu empresa operadora, o presentas algún incoveniente con el desbloqueo de tu"+
                                                                    "teléfono luego de la recuperación del mismo escríbenos a reportaimei@osiptel.gob.pe o completa el formulario "+
                                                                    "que encontrarás en el siguiente enlace."
                                                            ,
                                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                            textAlign = TextAlign.Justify
                                                        )
                                                        //Spacer(modifier = Modifier.height(20.dp))

                                                        Row() {
                                                            ElevatedButton(
                                                                modifier = Modifier
                                                                    .weight(1f)
                                                                    .padding(16.dp),
                                                                onClick = { navHostController.navigate(
                                                                    AppScreens.Formulario1Screen.route)},
                                                                colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.primary)
                                                            ) {

                                                                Icon(
                                                                    imageVector = Icons.Filled.Info,
                                                                    contentDescription = "Icono Info",
                                                                    modifier = Modifier.align(Alignment.CenterVertically)
                                                                )

                                                                Text(
                                                                    modifier = Modifier.padding(start = 10.dp),
                                                                    text = "Formulario",
                                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                                    style = TextStyle(fontSize = 18.sp)
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(30.dp))
                                        Row() {
                                            ElevatedButton(
                                                modifier = Modifier.weight(1f),
                                                onClick = { navHostController.popBackStack() },
                                                colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.primary)
                                            ) {

                                                Icon(
                                                    imageVector = Icons.Filled.ArrowBack,
                                                    contentDescription = "Favorite Icon",
                                                    modifier = Modifier.align(Alignment.CenterVertically)
                                                )

                                                Text(
                                                    modifier = Modifier.padding(start = 10.dp),
                                                    text = "Regresar",
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
            )
        }
    }
}
