package com.example.gitaplication.firstScreen.bluetooth

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.greenrobot.eventbus.EventBus

class BluetoothBroadcastReceiver() : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {

        val action: String? = p1?.action

        if (BluetoothDevice.ACTION_FOUND == action) {

            val bluetoothDevice: BluetoothDevice? = p1.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)

            val deviceName = bluetoothDevice?.name;
            val deviceHardwareAddress = bluetoothDevice?.address; // MAC address

            val device = Device(deviceHardwareAddress, deviceName)

            val deviceEventHandler = DeviceEventHandler(device)

            EventBus.getDefault().post(deviceEventHandler)
        }
    }
}
