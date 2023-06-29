package com.miko.checa_tu_imei.ui.view.components

import android.app.DatePickerDialog

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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

import java.time.LocalDate
import java.util.Calendar
import java.util.Date

///CODIGO BUENO
@Composable
fun DatePickerExample(onDateSelected: (Date) -> Unit) {
    val context = LocalContext.current
    var dialogShown by remember { mutableStateOf(false) }

    if (dialogShown) {
        DatePickerDialog(context, { _, year, month, day ->
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, day)
            }
            onDateSelected(calendar.time)
            dialogShown = false
        }, 2023, 6, 13).show()
    }
}

@Composable
fun TimePickerExample(onTimeSelected: (Date) -> Unit) {
    val context = LocalContext.current
    var dialogShown by remember { mutableStateOf(false) }

    if (dialogShown) {
        TimePickerDialog(context, { _, hour, minute ->
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }
            onTimeSelected(calendar.time)
            dialogShown = false
        }, 12, 0, true).show()
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerExample() {
    val context = LocalContext.current
    var dialogShown by remember { mutableStateOf(false) }

    // Obtén la fecha actual como el valor por defecto
    val current = LocalDate.now()
    var dateText by remember { mutableStateOf(formatDate(current.dayOfMonth, current.monthValue, current.year)) }

    OutlinedTextField(
        value = dateText,
        onValueChange = { dateText = it },
        label = { Text("Fecha Reporte") },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            //.padding(16.dp)
        ,
        trailingIcon = {
            Icon(Icons.Filled.DateRange, "Fecha reporte",
                Modifier.clickable { dialogShown = true })
        }
    )

    if (dialogShown) {
        val dialog = DatePickerDialog(context, { _, year, month, dayOfMonth ->
            dateText = formatDate(dayOfMonth, month + 1, year)
            dialogShown = false
        }, current.year, current.monthValue - 1, current.dayOfMonth)

        dialog.setOnCancelListener {
            dialogShown = false
        }

        dialog.show()
    }
}

private fun formatDate(dayOfMonth: Int, month: Int, year: Int): String {
    return "${dayOfMonth}/${month}/$year"
}



