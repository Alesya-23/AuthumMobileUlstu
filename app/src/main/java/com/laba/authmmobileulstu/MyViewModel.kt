package com.laba.authmmobileulstu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    var deleteFlag: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var itemList: MutableLiveData<ItemList> = MutableLiveData<ItemList>()
    var newItem: MutableLiveData<ItemList> = MutableLiveData<ItemList>()
    var itemKey: MutableLiveData<Int> = MutableLiveData<Int>()

    fun setItem(item: ItemList) {
        if (item != null) {
            itemList.value = item
        }
    }

    fun getItem() = itemList

    fun setKey(key: Int) {
        itemKey.value = key
    }
}