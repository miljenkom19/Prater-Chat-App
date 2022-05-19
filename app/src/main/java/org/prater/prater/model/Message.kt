package org.prater.prater.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    val id: Int?,
    val content: String,
    val userId: Int,
    val conversationId: Int
) : Parcelable
