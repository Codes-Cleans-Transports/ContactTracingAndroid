package com.example.gitaplication.repositories.rest

data class Conatacts(
    val macs: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Conatacts

        if (!macs.contentEquals(other.macs)) return false

        return true
    }

    override fun hashCode(): Int {
        return macs.contentHashCode()
    }
}