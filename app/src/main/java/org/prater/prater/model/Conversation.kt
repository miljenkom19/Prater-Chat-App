package org.prater.prater.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Conversation(
    val id: Int?,
    val user1: Int,
    val user2: Int
): Parcelable