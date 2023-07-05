package com.miko.checa_tu_imei.ui.navigation

import android.os.Build
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.miko.checa_tu_imei.ui.view.EmpresasRentesegScreen
import com.miko.checa_tu_imei.ui.view.Formulario1Screen
import com.miko.checa_tu_imei.ui.view.Formulario2Screen
import com.miko.checa_tu_imei.ui.view.Formulario3Screen
import com.miko.checa_tu_imei.ui.view.Formulario4Screen
import com.miko.checa_tu_imei.ui.view.FormularioPrincipalScreen
import com.miko.checa_tu_imei.ui.view.HomeSreen
import com.miko.checa_tu_imei.ui.view.PreguntasFrecuentesScreen
import com.miko.checa_tu_imei.ui.view.RespuestaConsulta
import com.miko.checa_tu_imei.ui.view.RespuestaConsultaScreen
import com.miko.checa_tu_imei.ui.view.SplashScreen
import com.miko.checa_tu_imei.ui.viewmodel.ImeiViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    viewModel: ImeiViewModel,
    isDarkTheme: MutableState<Boolean>,
    icon: @Composable() (() -> Unit)?,

){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route ) {
        //NavHost(navController = navController, startDestination = AppScreens.Drawer.route){
        composable(route = AppScreens.SplashScreen.route) {
            SplashScreen(navHostController =navController)
        }
        composable(route = AppScreens.HomeScreen.route){

            HomeSreen(navHostController = navController,viewModel=viewModel,isDarkTheme=isDarkTheme,icon = icon)
        }
        composable(
            route = AppScreens.RespuestaConsultaScreen.route +"{imeiConsulta}",
            arguments = listOf(navArgument(name = "imeiConsulta"){ type = NavType.StringType
            }
        ) ){
            it.arguments?.getString("imeiConsulta")?.let { it1 ->
                RespuestaConsultaScreen(navHostController = navController, viewModel = viewModel, isDarkTheme = isDarkTheme,
                    imeiConsulta = it1
                ) {
                }
            }
        }
        composable(route = AppScreens.EmpresasRentesegScreen.route){
            EmpresasRentesegScreen(
                navHostController = navController,
                viewModel = viewModel,
                isDarkTheme = isDarkTheme,
                icon =icon
            )
        }
        composable(route = AppScreens.FormularioPrincipalScreen.route){
            FormularioPrincipalScreen(
                navHostController = navController,
                viewModel = viewModel,
                isDarkTheme = isDarkTheme,
                icon =icon
            )
        }
        composable(route = AppScreens.Formulario1Screen.route){
            Formulario1Screen(
                navHostController = navController,
                viewModel = viewModel,
                isDarkTheme = isDarkTheme,
                icon =icon
            )
        }
        composable(route = AppScreens.Formulario2Screen.route){
            Formulario2Screen(
                navHostController = navController,
                viewModel = viewModel,
                isDarkTheme = isDarkTheme,
                icon =icon
            )
        }
        composable(route = AppScreens.Formulario3Screen.route){
            Formulario3Screen(
                navHostController = navController,
                viewModel = viewModel,
                isDarkTheme = isDarkTheme,
                icon =icon
            )
        }
        composable(route = AppScreens.Formulario4Screen.route){
            Formulario4Screen(
                navHostController = navController,
                viewModel = viewModel,
                isDarkTheme = isDarkTheme,
                icon =icon
            )
        }
        composable(route = AppScreens.PreguntasFrecuentesScreen.route){
            PreguntasFrecuentesScreen(
                navHostController = navController,
                viewModel = viewModel,
                isDarkTheme = isDarkTheme,
                icon =icon
            )
        }
    }
}