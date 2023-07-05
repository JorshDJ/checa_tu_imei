package com.miko.checa_tu_imei.ui.viewmodel

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miko.checa_tu_imei.MainActivity
import com.miko.checa_tu_imei.domain.model.EmpresaItem
import com.miko.checa_tu_imei.domain.model.ImeiItem
import com.miko.checa_tu_imei.domain.model.MarcaItem
import com.miko.checa_tu_imei.domain.model.ModeloItem
import com.miko.checa_tu_imei.domain.model.PreguntaFrecuenteItem
import com.miko.checa_tu_imei.domain.model.ProblemaDetectadoItem
import com.miko.checa_tu_imei.domain.usecases.GetConsultaImeiUseCase
import com.miko.checa_tu_imei.domain.usecases.GetEmpresasUseCase
import com.miko.checa_tu_imei.domain.usecases.GetImeisUseCase
import com.miko.checa_tu_imei.domain.usecases.GetMarcasUseCase
import com.miko.checa_tu_imei.domain.usecases.GetModelosxMarcaUseCase
import com.miko.checa_tu_imei.domain.usecases.GetPreguntasFrecuentesUseCase
import com.miko.checa_tu_imei.domain.usecases.GetProblemasDetectadosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class ImeiViewModel @Inject constructor(
    private val getImeisUseCase: GetImeisUseCase,
    private val getConsultaImeiUseCase: GetConsultaImeiUseCase,
    private val getMarcasUseCase: GetMarcasUseCase,
    private val getModelosxMarcaUseCase: GetModelosxMarcaUseCase,
    private val getEmpresasUseCase: GetEmpresasUseCase,
    private val getProblemasDetectadosUseCase: GetProblemasDetectadosUseCase,
    private val getPreguntasFrecuentesUseCase: GetPreguntasFrecuentesUseCase


) : ViewModel() {

    private val _imeis = MutableLiveData<List<ImeiItem>>()
    val imeis: LiveData<List<ImeiItem>> = _imeis

    private val _imei = MutableLiveData<ImeiItem>()
    val imei: LiveData<ImeiItem> = _imei

    private val _marcas = MutableLiveData<List<MarcaItem>>()
    val marcas: LiveData<List<MarcaItem>> = _marcas

    private val _modelos = MutableLiveData<List<ModeloItem>>()
    val modelos: LiveData<List<ModeloItem>> = _modelos

    private val _empresas = MutableLiveData<List<EmpresaItem>>()
    val empresas: LiveData<List<EmpresaItem>> = _empresas

    private val _problemasDetectados = MutableLiveData<List<ProblemaDetectadoItem>>()
    val problemasDetectados: LiveData<List<ProblemaDetectadoItem>> = _problemasDetectados

    private val _preguntasFrecuentes = MutableLiveData<List<PreguntaFrecuenteItem>>()
    val preguntasFrecuentes: LiveData<List<PreguntaFrecuenteItem>> = _preguntasFrecuentes


    init {
        //loadImeis()
        loadMarcas()
        loadEmpresas()
        loadProblemasDetectados()
        loadPreguntasFrecuentes()
    }

    private fun loadImeis() = viewModelScope.launch {
        _imeis.value = getImeisUseCase.invoke()
    }

    fun loadImei(imei: String) = viewModelScope.launch {
        _imei.value = getConsultaImeiUseCase.invoke(imei)
    }

    private fun loadMarcas() = viewModelScope.launch {
        _marcas.value = getMarcasUseCase.invoke()
    }

    fun loadModelosxMarca(idMarca: String) = viewModelScope.launch {
        _modelos.value = getModelosxMarcaUseCase.invoke(idMarca)
    }

    fun loadEmpresas() = viewModelScope.launch {
        _empresas.value = getEmpresasUseCase.invoke()
    }
    private fun loadProblemasDetectados() = viewModelScope.launch {
        _problemasDetectados.value = getProblemasDetectadosUseCase.invoke()
    }

    fun loadPreguntasFrecuentes() = viewModelScope.launch {
        _preguntasFrecuentes.value=getPreguntasFrecuentesUseCase.invoke()
    }
}


