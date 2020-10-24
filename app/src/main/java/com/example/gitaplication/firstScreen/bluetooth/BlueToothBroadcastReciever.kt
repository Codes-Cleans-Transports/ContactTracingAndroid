package com.example.gitaplication.firstScreen.bluetooth

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import org.greenrobot.eventbus.EventBus

class BluetoothBroadcastReceiver() : BroadcastReceiver() {

    val TAG: String = "DEVICE"

    override fun onReceive(p0: Context?, p1: Intent?) {

        val action: String? = p1?.action

        if (BluetoothDevice.ACTION_FOUND == action) {
            // Discovery has found a device. Get the BluetoothDevice
            // object and its info from the Intent.
            val bluetoothDevice: BluetoothDevice = p1.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)

            val deviceName = bluetoothDevice.name;
            val deviceHardwareAddress = bluetoothDevice.address; // MAC address

            val device = Device(deviceName, deviceHardwareAddress)

            val deviceEventHandler = DeviceEventHandler(device)
            val eventBus = EventBus()

            eventBus.post(deviceEventHandler)

            Toast.makeText(p0, "$deviceName $deviceHardwareAddress", Toast.LENGTH_SHORT).show();
        }
    }
}
