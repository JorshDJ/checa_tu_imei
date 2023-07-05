package com.miko.checa_tu_imei.data.remote

import com.miko.checa_tu_imei.data.model.EmpresasResponse
import com.miko.checa_tu_imei.data.model.ImeiResponse
import com.miko.checa_tu_imei.data.model.MarcasResponse
import com.miko.checa_tu_imei.data.model.ModelosResponse
import com.miko.checa_tu_imei.data.model.PreguntasFrecuentesResponse
import com.miko.checa_tu_imei.data.model.ProblemasDetectadosResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImeiService @Inject constructor(private val imeiApi: ImeiApi) {

    suspend fun getImeis(): List<ImeiResponse> {
        return withContext(Dispatchers.IO) {
            val imeis = imeiApi.getImeis()
            imeis.body() ?: emptyList()
        }
    }

    suspend fun getConsultaImei(imei:String): ImeiResponse? {
        return withContext(Dispatchers.IO) {
            val consultaImei = imeiApi.getConsultaImei(imei)
            consultaImei.body()
        }
    }

    suspend fun getMarcas(): List<MarcasResponse> {
        return withContext(Dispatchers.IO) {
            val marcas = imeiApi.getMarcas()
            marcas.body() ?: emptyList()
        }
    }

    suspend fun getModelosxMarca(idMarca: String): List<ModelosResponse> {
        return withContext(Dispatchers.IO) {
            val modelos = imeiApi.getModelosxMarca(idMarca)
            modelos.body() ?: emptyList()
        }
    }

    suspend fun getEmpresas(): List<EmpresasResponse> {
        return withContext(Dispatchers.IO) {
            val empresas = imeiApi.getEmpresas()
            empresas.body() ?: emptyList()
        }
    }

    suspend fun getProblemasDetectados(): List<ProblemasDetectadosResponse> {
        return withContext(Dispatchers.IO) {
            val problemasDetectados = imeiApi.getProblemasDetectados()
            problemasDetectados.body() ?: emptyList()
        }
    }

    suspend fun getPreguntasFrecuentes(): List<PreguntasFrecuentesResponse> {
        return withContext(Dispatchers.IO) {
            val preguntasFrecuentes = imeiApi.getPreguntasFrecuentes()
            preguntasFrecuentes.body() ?: emptyList()
        }
    }


}