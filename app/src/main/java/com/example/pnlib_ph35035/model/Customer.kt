package com.example.pnlib_ph35035.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey
    @NotNull
    val idCustomer: String,

    @ColumnInfo("nameCustomer")
    val name: String,

    @ColumnInfo("numberPhone")
    val numberPhone: String
)
