package com.miko.checa_tu_imei.data.remote

import com.miko.checa_tu_imei.data.model.EmpresasResponse
import com.miko.checa_tu_imei.data.model.ImeiResponse
import com.miko.checa_tu_imei.data.model.MarcasResponse
import com.miko.checa_tu_imei.data.model.ModelosResponse
import com.miko.checa_tu_imei.data.model.PreguntasFrecuentesResponse
import com.miko.checa_tu_imei.data.model.ProblemasDetectadosResponse
import com.miko.checa_tu_imei.util.Constants.END_POINT_IMEI
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ImeiApi {


    @GET(END_POINT_IMEI)
    suspend fun getImeis(): Response<List<ImeiResponse>>
    //PARA HOME SCREEN
    @GET("/imei/{imei}")
    suspend fun getConsultaImei(@Path("imei") imei: String):Response<ImeiResponse>

    //PARA FORMULARIOS
    @GET("/marcas")
    suspend fun getMarcas():Response<List<MarcasResponse>>

    @GET("/modelos/{idMarca}")
    suspend fun getModelosxMarca(@Path("idMarca") idMarca: String):Response<List<ModelosResponse>>

    @GET("/empresas")
    suspend fun getEmpresas():Response<List<EmpresasResponse>>

    @GET("/problemas_detectados")
    suspend fun getProblemasDetectados():Response<List<ProblemasDetectadosResponse>>

    @GET("/preguntas_frecuentes")
    suspend fun getPreguntasFrecuentes():Response<List<PreguntasFrecuentesResponse>>

}

