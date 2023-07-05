package com.miko.checa_tu_imei.data.repository

import com.miko.checa_tu_imei.data.remote.ImeiService
import com.miko.checa_tu_imei.domain.model.EmpresaItem
import com.miko.checa_tu_imei.domain.model.ImeiItem
import com.miko.checa_tu_imei.domain.model.MarcaItem
import com.miko.checa_tu_imei.domain.model.ModeloItem
import com.miko.checa_tu_imei.domain.model.PreguntaFrecuenteItem
import com.miko.checa_tu_imei.domain.model.ProblemaDetectadoItem
import com.miko.checa_tu_imei.domain.model.toEmpresaItem
import com.miko.checa_tu_imei.domain.model.toImeiItem
import com.miko.checa_tu_imei.domain.model.toMarcaItem
import com.miko.checa_tu_imei.domain.model.toModeloItem
import com.miko.checa_tu_imei.domain.model.toPreguntaFrecuenteItem
import com.miko.checa_tu_imei.domain.model.toProblemaDetectadoItem
import javax.inject.Inject

class ImeiRepository @Inject constructor(private val imeiService: ImeiService) {

    suspend fun getImeis(): List<ImeiItem> {
        return imeiService.getImeis().map {
            it.toImeiItem()
        }
    }
    suspend fun getConsultaImei(imei: String): ImeiItem? {
        val response = imeiService.getConsultaImei(imei)
        return response?.toImeiItem()
    }
    suspend fun getMarcas(): List<MarcaItem> {
        return imeiService.getMarcas().map{
            it.toMarcaItem()
        }
    }

    suspend fun getModelosxMarca(idMarca: String): List<ModeloItem> {
        val response = imeiService.getModelosxMarca(idMarca)
        return response.map { it.toModeloItem() } ?: emptyList()
    }

    suspend fun getEmpresas(): List<EmpresaItem> {
        val response = imeiService.getEmpresas()
        return response.map { it.toEmpresaItem() } ?: emptyList()
    }

    suspend fun getProblemasDetectados(): List<ProblemaDetectadoItem> {
        val response = imeiService.getProblemasDetectados()
        return response.map { it.toProblemaDetectadoItem() } ?: emptyList()
    }

    suspend fun getPreguntasFrecuentes() : List<PreguntaFrecuenteItem>{
        val response = imeiService.getPreguntasFrecuentes()
        return response.map { it.toPreguntaFrecuenteItem() } ?: emptyList()
    }

}