package com.zebra.zcst

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class DisablePluginActivity: ComponentActivity() {

    val viewModel = DisablePluginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.handleOnCreate(this)
        setContent {
            RootView()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.handleOnPause(this)
    }

    override fun onResume() {
        super.onResume()
        viewModel.handleOnResume(this)
    }

    @Composable
    fun RootView() {
        val barcodeText = remember { viewModel.barcodeText }
        Column(
            Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            StyledOutlinedTextField(
                "scan barcode or manually input",
                barcodeText.value,
                modifier = Modifier
                    .fillMaxWidth()
            ) { newValue ->
                barcodeText.value = newValue
            }
            Text("delay before stop scanning 20-1000 (ms)")
            Text("value = ${viewModel.delayBeforeStopScanMilliSeconds.value.toInt()}")
            ThresholdSlider()
            RoundButton("Stop by \nDISABLE_PLUGIN & ENABLE_PLUGIN API", color = Color(0xFFF00000)) {
                viewModel.restartPlugin(this@DisablePluginActivity)
            }
        }
    }

    @Composable
    fun ThresholdSlider() {
        Slider(
            value = viewModel.delayBeforeStopScanMilliSeconds.value,
            onValueChange = { viewModel.delayBeforeStopScanMilliSeconds.value = it },
            colors = SliderDefaults.colors(
                thumbColor = Color.Blue,
                activeTrackColor = Color.Green,
                inactiveTickColor = Color.LightGray
            ),
            valueRange = 20f..1000f
        )
    }
}