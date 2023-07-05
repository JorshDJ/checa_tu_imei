package com.miko.checa_tu_imei.ui.view
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.miko.checa_tu_imei.R
import com.miko.checa_tu_imei.ui.view.components.CustomDrawerContent
import com.miko.checa_tu_imei.ui.viewmodel.ImeiViewModel
import kotlinx.coroutines.launch


///Datos Preguntas Frecuentes
data class PreguntasFrecuentes(val id:String,val pregunta: String, val respuesta: String)

@Composable
fun PreguntasFrecuentesScreen(
    navHostController: NavHostController,
    //Variables Switch
    viewModel: ImeiViewModel,
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){
    PreguntasFrecuentes(navHostController = navHostController, viewModel = viewModel, isDarkTheme = isDarkTheme, icon = icon)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreguntasFrecuentes(
    navHostController: NavHostController,

    //Variables Switch
    viewModel: ImeiViewModel,
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){
    //Variables para el drawer
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedItemDrawer =1

    //Variables para preguntas frecuentes, llama a la api y carga todas las preguntas frecuentes
    viewModel.loadPreguntasFrecuentes()
    val preguntasFrecuentesApi by viewModel.preguntasFrecuentes.observeAsState(initial = emptyList())
    val PreguntasFrecuentesItem = preguntasFrecuentesApi.map { PreguntasFrecuentes(it.id,it.pregunta,it.respuesta) }



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
                                title = { AppBarTitle(stringResource(R.string.preguntas_frecuentes)) },
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
                                        Spacer(modifier = Modifier.height(20.dp))

                                        ImagePrincipalPreguntasFrecuentes()

                                        Spacer(modifier = Modifier.height(20.dp))

                                        ListaPreguntasFrecuentes(datos= PreguntasFrecuentesItem)

                                        Spacer(modifier = Modifier.height(20.dp))

                                        ButtonRow(stringResource(R.string.regresar), { navHostController.popBackStack()})

                                        Spacer(modifier = Modifier.height(20.dp))
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
fun textoPrincipalPreguntasFrecuentes(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Text(
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
            text = stringResource(R.string.preguntas_frecuentes),

            fontWeight = FontWeight.Bold,
            color = Color(android.graphics.Color.parseColor("#599BCC")),
            style = TextStyle(fontSize = 23.sp)

        )
    }
}


@Composable
fun ImagePrincipalPreguntasFrecuentes(){
    Row(modifier = Modifier.fillMaxWidth(), Arrangement.Center) {
        Image(
            painter = painterResource(id = R.drawable.solicitud),
            contentDescription = stringResource(R.string.preguntas_frecuentes),
            modifier = Modifier.size(70.dp)
        )

    }
}

@Composable
fun ListaPreguntasFrecuentes(
    datos: List<PreguntasFrecuentes>
){
    LazyColumn() {
        items(items = datos){ dato ->
            CardGeneral(pregunta = dato.pregunta, respuesta = dato.respuesta)
        }
    }
}

@Composable
private fun CardGeneral(
    pregunta: String,
    respuesta: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        CardContent(pregunta,respuesta)
    }
}


@Composable
private fun CardContent(pregunta: String,respuesta: String) {

    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            //Text(text = "Hello, ")
            Text(
                text = pregunta,
                /*
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                 */
                style = TextStyle(fontSize = 20.sp),
                fontWeight = FontWeight.Bold,
            )
            if (expanded) {
                Text(
                    text = (respuesta),
                )
            }
        }

        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                //imageVector = if (expanded) Icons.Filled.ArrowBack else Icons.Filled.ArrowDropDown,
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}