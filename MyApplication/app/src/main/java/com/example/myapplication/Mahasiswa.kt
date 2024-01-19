package com.example.myapplication

data class Mahasiswa(
    var id: String = "",
    val name: String = "",
    val address: String = "",
    val phone: String = ""
) {
    override fun toString(): String {
        return "ID: $id\nName: $name\nAddress: $address\nPhone: $phone"
    }
}




