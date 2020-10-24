package com.example.gitaplication.firstScreen.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast


class BluetoothManager {
    fun turnOn(btAdapter: BluetoothAdapter, activity: Activity?) {

        if (!btAdapter.isEnabled) {
            val intentBtEnabled = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

            val REQUEST_ENABLE_BT = 1

            activity?.startActivityForResult(intentBtEnabled, REQUEST_ENABLE_BT);
        } else {
            Toast.makeText(activity, "Bluetooth already enabled", Toast.LENGTH_SHORT).show();
        }

//        // Turning bluetooth ON without system permission
//        if (!btAdapter.isEnabled) {
//            btAdapter.enable()
//            Toast.makeText(activity, "Bluetooth turned ON", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(activity, "Bluetooth already enabled", Toast.LENGTH_SHORT).show()
//        }
    }

    fun turnOff(btAdapter: BluetoothAdapter, activity: Activity?) {
        if (btAdapter.isEnabled) {
            btAdapter.disable()
            Toast.makeText(activity, "Bluetooth turned OFF", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Bluetooth already disabled", Toast.LENGTH_SHORT).show()
        }
    }

    fun scan(bluetoothAdapter: BluetoothAdapter, activity: Activity?, mReceiver: BluetoothBroadcastReceiver) {
        bluetoothAdapter.startDiscovery()
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        activity?.registerReceiver(mReceiver, filter)
    }
}