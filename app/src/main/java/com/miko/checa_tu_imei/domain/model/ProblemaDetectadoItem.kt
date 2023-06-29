package com.miko.checa_tu_imei.domain.model

import com.miko.checa_tu_imei.data.model.EmpresasResponse
import com.miko.checa_tu_imei.data.model.ProblemasDetectadosResponse

data class ProblemaDetectadoItem(
    val descripcion: String,
    val id: String
)

fun ProblemasDetectadosResponse.toProblemaDetectadoItem() = ProblemaDetectadoItem(descripcion, id)