package com.miko.checa_tu_imei.domain.model

import com.miko.checa_tu_imei.data.model.MarcasResponse

data class MarcaItem(
    val id: String,
    val nombre: String
)

fun MarcasResponse.toMarcaItem() = MarcaItem(id, nombre)