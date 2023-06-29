package com.miko.checa_tu_imei.ui.view


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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

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
import com.miko.checa_tu_imei.ui.viewmodel.ImeiViewModel
import kotlinx.coroutines.launch


data class Empresas(val name: String, val description: String)

val items = listOf(
    Empresas("Claro", "www.claro.com.pe"),
    Empresas("Movistar", "www.movistar.com.pe"),
)
@Composable
fun EmpresasRentesegScreen(
    //Parámetro NavHostController
    navHostController: NavHostController,
    viewModel: ImeiViewModel,
    //Parametro Switch
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){
    EmpresasRenteseg(navHostController = navHostController,viewModel=viewModel, isDarkTheme = isDarkTheme, icon = icon)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpresasRenteseg(
    //Parámetro NavHostController
    navHostController: NavHostController,
    //Parametro Switch
    viewModel: ImeiViewModel,
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){
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
    val selectedItem = remember { mutableStateOf(itemsOpciones[2]) }


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

                        //OPCIONES DRAWER
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
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(
                                        "Consulta IMEI",
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
                                ElevatedCard(modifier= Modifier.fillMaxWidth()) {
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp),

                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Spacer(modifier = Modifier.height(20.dp))
                                        //SEARCH
                                        AutoCompleteTextField(items = items)
                                        //
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteTextField(items: List<Empresas>) {
    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            modifier=Modifier.fillMaxWidth(),
            value = query,
            onValueChange = { newQuery -> query = newQuery },
            label = { Text("Buscar") },
        )
        LazyColumn {
            val results = items.filter { it.name.contains(query, ignoreCase = true) }
            items(results) { item ->
                Text(
                    text = "${item.name} - ${item.description}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { query = item.name }
                        .padding(8.dp)
                )
            }
        }
    }
}


