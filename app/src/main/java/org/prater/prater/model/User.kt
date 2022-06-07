package org.prater.prater.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int?,
    val username: String,
    val password: String,
    val profilePicture: Int
) : Parcelable
