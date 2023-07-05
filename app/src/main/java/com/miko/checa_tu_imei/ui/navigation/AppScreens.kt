package com.miko.checa_tu_imei.ui.navigation

sealed class AppScreens(val route:String){
    object SplashScreen:AppScreens("splash_screen")
    object HomeScreen:AppScreens("home_screen")
    object RespuestaConsultaScreen :AppScreens("respuesta_consulta_screen")
    object EmpresasRentesegScreen :AppScreens("empresas_renteseg_screen")
    object PreguntasFrecuentesScreen :AppScreens("preguntas_frecuentes_screen")
    object FormularioPrincipalScreen :AppScreens("formulario_principal_screen")
    object Formulario1Screen :AppScreens("formulario_1_screen")
    object Formulario2Screen :AppScreens("formulario_2_screen")
    object Formulario3Screen :AppScreens("formulario_3_screen")
    object Formulario4Screen :AppScreens("formulario_4_screen")
}