package com.miko.checa_tu_imei.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.miko.checa_tu_imei.ui.viewmodel.ImeiViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImeiScreen(viewModel: ImeiViewModel) {
    val imeiList by viewModel.imeis.observeAsState(initial = emptyList())
    val imei by viewModel.imei.observeAsState()
    var inputImei by remember { mutableStateOf("") }

    Column {
        TextField(
            value = inputImei,
            onValueChange = { newValue -> inputImei = newValue },
            label = { Text("IMEI") },
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = {
                viewModel.loadImei(inputImei)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Consultar IMEI")
        }
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(imeiList) { imeiItem ->
                Text(text = imeiItem.numImei)
            }
        }

        Text(text = "Resultado de la consulta:")
        //Text(text = imei?.numImei ?: "IMEI no encontrado")

        if (imei == null){
            Text(text = "IMEI no encontrado:")
        } else{
            Text(text = "Operador: ${imei!!.Operador}")
            Text(text = "Estado del Equipo: ${imei!!.estadoEquipo}")
            Text(text = "Fecha: ${imei!!.fecha}")
            Text(text = "Número IMEI: ${imei!!.numImei.toString()}")
        }

    }
}
