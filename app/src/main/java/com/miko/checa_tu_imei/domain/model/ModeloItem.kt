package com.miko.checa_tu_imei.domain.model

import com.miko.checa_tu_imei.data.model.ModelosResponse

data class ModeloItem(
    val codigo: String,
    val descripcion: String
)

fun ModelosResponse.toModeloItem() = ModeloItem(codigo, descripcion)