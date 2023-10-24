package com.example.conversiondedivisas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import com.example.conversiondedivisas.ui.theme.ConversionDeDivisasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConversionDeDivisasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConversionDeDivisas()
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ConversionDeDivisas( modifier: Modifier = Modifier) {
    val apikey = "fca_live_Dox4LM8NuMBMdBqvukXIpp7e42XJUgrtKzFpQ80b"
    val divisaModel = DivisaModel()
    var conversion by remember {
        mutableStateOf<ConversionData?>(null)
    }
    var divisas by remember {
        mutableStateOf<DivisaData?>(null)
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var divisaOrigen by remember {
        mutableStateOf("")
    }
    var divisaDestino by remember {
        mutableStateOf("")
    }
    var botonPresionado by remember {
        mutableStateOf("")
    }
    divisaModel.viewModelScope.launch(Dispatchers.IO) {
        try {
            divisas = divisaModel.getDivisas(apikey)
        } catch (ex: Exception){
            divisaOrigen = ""
            divisaDestino = ""

        }

    }


    Column {
        Row {
            Button(onClick = { isExpanded = true; botonPresionado = "origen" }) {
                Text(text = "Selecciona origen")
            }
            Text(text = divisaOrigen)
        }
        Row {
            Button(onClick = { isExpanded = true; botonPresionado = "destino" }) {
                Text(text = "Selecciona destino")
            }
            Text(text = divisaDestino)
        }
        if (divisaOrigen != "" && divisaDestino != "") {
            divisaModel.viewModelScope.launch(Dispatchers.IO) {
                try {
                    conversion = divisaModel.getConversion(apikey, divisaOrigen, divisaDestino)
                }catch (ex: Exception){
                    divisaOrigen = ""
                    divisaDestino = ""
                }
            }
            if (conversion != null) {
                Row {
                    Text(text = "El tipo de cambio es:${conversion?.data!![divisaDestino]}")
                }
            }
        }
        Row {
            DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                divisas?.data?.forEach {
                    DropdownMenuItem(text = { Text(text = it.value.name) },
                        onClick = {
                            isExpanded = false
                            if(botonPresionado == "origen"){
                                divisaOrigen = it.value.code
                            }
                            else if(botonPresionado == "destino"){
                                divisaDestino = it.value.code
                            }
                        })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConversionDeDivisasTheme {
        ConversionDeDivisas()
    }
}