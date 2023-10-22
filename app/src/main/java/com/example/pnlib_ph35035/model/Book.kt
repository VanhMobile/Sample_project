package com.example.pnlib_ph35035.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.NotNull
import java.io.Serializable


@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val idBook: Int?,

    @ColumnInfo("imgPath")
    var imgPath: String,

    @ColumnInfo("nameBook")
    var nameBook: String,

    @ColumnInfo("category")
    var category: String,

    @ColumnInfo("quantity")
    var quantity: Int,

    @ColumnInfo("price")
    var price: Int,

    @ColumnInfo("color")
    var color: String?
):Serializable{
    override fun toString(): String {
        return "$color + $nameBook + $category"
    }
}
