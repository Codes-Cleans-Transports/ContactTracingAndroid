package com.example.gitaplication.firstScreen.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.IntentFilter
import android.widget.Toast


class BluetoothManager {
    fun turnOn(btAdapter: BluetoothAdapter, activity: Activity?) {
        // Turning bluetooth ON with system permission
//        if (!btAdapter.isEnabled()) {
//            Intent intentBtEnabled = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            // The REQUEST_ENABLE_BT constant passed to startActivityForResult() is a locally defined integer (which must be greater than 0), that the system passes back to you in your onActivityResult()
//            // implementation as the requestCode parameter.
//            int REQUEST_ENABLE_BT = 1;
//            activity.startActivityForResult(intentBtEnabled, REQUEST_ENABLE_BT);
//        } else {
//            Toast.makeText(activity, "Bluetooth already enabled", Toast.LENGTH_SHORT).show();
//        }

        // Turning bluetooth ON without system permission
        if (!btAdapter.isEnabled) {
            btAdapter.enable()
            Toast.makeText(activity, "Bluetooth turned ON", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Bluetooth already enabled", Toast.LENGTH_SHORT).show()
        }
    }

    fun turnOff(btAdapter: BluetoothAdapter, activity: Activity?) {
        if (btAdapter.isEnabled) {
            btAdapter.disable()
            Toast.makeText(activity, "Bluetooth turned OFF", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Bluetooth already disabled", Toast.LENGTH_SHORT).show()
        }
    }

    fun scan(bluetoothAdapter: BluetoothAdapter, activity: Activity) {
        bluetoothAdapter.startDiscovery()
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        activity.registerReceiver(mReceiver, filter)
    }
}