package com.example.gitaplication.firstScreen.bluetooth

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import androidx.core.app.ActivityCompat




class BluetoothManager {
    fun turnOn(btAdapter: BluetoothAdapter, activity: Activity?) {

        if (!btAdapter.isEnabled) {
            val intentBtEnabled = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

            val REQUEST_ENABLE_BT = 1

            activity?.startActivityForResult(intentBtEnabled, REQUEST_ENABLE_BT)
        }
    }

    fun scan(bluetoothAdapter: BluetoothAdapter, activity: Activity?, mReceiver: BluetoothBroadcastReceiver) {
        val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        ActivityCompat.requestPermissions(
            activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
        )
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        activity.registerReceiver(mReceiver, filter)
        bluetoothAdapter.startDiscovery()
    }
}