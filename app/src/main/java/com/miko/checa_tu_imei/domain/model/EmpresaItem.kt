package com.miko.checa_tu_imei.domain.model

import com.miko.checa_tu_imei.data.model.EmpresasResponse

data class EmpresaItem(
    val id: String,
    val nombre: String,
    val pagina_web: String,

)

fun EmpresasResponse.toEmpresaItem() = EmpresaItem(id, nombre,pagina_web)