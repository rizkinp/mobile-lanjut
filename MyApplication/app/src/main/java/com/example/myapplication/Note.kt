package com.example.myapplication

data class Note(
    var id: String = "",
    val title: String = "",
    val description: String = ""
) {
    override fun toString(): String {
        return title
    }
}

