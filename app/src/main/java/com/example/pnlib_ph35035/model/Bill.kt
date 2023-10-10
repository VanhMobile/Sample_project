package com.example.pnlib_ph35035.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "bills",
    foreignKeys = [
        ForeignKey(entity = Customer::class,
            parentColumns = ["idCustomer"],
            childColumns = ["idCustomerBill"],
            onDelete = ForeignKey.CASCADE),

        ForeignKey(entity = Book::class,
            parentColumns = ["idBook"],
            childColumns = ["idBookBill"],
            onDelete = ForeignKey.CASCADE)
    ])
data class Bill (
    @PrimaryKey
    @NotNull
    val idBill: String,

    @ColumnInfo("idCustomerBill")
    val idCustomerBill: String,

    @ColumnInfo("dateBill")
    val dateBill: String,

    @ColumnInfo("idBookBill")
    val idBookBill: String,

    @ColumnInfo("quantityBill")
    val quantityBill: Int,

    @ColumnInfo("status")
    var status: String
)