package com.example.pnlib_ph35035.model

import androidx.room.Entity

data class DetailBill (
    val imgPathBill: String,
    val nameBookBill: String,
    val nameBill: String,
    val numberPhoneBill: String,
    val quantityBill: Int,
    val priceBill: Int,
    val idBill: String,
    val status: String
)