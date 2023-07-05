package com.miko.checa_tu_imei.ui.view.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.miko.checa_tu_imei.R
import com.miko.checa_tu_imei.ui.theme.topAppBar2


import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import com.miko.checa_tu_imei.ui.navigation.AppScreens
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDrawerContent(
    drawerState: DrawerState,
    isDarkTheme: MutableState<Boolean>,
    //itemsOpciones: List<IconItem>,
    //selectedItem: MutableState<IconItem>,
    navHostController: NavHostController,
    icon: @Composable() (() -> Unit)?,
    selectedItem : Int
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    data class IconItem(val icon: ImageVector, val description: String)
    val itemsOpciones = listOf(
        IconItem(Icons.Default.Home, stringResource(R.string.drawer_inicio)),
        IconItem(Icons.Default.Info, stringResource(R.string.drawer_preguntas_frecuentes)),
        IconItem(Icons.Default.Search, stringResource(R.string.drawer_empresas_renteseg)),
        IconItem(Icons.Default.List, stringResource(R.string.drawer_formulario))
    )
    val selectedItem = remember { mutableStateOf(itemsOpciones[selectedItem]) }
    //val selectedItem = selectedItem.value


    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(20.dp))
        // Switch para modo oscuro
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(start = 30.dp, top = 10.dp),
                text = stringResource(R.string.modo_oscuro),
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
                    uncheckedThumbColor = topAppBar2,
                    uncheckedBorderColor = MaterialTheme.colorScheme.primary
                )
            )
        }
        Divider(modifier = Modifier.padding(start = 20.dp, end = 20.dp))
        Spacer(modifier = Modifier.height(20.dp))

        itemsOpciones.forEach { item ->
            NavigationDrawerItem(
                label = { Text(item.description) },
                selected = item == selectedItem.value,
                onClick = {
                    //scope.launch { drawerState.close() }
                    selectedItem.value = item
                    //Navegación en el drawer
                    when (selectedItem.value) {
                        itemsOpciones[0] -> navHostController.navigate(route = AppScreens.HomeScreen.route)
                        itemsOpciones[1] -> navHostController.navigate(route = AppScreens.PreguntasFrecuentesScreen.route)
                        itemsOpciones[2] -> navHostController.navigate(route = AppScreens.EmpresasRentesegScreen.route)
                        itemsOpciones[3] -> navHostController.navigate(route = AppScreens.FormularioPrincipalScreen.route)
                    }
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                icon = { Icon(imageVector = item.icon, contentDescription = null) },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}





