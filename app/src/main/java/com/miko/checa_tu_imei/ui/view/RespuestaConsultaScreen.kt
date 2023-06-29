package com.miko.checa_tu_imei.ui.view

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
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
import androidx.compose.material3.IconToggleButton
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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
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
import com.miko.checa_tu_imei.domain.model.ImeiItem
import com.miko.checa_tu_imei.ui.viewmodel.ImeiViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@Composable
fun RespuestaConsultaScreen(
    //Parámetro NavHostController
    navHostController: NavHostController,
    viewModel: ImeiViewModel,
    //imei
    imeiConsulta:String,
    //Parametro Switch
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){
    RespuestaConsulta(navHostController = navHostController,viewModel=viewModel, isDarkTheme = isDarkTheme, icon = icon, imeiConsulta = imeiConsulta)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RespuestaConsulta(
    //Parámetro NavHostController
    navHostController: NavHostController,
    //imei
    imeiConsulta:String,
    viewModel: ImeiViewModel,
    //Parametro Switch
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,
){
    //Observa si hay datos en una consulta de imei
    val imei by viewModel.imei.observeAsState()
    //Carga los datos de la consulta
    imeiConsulta?.let {
        viewModel.loadImei(it)
    }
    //variable para el scroll
    val scrollState = rememberScrollState()

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
                                "Respuesta",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { /*scope.launch { drawerState.open() }*/ }) {
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
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 15.dp),

                                horizontalArrangement = Arrangement.End
                                //horizontalArrangement = Arrangement.SpaceBetween

                            ) {
                                /*
                                //SWITCH
                                Switch(
                                    modifier = Modifier.semantics { contentDescription = "Demo with icon" },
                                    checked = isDarkTheme.value,
                                    onCheckedChange = { isDarkTheme.value = it },
                                    thumbContent = icon
                                )
                                 */
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
                                    painter = painterResource(id = R.drawable.search),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(100.dp)
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
                                                modifier = Modifier.padding(top = 10.dp, bottom = 15.dp, start = 16.dp, end = 16.dp),
                                                text = "La respuesta a su consulta es :",
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                style = TextStyle(fontSize = 20.sp)
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(24.dp))

                                // MANEJAR PANTALLA SI ENCUENTRA IMEI O NO

                                imeiConsulta?.let {

                                    //ImeiCard(imei,it)
                                    //MyComponent(imei,it)
                                    //MyComponent2(imei,it)
                                }

                                if (imei == null){
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        OutlinedCard(
                                            modifier = Modifier.fillMaxWidth(),
                                        ) {
                                            Box(modifier = Modifier
                                                .fillMaxWidth()

                                            ) {
                                                Column() {
                                                    Row(modifier = Modifier.fillMaxWidth(), Arrangement.Start) {
                                                        Text(
                                                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                                                            text ="El número :",
                                                            //color = MaterialTheme.colorScheme.onPrimary
                                                        )
                                                    }
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.Center

                                                    ) {
                                                        imeiConsulta.let {
                                                            Text(
                                                                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                                                                text = it,

                                                                fontWeight = FontWeight.Bold,
                                                                //color = Color(android.graphics.Color.parseColor("#599BCC")),
                                                                //color = Color(android.graphics.Color.parseColor("#599BCC")),
                                                                style = TextStyle(fontSize = 23.sp)
                                                            )
                                                        }
                                                    }
                                                    Text(
                                                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                                                        text = "El IMEI no se encuentra asociado a ningún terminal reportado",
                                                        //color = MaterialTheme.colorScheme.onPrimary
                                                    )
                                                    Spacer(modifier = Modifier.height(20.dp))
                                                }
                                            }
                                        }
                                    }
                                }else {
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        OutlinedCard(
                                            modifier = Modifier.fillMaxWidth(),
                                        ) {
                                            Box(modifier = Modifier
                                                .fillMaxWidth()

                                            ) {
                                                //Se hace esto para colocar los datos del imei si ha encontrado algo, se maneja con el it
                                                imei?.let {
                                                    Column() {
                                                        //EL NÚMERO
                                                        Row(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            Arrangement.Start
                                                        ) {
                                                            Text(
                                                                modifier = Modifier.padding(top = 10.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
                                                                text ="El número :",
                                                                color = MaterialTheme.colorScheme.error
                                                            )
                                                        }
                                                        //NUMBER
                                                        Row(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Start

                                                        ) {
                                                            Text(
                                                                modifier = Modifier.padding(top = 0.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
                                                                text = it.numImei,
                                                                fontWeight = FontWeight.Bold,
                                                                color = MaterialTheme.colorScheme.error,
                                                                //color = Color(android.graphics.Color.parseColor("#599BCC")),
                                                                style = TextStyle(fontSize = 23.sp)
                                                            )
                                                        }

                                                        Text(
                                                            modifier = Modifier.padding(top = 0.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
                                                            text = "El IMEI se encuentra en lista negra.",
                                                            color = MaterialTheme.colorScheme.error
                                                        )
                                                        //ESTADO EQUIPO
                                                        Row(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Start

                                                        ) {
                                                            Text(
                                                                modifier = Modifier.padding(top = 10.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
                                                                text = "Estado equipo",
                                                                fontWeight = FontWeight.Bold,
                                                                color = MaterialTheme.colorScheme.error,
                                                                //color = Color(android.graphics.Color.parseColor("#599BCC")),
                                                                style = TextStyle(fontSize = 20.sp)
                                                            )
                                                        }
                                                        Text(
                                                            modifier = Modifier.padding(top = 0.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                                                            text = it.estadoEquipo,
                                                            color = MaterialTheme.colorScheme.error
                                                        )
                                                        //OPERADOR
                                                        Row(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Start
                                                        ) {
                                                            Text(
                                                                modifier = Modifier.padding(top = 0.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
                                                                text = "Operador",
                                                                fontWeight = FontWeight.Bold,
                                                                color = MaterialTheme.colorScheme.error,
                                                                style = TextStyle(fontSize = 20.sp)
                                                            )
                                                        }
                                                        Text(
                                                            modifier = Modifier.padding(top = 0.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
                                                            text = it.Operador,
                                                            color = MaterialTheme.colorScheme.error
                                                        )
                                                        //FECHA
                                                        Row(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Start
                                                        ) {
                                                            Text(
                                                                modifier = Modifier.padding(top = 10.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
                                                                text = "Fecha",
                                                                fontWeight = FontWeight.Bold,
                                                                color = MaterialTheme.colorScheme.error,
                                                                //color = Color(android.graphics.Color.parseColor("#599BCC")),
                                                                style = TextStyle(fontSize = 23.sp)
                                                            )
                                                        }
                                                        Text(
                                                            modifier = Modifier.padding(top = 0.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
                                                            text = it.fecha,
                                                            color = MaterialTheme.colorScheme.error
                                                        )
                                                        Spacer(modifier = Modifier.height(20.dp))
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                                ScreenCaptureButton(navHostController)
                                Spacer(modifier = Modifier.height(20.dp))

                            }
                        }
                    }
                }
            )
        }
    }
}



@Composable
fun ScreenCaptureButton(

    navHostController: NavHostController

) {
    val context = LocalContext.current
    val view = LocalView.current
    var showButton by remember { mutableStateOf(true) }

    if (showButton) {

        Row() {

            //BOTON PARA COMPARTIR
            ElevatedButton(
                modifier = Modifier.weight(1f),
                onClick = { showButton = false },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {

                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Compartir",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = "Compartir",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = TextStyle(fontSize = 18.sp)
                )
            }
            //BOTON PARA IR A INICIO
            ElevatedButton(
                modifier = Modifier.weight(1f),
                onClick = { navHostController.popBackStack() },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {

                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Ir a Inicio",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = "Ir a Inicio",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        }
    }

    LaunchedEffect(showButton) {
        if (!showButton) {
            delay(200)  // Espera para que la interfaz de usuario tenga tiempo de redibujarse sin el botón
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            val imageUri = saveBitmap(bitmap, context)
            imageUri?.let { shareImageUri(it, context) }
            showButton = true
        }
    }
}



fun saveBitmap(bitmap: Bitmap, context: Context): Uri? {
    val filename = "${System.currentTimeMillis()}.jpg"
    var fos: OutputStream? = null
    var imageUri: Uri? = null
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
        context.contentResolver?.also { resolver ->
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }
    } else {
        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
        val image = File(imagesDir, filename)
        fos = FileOutputStream(image)
        imageUri = Uri.fromFile(image)
    }

    fos?.use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        Toast.makeText(context, "Imagen guardada", Toast.LENGTH_SHORT).show()
    }
    return imageUri
}

fun shareImageUri(uri: Uri, context: Context) {
    val shareIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, uri)
        type = "image/jpeg"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Enviar a"))
}

////////////////////////////

@Composable
fun ImeiCard(imei:  ImeiItem?, imeiConsulta: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()

            ) {
                imei?.let {
                    // Se ha encontrado un IMEI
                    //IMEICardContent(imei = it, color = MaterialTheme.colors.error)
                    IMEICardContent(imei = it, color = MaterialTheme.colorScheme.error)
                } ?: run {
                    // IMEI no encontrado
                    IMEINotFoundCardContent(imeiConsulta = imeiConsulta)
                }
            }
        }
    }
}

@Composable
fun IMEICardContent(imei:  ImeiItem, color: Color) {
    Column() {
        ImeiRow(label = "El número :", value = imei.numImei, color = color)
        ImeiRow(label = "Estado equipo", value = imei.estadoEquipo, color = color)
        ImeiRow(label = "Operador", value = imei.Operador, color = color)
        ImeiRow(label = "Fecha", value = imei.fecha, color = color)
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun IMEINotFoundCardContent(imeiConsulta: String) {
    Column() {
        TextRow(label = "El número :", value = imeiConsulta)
        TextRow(label = "El IMEI no se encuentra asociado a ningún terminal reportado")
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun ImeiRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = label,
            fontWeight = FontWeight.Bold,
            color = color,
            style = TextStyle(fontSize = 20.sp)
        )
    }
    Text(
        modifier = Modifier.padding(10.dp),
        text = value,
        color = color
    )
}

@Composable
fun TextRow(label: String, value: String? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        Arrangement.Start
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = label
        )
    }
    value?.let {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = it,
                fontWeight = FontWeight.Bold,
                style = TextStyle(fontSize = 23.sp)
            )
        }
    }
}

