package com.miko.checa_tu_imei

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.miko.checa_tu_imei.ui.navigation.AppNavigation
import com.miko.checa_tu_imei.ui.theme.Checa_tu_imeiTheme
import com.miko.checa_tu_imei.ui.view.ImeiScreen
import com.miko.checa_tu_imei.ui.viewmodel.ImeiViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ImeiViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //Variables para dark theme
            val isDarkTheme = remember { mutableStateOf(false) }
            //Variable que regresa un icono de acuerdo a lo que manda
            val icon: (@Composable () -> Unit)? = if (isDarkTheme.value) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
            Checa_tu_imeiTheme (darkTheme = isDarkTheme.value){
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    //color = MaterialTheme.colors.background
                ) {
                    //ImeiScreen(viewModel)
                    AppNavigation(viewModel=viewModel,isDarkTheme = isDarkTheme, icon = icon)
                }
            }
        }
    }
}

