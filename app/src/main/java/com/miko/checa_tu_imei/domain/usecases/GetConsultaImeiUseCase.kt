package com.miko.checa_tu_imei.domain.usecases

import com.miko.checa_tu_imei.data.repository.ImeiRepository
import com.miko.checa_tu_imei.domain.model.ImeiItem
import javax.inject.Inject

class GetConsultaImeiUseCase @Inject constructor(private val imeiRepository: ImeiRepository) {

    suspend operator fun invoke(imei: String): ImeiItem? {
        return imeiRepository.getConsultaImei(imei)
    }
}