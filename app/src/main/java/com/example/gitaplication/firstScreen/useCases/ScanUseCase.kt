package com.example.gitaplication.firstScreen.useCases

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.IntentFilter
import com.multiplatform.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ScanUseCase(
    private val activity: Activity?,
    private val bluetoothAdapter: BluetoothAdapter
) : UseCase {
    suspend operator fun invoke() = withContext(Dispatchers.IO)
    {
        bluetoothAdapter.startDiscovery()
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        activity!!.registerReceiver(mReceiver, filter)
    }
}