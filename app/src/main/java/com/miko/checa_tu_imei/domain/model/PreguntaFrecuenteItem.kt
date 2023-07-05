package com.miko.checa_tu_imei.domain.model

import com.miko.checa_tu_imei.data.model.ModelosResponse
import com.miko.checa_tu_imei.data.model.PreguntasFrecuentesResponse

data class PreguntaFrecuenteItem(
    val id: String,
    val pregunta: String,
    val respuesta: String,
)

fun PreguntasFrecuentesResponse.toPreguntaFrecuenteItem() = PreguntaFrecuenteItem(id,pregunta,respuesta)