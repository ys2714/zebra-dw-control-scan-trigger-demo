package com.zebra.zcst

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import java.util.Date
import java.util.Timer
import kotlin.concurrent.schedule

class DisablePluginViewModel : ViewModel() {

    /**
     * please create a profile with following settings:
     *
     * barcode_input: enable
     * barcode_aim_type
     *
     * aim_type:
     *
     * 0 - Trigger
     * 1 - Timed Hold
     * 2 - Timed Release
     * 3 - Press And Release
     * 4 - Presentation
     * 5 - Continuous Read
     * 6 - Press and Sustain
     * 7 – Press and Continue
     * 8 - Timed Continuous (select this one in profile)
     * */
    companion object {
        val profileName = "control_scan_trigger"
        val scanResultAction = "com.zebra.trigger.ACTION"
        val scanResultCategory = "com.zebra.trigger.CATEGORY"
    }

    var delayBeforeStopScanMilliSeconds: MutableState<Float> = mutableStateOf(20f)
    var barcodeText: MutableState<String> = mutableStateOf("")
    var scanDataIntentReceiver: BroadcastReceiver? = null

    private val delay: Long
        get() {
            return delayBeforeStopScanMilliSeconds.value.toLong()
        }

    fun handleOnCreate(context: Context) {
        context.sendOrderedBroadcast(
            Intent().apply {
                action = "com.symbol.datawedge.api.ACTION"
                putExtra("com.symbol.datawedge.api.SWITCH_TO_PROFILE", profileName)
            },
            null
        )
    }

    fun handleOnResume(context: Context) {
        registerDataListener(context)
    }

    fun handleOnPause(context: Context) {
        unregisterDataListener(context)
    }

    fun handleOnScanData(context: Context, intent: Intent?) {
        if (intent == null) {
            return
        }
        if (intent.action != scanResultAction) {
            return
        }
        intent.extras?.let {
            val type = it.getString("com.symbol.datawedge.label_type") ?: ""
            val data = it.getString("com.symbol.datawedge.data_string") ?: ""
            val timestamp: Long = it.getLong("com.symbol.datawedge.data_dispatch_time")
            val date = Date(timestamp)
            barcodeText.value = data
        }
        restartPlugin(context)
    }

    fun restartPlugin(context: Context) {
        // delay 20ms to wait beep sound over
        Timer().schedule(delay) {
            disablePlugin(context)
        }
        Timer().schedule(delay + 5) {
            enablePlugin(context)
        }
    }

    private fun disablePlugin(context: Context) {
        context.sendOrderedBroadcast(
            Intent().apply {
                action = "com.symbol.datawedge.api.ACTION"
                putExtra("com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", "DISABLE_PLUGIN")
            },
            null
        )
    }

    private fun enablePlugin(context: Context) {
        context.sendOrderedBroadcast(
            Intent().apply {
                action = "com.symbol.datawedge.api.ACTION"
                putExtra("com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", "ENABLE_PLUGIN")
            },
            null
        )
    }

    // Setup Listeners

    fun registerDataListener(context: Context) {
        val ctx = context.applicationContext
        if (scanDataIntentReceiver != null) {
            return
        }
        scanDataIntentReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                handleOnScanData(ctx, intent)
            }
        }
        ContextCompat.registerReceiver(
            ctx,
            scanDataIntentReceiver,
            IntentFilter().apply {
                addAction(scanResultAction)
                addCategory(scanResultCategory)
            },
            ContextCompat.RECEIVER_EXPORTED
        )
    }

    fun unregisterDataListener(context: Context) {
        val ctx = context.applicationContext
        if (scanDataIntentReceiver != null) {
            ctx.unregisterReceiver(scanDataIntentReceiver)
            scanDataIntentReceiver = null
        }
    }
}