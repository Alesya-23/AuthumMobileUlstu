package com.laba.authmmobileulstu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    var deleteFlag: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var itemList: MutableLiveData<String> = MutableLiveData<String>()
    var newItem: MutableLiveData<String> = MutableLiveData<String>()
    var itemKey: MutableLiveData<Int> = MutableLiveData<Int>()

    fun setItem(item: String) {
        if (item != null) {
            itemList.value = item
        }
    }

    fun getItem() = itemList

    fun setKey(key: Int) {
        itemKey.value = key
    }
}