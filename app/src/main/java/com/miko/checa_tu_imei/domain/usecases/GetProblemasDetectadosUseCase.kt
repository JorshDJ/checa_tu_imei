package com.miko.checa_tu_imei.domain.usecases

import com.miko.checa_tu_imei.data.repository.ImeiRepository
import com.miko.checa_tu_imei.domain.model.EmpresaItem
import com.miko.checa_tu_imei.domain.model.ProblemaDetectadoItem
import javax.inject.Inject

class GetProblemasDetectadosUseCase @Inject constructor(private val imeiRepository: ImeiRepository) {
    suspend operator fun invoke(): List<ProblemaDetectadoItem> {
        return imeiRepository.getProblemasDetectados()
    }
}
