package com.laba.authmmobileulstu

class DataItems {
    private var dance: List<ItemList>?= null

    fun getUsers(): List<ItemList>? {
        return dance
    }

    fun setUsers(dance: List<ItemList> ) {
        this.dance = dance
    }
}
