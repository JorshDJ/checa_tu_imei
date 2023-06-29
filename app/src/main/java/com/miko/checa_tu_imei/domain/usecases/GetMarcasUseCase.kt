package com.miko.checa_tu_imei.domain.usecases

import com.miko.checa_tu_imei.data.repository.ImeiRepository
import com.miko.checa_tu_imei.domain.model.MarcaItem
import javax.inject.Inject

class GetMarcasUseCase @Inject constructor(private val imeiRepository: ImeiRepository) {

    suspend operator fun invoke(): List<MarcaItem> {
        return imeiRepository.getMarcas()
    }
}