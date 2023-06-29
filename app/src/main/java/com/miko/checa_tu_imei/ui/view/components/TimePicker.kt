package com.miko.checa_tu_imei.ui.view.components

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerExample() {
    val context = LocalContext.current
    var dialogShown by remember { mutableStateOf(false) }

    // Obtén la hora actual como valor por defecto
    val current = LocalTime.now()
    var timeText by remember { mutableStateOf(formatTime(current.hour, current.minute)) }

    OutlinedTextField(
        value = timeText,
        onValueChange = { timeText = it },
        label = { Text("Hora Reporte") },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            //.padding(16.dp)
        ,
        trailingIcon = {
            Icon(Icons.Filled.Edit, "Hora reporte",
                Modifier.clickable { dialogShown = true })
        }
    )

    if (dialogShown) {
        val dialog = TimePickerDialog(context, { _, hour, minute ->
            timeText = formatTime(hour, minute)
            dialogShown = false
        }, current.hour, current.minute, true)

        dialog.setOnCancelListener {
            dialogShown = false
        }

        dialog.show()
    }
}

private fun formatTime(hour: Int, minute: Int): String {
    return "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
}

