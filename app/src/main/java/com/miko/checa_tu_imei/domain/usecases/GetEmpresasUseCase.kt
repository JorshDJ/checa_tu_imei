package com.miko.checa_tu_imei.domain.usecases

import com.miko.checa_tu_imei.data.repository.ImeiRepository
import com.miko.checa_tu_imei.domain.model.EmpresaItem
import javax.inject.Inject

class GetEmpresasUseCase @Inject constructor(private val imeiRepository: ImeiRepository) {

    suspend operator fun invoke(): List<EmpresaItem> {
        return imeiRepository.getEmpresas()
    }
}