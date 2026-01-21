package com.zebra.zcst

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class StopScanningActivity: ComponentActivity() {

    val viewModel = StopScanningViewModel()

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
            RoundButton("Stop by \nSTOP_SCANNING API", color = Color(0xFFF00000)) {
                viewModel.stopScanning(this@StopScanningActivity)
            }
        }
    }
}