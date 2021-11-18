package com.laba.authmmobileulstu

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*


data class ItemList(
    @SerializedName("ID")
    var id: Int,
    @SerializedName("NameDance")
    var nameDance: String,
    @SerializedName("DateCreate")
    var isModernDance: Boolean
)