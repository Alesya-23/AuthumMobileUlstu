package com.laba.authmmobileulstu

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*


data class ItemList(
    @SerializedName("ID")
    val id: Int,
    @SerializedName("NameDance")
    val nameDance: String,
    @SerializedName("DateCreate")
    val isModernDance: Boolean
)