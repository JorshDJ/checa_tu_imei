package com.miko.checa_tu_imei.domain.model

import com.miko.checa_tu_imei.data.model.ImeiResponse

data class ImeiItem(
    val Operador: String,
    val estadoEquipo: String,
    val fecha: String,
    val numImei: String
)

fun ImeiResponse.toImeiItem() = ImeiItem(Operador,estadoEquipo,fecha,numImei)
