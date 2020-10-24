package com.example.gitaplication.firstScreen.useCases

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import com.example.gitaplication.firstScreen.bluetooth.BluetoothManager
import com.multiplatform.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TurnBluetoothOnUseCase(
    private val bluetoothManger: BluetoothManager,
    private val activity: Activity?,
    private val bluetoothAdapter: BluetoothAdapter
) : UseCase {
    suspend operator fun invoke() = withContext(Dispatchers.IO)
    {
        bluetoothManger.turnOn(bluetoothAdapter, activity)
    }
}