//////////////////Para captura
@Composable
fun MyComponent(
    imei: ImeiItem?,
    imeiConsulta: String

) {
    val context = LocalContext.current
    val view = LocalView.current
    var composableBounds by remember { mutableStateOf<Rect?>(null) }

    Box(
        modifier = Modifier
            .onGloballyPositioned {
                composableBounds = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    it.boundsInWindow()
                } else {
                    it.boundsInRoot()
                }
            }
    ) {

        ImeiCard(imei,imeiConsulta)
    }

    Button(onClick = {
        composableBounds?.let { bounds ->
            val bitmap = Bitmap.createBitmap(
                bounds.width.toInt(),
                bounds.height.toInt(),
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap).apply {
                translate(-bounds.left, -bounds.top)
            }
            view.draw(canvas)
            val imageUri = saveBitmap2(bitmap, context)
            imageUri?.let { shareImageUri2(it, context) }
        }
    }) {
        Text("Capturar pantalla")
    }
}

fun saveBitmap2(bitmap: Bitmap, context: Context): Uri? {
    val filename = "${System.currentTimeMillis()}.jpg"
    var fos: OutputStream? = null
    var imageUri: Uri? = null
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
        context.contentResolver?.also { resolver ->
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }
    } else {
        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
        val image = File(imagesDir, filename)
        fos = FileOutputStream(image)
        imageUri = Uri.fromFile(image)
    }

    fos?.use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        Toast.makeText(context, "Imagen guardada", Toast.LENGTH_SHORT).show()
    }
    return imageUri
}

fun shareImageUri2(uri: Uri, context: Context) {
    val shareIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, uri)
        type = "image/jpeg"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Enviar a"))
}
////para captura 3

@Composable
fun MyComponent2(
    imei: ImeiItem?,
    imeiConsulta: String
) {
    val context = LocalContext.current
    val view = LocalView.current
    var composableBounds by remember { mutableStateOf<Rect?>(null) }
    var showButton by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .onGloballyPositioned {
                composableBounds = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    it.boundsInWindow()
                } else {
                    it.boundsInRoot()
                }
            }
    ) {
        // Tu componente real va aquí
        //Text("Hola, Mundo!")
        ImeiCard(imei,imeiConsulta)
    }

    if (showButton) {
        Button(onClick = {
            showButton = false
            composableBounds?.let { bounds ->
                val bitmap = Bitmap.createBitmap(
                    bounds.width.toInt(),
                    bounds.height.toInt(),
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap).apply {
                    translate(-bounds.left, -bounds.top)
                }
                view.draw(canvas)
                val imageUri = saveBitmap2(bitmap, context)
                imageUri?.let { shareImageUri2(it, context) }
            }
            showButton = true
        }) {
            Text("Capturar pantalla")
        }
    }
}
