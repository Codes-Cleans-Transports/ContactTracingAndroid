package com.example.gitaplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class User(
    val username: String,
    val avatarUrl: String,
    val bio: String,
    val location: String,
    val publicRepos: Int,
    val followers: Int,
    val following: Int
) : Parcelable