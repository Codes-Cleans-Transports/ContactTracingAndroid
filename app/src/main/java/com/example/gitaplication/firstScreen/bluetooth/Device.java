package com.example.gitaplication.firstScreen.bluetooth;

import java.util.Objects;

public class Device {

    public final String deviceName;
    public final String deviceMAC;

    public Device(String deviceName, String deviceMAC) {
        this.deviceName = deviceName;
        this.deviceMAC = deviceMAC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return deviceName.equals(device.deviceName) && deviceMAC.equals(((Device) o).deviceMAC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceName, deviceMAC);
    }
}