package com.miko.checa_tu_imei.domain.usecases

import com.miko.checa_tu_imei.data.repository.ImeiRepository
import com.miko.checa_tu_imei.domain.model.ModeloItem
import javax.inject.Inject

class GetModelosxMarcaUseCase @Inject constructor(private val imeiRepository: ImeiRepository) {

    suspend operator fun invoke(idMarca: String): List<ModeloItem> {
        return imeiRepository.getModelosxMarca(idMarca)
    }
}