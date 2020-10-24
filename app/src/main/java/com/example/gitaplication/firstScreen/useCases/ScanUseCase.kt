package com.example.gitaplication.firstScreen.useCases

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.IntentFilter
import com.example.gitaplication.firstScreen.bluetooth.BluetoothBroadcastReceiver
import com.example.gitaplication.firstScreen.bluetooth.BluetoothManager
import com.multiplatform.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ScanUseCase(
    private val bluetoothManager: BluetoothManager,
    private val activity: Activity?,
    private val bluetoothAdapter: BluetoothAdapter,
    private val mReceiver: BluetoothBroadcastReceiver
) : UseCase {
    suspend operator fun invoke() = withContext(Dispatchers.IO)
    {
        bluetoothManager.scan(bluetoothAdapter,activity,mReceiver)
    }
}