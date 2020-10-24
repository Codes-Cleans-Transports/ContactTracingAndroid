package com.example.gitaplication.firstScreen.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter


class BluetoothManager {
    fun turnOn(btAdapter: BluetoothAdapter, activity: Activity?) {

        if (!btAdapter.isEnabled) {
            val intentBtEnabled = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

            val REQUEST_ENABLE_BT = 1

            activity?.startActivityForResult(intentBtEnabled, REQUEST_ENABLE_BT)
        }
    }

    fun scan(bluetoothAdapter: BluetoothAdapter, activity: Activity?, mReceiver: BluetoothBroadcastReceiver) {
        bluetoothAdapter.startDiscovery()
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        activity?.registerReceiver(mReceiver, filter)
    }
}