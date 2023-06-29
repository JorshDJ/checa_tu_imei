package com.miko.checa_tu_imei.domain.usecases

import com.miko.checa_tu_imei.data.repository.ImeiRepository
import com.miko.checa_tu_imei.domain.model.ImeiItem
import javax.inject.Inject

class GetImeisUseCase @Inject constructor(private val gameRepository: ImeiRepository) {

    suspend operator fun invoke(): List<ImeiItem> {
        return gameRepository.getImeis().shuffled()
    }
}

/*
Este es mi ImeiResponse
data class ImeiResponse(
    val Operador: String,
    val estadoEquipo: String,
    val fecha: String,
    val numImei: String
)
Este es mi ImeiApi
interface ImeiApi {

    @GET(END_POINT_IMEI)
    suspend fun getImeis(): Response<List<ImeiResponse>>

    @GET("/imei/{imei}")
    suspend fun getConsultaImei(@Path("imei") imei: String):Response<ImeiResponse>
}

Este es mi ImeiService
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
}

Este es mi ImeiRepository
class ImeiRepository @Inject constructor(private val imeiService: ImeiService) {

    suspend fun getImeis(): List<ImeiItem> {
        return imeiService.getImeis().map {
            it.toImeiItem()
        }
    }
    suspend fun getConsultaImei(imei: String): ImeiItem? {
        val response = imeiService.getConsultaImei(imei)
        return response?.toImeiItem() // Aquí asumí que tienes un método de extensión toImeiItem() para ImeiResponse
    }
}

Este es mi RetrofitModule
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideImeiApi(retrofit: Retrofit): ImeiApi {
        return retrofit.create(ImeiApi::class.java)
    }
}
Este es mi ImeiItem
data class ImeiItem(
    val Operador: String,
    val estadoEquipo: String,
    val fecha: String,
    val numImei: String
)

fun ImeiResponse.toImeiItem() = ImeiItem(Operador,estadoEquipo,fecha,numImei)

este es mi GetConsultaImeiUseCase
class GetConsultaImeiUseCase @Inject constructor(private val imeiRepository: ImeiRepository) {

    suspend operator fun invoke(imei: String): ImeiItem? {
        return imeiRepository.getConsultaImei(imei)
    }
}
este es mi GetImeisUseCase
class GetImeisUseCase @Inject constructor(private val gameRepository: ImeiRepository) {

    suspend operator fun invoke(): List<ImeiItem> {
        return gameRepository.getImeis().shuffled()
    }
}

 */