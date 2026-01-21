package com.zebra.zcst

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootView()
        }
    }

    @Composable
    fun RootView() {
        Column(
            Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text("日本語")
            Text("English")
            Text("中文")
            Text("Tiếng Việt")
            Text("แบบไทย")
            Text("हिंदी")
            Text("----------")
            RoundButton("STOP_SCANNING", color = Color(0xFF0000F0)) {
                startActivity(Intent(this@MainActivity, StopScanningActivity::class.java))
            }
            RoundButton("DISABLE_PLUGIN", color = Color(0xFF0000F0)) {
                startActivity(Intent(this@MainActivity, DisablePluginActivity::class.java))
            }
        }
    }
}