package com.miko.checa_tu_imei.domain.usecases

import com.miko.checa_tu_imei.data.repository.ImeiRepository
import com.miko.checa_tu_imei.domain.model.PreguntaFrecuenteItem
import javax.inject.Inject

class GetPreguntasFrecuentesUseCase @Inject constructor(private val imeiRepository: ImeiRepository) {
    suspend operator fun invoke(): List<PreguntaFrecuenteItem> {
        return imeiRepository.getPreguntasFrecuentes()
    }
}