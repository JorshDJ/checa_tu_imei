package com.miko.checa_tu_imei.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background

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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer

import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription

import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.recreate
import androidx.navigation.NavHostController
import com.miko.checa_tu_imei.R
import com.miko.checa_tu_imei.ui.navigation.AppScreens
import com.miko.checa_tu_imei.ui.theme.topAppBar2
import com.miko.checa_tu_imei.ui.view.components.CustomDrawerContent

import com.miko.checa_tu_imei.ui.viewmodel.ImeiViewModel
import com.miko.checa_tu_imei.util.Util
import com.miko.checa_tu_imei.util.Util.setLocale

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
    //var context = LocalContext.current
    //val isEnglish = remember { mutableStateOf(false) }
    var context = LocalContext.current
    var isEnglish by remember { mutableStateOf(false) }

    val txt_inicio by remember { mutableStateOf(R.string.drawer_inicio) }

    ///

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
    val selectedItemDrawer =0

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
                    CustomDrawerContent(
                        isDarkTheme = isDarkTheme,
                        selectedItem = selectedItemDrawer,
                        drawerState = drawerState,
                        navHostController = navHostController,
                        icon = icon,
                    )
                },
                content = {

                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                ),
                                title = { AppBarTitle(stringResource(R.string.consultar_imei)) },
                                navigationIcon = { AppBarNavigationIcon { scope.launch { drawerState.open() } } },
                                actions = { AppBarAction(navHostController) }
                            )
                        },
                        content = { innerPadding ->
                            Column(modifier = Modifier.padding(innerPadding)) {

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

                                        CardRow(stringResource(R.string.aplicativo_permitir_consulta))

                                        Spacer(modifier = Modifier.height(24.dp))

                                        ImeiTextField(
                                            imei = imei,
                                            onImeiChange = { imei = it },
                                            isErrorImei = isErrorImei,
                                            onImeiErrorChange = { isErrorImei = it },
                                            charLimitImei = charLimitImei
                                        )

                                        Spacer(modifier = Modifier.height(24.dp))

                                        CheckboxRow(stringResource(R.string.no_soy_robot))

                                        Spacer(modifier = Modifier.height(30.dp))

                                        ButtonRow(stringResource(R.string.verificar), { navHostController.navigate(route = AppScreens.RespuestaConsultaScreen.route +imei)})

                                        Spacer(modifier = Modifier.height(30.dp))
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


// Extracted AppBarTitle
@Composable
fun AppBarTitle(text: String) {
    Text(
        text = text,
        maxLines = 1,
        color = MaterialTheme.colorScheme.onPrimary,
        fontWeight = FontWeight.Bold
    )
}

// Extracted AppBarNavigationIcon
@Composable
fun AppBarNavigationIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Localized description",
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
@Composable
fun AppBarAction(navHostController: NavHostController) {
    IconButton(onClick = { navHostController.navigate(AppScreens.PreguntasFrecuentesScreen.route) }) {
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = "Localized description",
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
fun CardRow(text: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)) {
                Text(
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                    text = text,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImeiTextField(
    imei: String,
    onImeiChange: (String) -> Unit,
    isErrorImei: Boolean,
    onImeiErrorChange: (Boolean) -> Unit,
    charLimitImei: Int
) {
    //val charLimitImei = 15

    OutlinedTextField(
        value = imei,
        onValueChange = { input ->
            if (input.all { it.isDigit() } && input.length <= charLimitImei) {
                onImeiChange(input)
            }
            onImeiErrorChange(input.length < charLimitImei)
        },
        label = { Text(stringResource(R.string.consultar_imei)) },
        placeholder = { Text(stringResource(R.string.consultar_imei)) },
        supportingText = {
            val remainingCharactersImei = charLimitImei - imei.length
            if (imei.isNotEmpty() && remainingCharactersImei > 0) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.faltan) +" $remainingCharactersImei "+ stringResource(R.string.caracteres),
                    textAlign = TextAlign.End,
                )
            }
        },
        isError = isErrorImei,
        trailingIcon = {
            if (isErrorImei)
                Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colorScheme.error)
        },
        keyboardActions = KeyboardActions { /* Validación del IMEI aquí */ },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CheckboxRow(text: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(10.dp)) {
                    val checkedState = remember { mutableStateOf(true) }

                    Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it }
                    )
                    Text(
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                        text = text,
                        //color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonRow(text: String, onClick: () -> Unit) {
    Row() {
        ElevatedButton(
            modifier = Modifier.weight(1f),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            /*
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "Favorite Icon",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
             */
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onPrimary,
                style = TextStyle(fontSize = 18.sp)
            )
        }
    }
}





