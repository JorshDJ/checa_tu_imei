package com.miko.checa_tu_imei.ui.view

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

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults.colors

import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription

import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.miko.checa_tu_imei.R
import com.miko.checa_tu_imei.ui.navigation.AppScreens
import com.miko.checa_tu_imei.ui.theme.topAppBar2
import com.miko.checa_tu_imei.ui.viewmodel.ImeiViewModel
import kotlinx.coroutines.launch




@Composable
fun HomeSreen(
    //Parámetro NavHostController
    navHostController: NavHostController,
    viewModel: ImeiViewModel,
    //Parametro Switch
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){
    Home(navHostController = navHostController,viewModel=viewModel, isDarkTheme = isDarkTheme, icon = icon)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    //Parámetro NavHostController
    navHostController: NavHostController,
    //Parametro Switch
    viewModel: ImeiViewModel,
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){

    //Variables y fx para el formulario Consulta IMEI
    var imei by remember { mutableStateOf("") }
    //var inputImei by remember { mutableStateOf("") }
    var isErrorImei by rememberSaveable { mutableStateOf(false) }
    val charLimitImei = 15
    fun validateImei(text2: String) {
        isErrorImei = text2.length > charLimitImei
    }

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
    val selectedItem = remember { mutableStateOf(itemsOpciones[0]) }




    MaterialTheme() {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondary
            )
        ) {
            ModalNavigationDrawer(
                //scrimColor = MaterialTheme.colorScheme.primaryContainer,
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet (
                        //modifier= Modifier.padding(5.dp),
                        //drawerContainerColor = MaterialTheme.colorScheme.primary,
                        //drawerContentColor = MaterialTheme.colorScheme.primaryContainer,

                            ){
                        Spacer(modifier = Modifier.height(20.dp))


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 30.dp, top = 10.dp),
                                text = "Modo Oscuro",
                                color = MaterialTheme.colorScheme.tertiaryContainer
                            )
                            Switch(
                                modifier = Modifier
                                    .semantics { contentDescription = "Demo with icon" }
                                    .padding(end = 30.dp),
                                checked = isDarkTheme.value,
                                onCheckedChange = { isDarkTheme.value = it },
                                thumbContent = icon,
                                colors = SwitchDefaults.colors(
                                    uncheckedIconColor= MaterialTheme.colorScheme.tertiary,
                                    //uncheckedTrackColor=MaterialTheme.colorScheme.tertiary,
                                    uncheckedThumbColor = topAppBar2,

                                    uncheckedBorderColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                        Divider(modifier = Modifier.padding(start = 20.dp, end = 20.dp))
                        Spacer(modifier = Modifier.height(20.dp))

                        itemsOpciones.forEach { item ->
                            NavigationDrawerItem(
                                colors= colors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimary
                                ),
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
                        }
                    }
                },
                content = {
                    Scaffold(
                        contentColor = MaterialTheme.colorScheme.primaryContainer,//parece que no hace nada
                        topBar = {
                            CenterAlignedTopAppBar(
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                ),

                                title = {
                                    Text(
                                        text="Consulta IMEI",
                                        maxLines = 1,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontWeight = FontWeight.Bold,
                                        //overflow = TextOverflow.Ellipsis
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(
                                            imageVector = Icons.Filled.Menu,
                                            contentDescription = "Localized description",
                                            tint = MaterialTheme.colorScheme.onPrimary,
                                        )
                                    }
                                },
                                actions = {

                                    IconButton(onClick = { /* doSomething() */ }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Info,
                                            contentDescription = "Localized description",
                                            tint = MaterialTheme.colorScheme.onPrimary,
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
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp),

                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Spacer(modifier = Modifier.height(30.dp))

                                        Image(
                                            painter = painterResource(id = R.drawable.logo_osiptel),
                                            contentDescription = "Logo",
                                            modifier = Modifier.size(200.dp)
                                        )
                                        Spacer(modifier = Modifier.height(24.dp))
                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            Card(
                                                modifier = Modifier.fillMaxWidth(),
                                            ) {
                                                Box(modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(color = MaterialTheme.colorScheme.primary)) {
                                                    Text(
                                                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                                                        text = "Este aplicativo permite consultar en línea el código IMEI ",
                                                        color = MaterialTheme.colorScheme.onPrimary
                                                    )
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(24.dp))

                                        OutlinedTextField(
                                            value = imei,
                                            onValueChange = { input ->
                                                if (input.all { it.isDigit() } && input.length <= charLimitImei) {
                                                    imei = input
                                                }
                                                isErrorImei = imei.length < charLimitImei
                                            },
                                            label = { Text("Consultar IMEI") },
                                            placeholder = { Text("Consultar IMEI") },
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
                                            isError = isErrorImei,
                                            keyboardActions = KeyboardActions { validateImei(imei) },
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .semantics {
                                                    //if (isErrorImei) error(errorMessageImei)
                                                },
                                        )
                                        Spacer(modifier = Modifier.height(24.dp))

                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            OutlinedCard(
                                                modifier = Modifier.fillMaxWidth(),
                                            ) {
                                                Box(modifier = Modifier
                                                    .fillMaxWidth()

                                                ) {
                                                    Row(modifier = Modifier.padding(10.dp)) {1
                                                        val checkedState = remember { mutableStateOf(true) }

                                                        Checkbox(
                                                            checked = checkedState.value,
                                                            onCheckedChange = { checkedState.value = it }
                                                        )
                                                        Text(
                                                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                                                            text = "No soy un robot ",
                                                            //color = Color.White
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(30.dp))
                                        Row() {
                                            ElevatedButton(
                                                modifier = Modifier.weight(1f),
                                                onClick = {
                                                    //var numDeConsulta : String = imei
                                                    navHostController.navigate(route = AppScreens.RespuestaConsultaScreen.route +imei)
                                                },
                                                colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.primary)) {
                                                /*
                                                Icon(
                                                    imageVector = Icons.Filled.Send,
                                                    contentDescription = "Favorite Icon",
                                                    modifier = Modifier.align(Alignment.CenterVertically)
                                                )
                                                 */
                                                Text(
                                                    text = "Verificar",
                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                    style = TextStyle(fontSize = 18.sp)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(30.dp))
                                        /*
                                        Divider(
                                            color= MaterialTheme.colorScheme.tertiary,
                                            //thickness = 5.dp
                                        )

                                         */
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



@Composable
fun ImageAsIcon(
    imageResId: Int,
    contentDescription: String,
    size: Dp,
    modifier: Modifier = Modifier
) {
    val image: Painter = painterResource(imageResId)

    Image(
        painter = image,
        contentDescription = contentDescription,
        modifier = modifier.size(size)

    )
}